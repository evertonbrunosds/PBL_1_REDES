package controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import model.ServerConsumer;
import uefs.ComumBase.classes.ClientConnection;
import model.RecycleBin;
import org.json.JSONObject;
import static util.Constants.*;
import uefs.ComumBase.interfaces.Receiver;
import static uefs.ComumBase.interfaces.Status.*;

public class MainController extends RecycleBin {

    private static MainController instance;
    private ClientConnection currentConnection;
    private final ServerConsumer request;
    private final List<Receiver<Boolean>> actionListChangeConnection;
    private final List<Receiver<JSONObject>> actionListChangeRecycle;

    private MainController() {
        request = new ServerConsumer(this);
        actionListChangeConnection = new LinkedList<>();
        actionListChangeRecycle = new LinkedList<>();
    }

    public static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    public void addActionChangeConnection(final Receiver<Boolean> action) {
        actionListChangeConnection.add(action);
    }

    public void addActionChangeRecycle(final Receiver<JSONObject> action) {
        actionListChangeRecycle.add(action);
    }

    @Override
    public void setData(final JSONObject data) {
        super.setData(data);
        actionListChangeRecycle.forEach(action -> action.receive(data));
    }

    public boolean isConnected() {
        return currentConnection != null;
    }

    public void disconnect() {
        currentConnection = null;
        actionListChangeConnection.forEach(action -> action.receive(isConnected()));
    }

    public void connectToServer(final String ip, final int port) throws IOException {
        final ClientConnection newConnection = new ClientConnection(ip, port);
        final JSONObject response = request.get(newConnection);
        if (response.toMap().containsKey(STATUS)) {
            currentConnection = newConnection;
        }
        actionListChangeConnection.forEach(action -> action.receive(isConnected()));
    }

    public void listRecycleBins(final Receiver<String[]> action) throws IOException {
        final JSONObject response = request.get(currentConnection);
        if (response.toMap().containsKey(ALL_IDS)) {
            action.receive(response.getString(ALL_IDS).split(";"));
        }
    }

    public void showRecycleDetails(final String id) throws IOException {
        setId(id);
        final JSONObject response = request.get(currentConnection);
        if (response.getString(STATUS).equals(FOUND)) {
            setData(response);
        }
    }

    public void setIsPriority(final String isPriority) throws IOException {
        getData().put(IS_PRIORITY, isPriority);
        final JSONObject response = request.put(currentConnection);
        if (!response.getString(STATUS).equals(FOUND)) {
            throw new IOException();
        }
    }

    public void setIsBlocked(final String isBlocked) throws IOException {
        getData().put(IS_BLOCKED, isBlocked);
        final JSONObject response = request.put(currentConnection);
        if (!response.getString(STATUS).equals(FOUND)) {
            throw new IOException();
        }
    }

}
