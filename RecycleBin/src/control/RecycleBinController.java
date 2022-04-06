package control;

import java.io.IOException;
import model.RecycleBin;
import org.json.JSONObject;
import model.ClientConnection;
import model.RecycleBinServerConsumer;
import uefs.ComumBase.interfaces.ServerConsumer;
import static uefs.ComumBase.interfaces.Status.*;

public class RecycleBinController extends RecycleBin {

    private static RecycleBinController instance;
    private static final String STATUS = "STATUS";
    private ClientConnection currentConnection;
    private final ServerConsumer<ClientConnection, JSONObject> request;

    private RecycleBinController() {
        request = new RecycleBinServerConsumer(this);
    }

    public static RecycleBinController getInstance() {
        if (instance == null) {
            instance = new RecycleBinController();
        }
        return instance;
    }

    public String getId() {
        return (currentConnection == null) ? "UNDETERMINED" : currentConnection.getId();
    }

    public void connectToServer(final String ip, final int port) throws IOException {
        final ClientConnection newConnection = new ClientConnection(ip, port);
        final JSONObject response = request.put(newConnection);
        switch (response.getString(STATUS)) {
            case OK:
                disconnect();
                currentConnection = newConnection;
                break;
            case NOT_FOUND:
                startLogs(currentConnection);
                break;
            case INTERNAL_SERVER_ERROR:
                throw new IOException("INTERNAL_SERVER_ERROR");
            default:
                throw new IOException("UNDETERMINED");
        }
    }

    public void disconnect() {

    }

    public void startLogs(final ClientConnection connection) throws IOException {
        final JSONObject response = request.post(connection);
        switch (response.getString(STATUS)) {
            case OK:
                disconnect();
                currentConnection = connection;
                break;
            case INTERNAL_SERVER_ERROR:
                throw new IOException("INTERNAL_SERVER_ERROR");
            default:
                throw new IOException("UNDETERMINED");
        }
    }
}
