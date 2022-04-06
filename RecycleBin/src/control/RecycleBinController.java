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
    private final ServerConsumer<ClientConnection, JSONObject> serverConsumer;

    private RecycleBinController() {
        serverConsumer = new RecycleBinServerConsumer(this);
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


    public void connect(final String ip, final int port) throws IOException {
        final ClientConnection newConnection = new ClientConnection(ip, port);
        final JSONObject responseGet = serverConsumer.get(newConnection);
        if (responseGet.getString(STATUS).equals(NOT_FOUND)) {
            newConnection.setId(responseGet.getString("ID"));
            final JSONObject responsePost = serverConsumer.poust(newConnection);
            if (responsePut.getString(STATUS).equals(OK)) {
                serverConsumer.delete(currentConnection);
                currentConnection = newConnection;
            } else {
                id = "UNDETERMINED";
                throw new IOException();
            }
        } else if (responseGet.getString(STATUS).equals(OK)) {

        }
    }

}
