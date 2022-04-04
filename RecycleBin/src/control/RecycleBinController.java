package control;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.RecycleBin;
import org.json.JSONObject;
import uefs.ComumBase.interfaces.ClientConnection;
import util.Usage;

public class RecycleBinController extends RecycleBin {

    private static RecycleBinController instance;
    private ClientConnection connection;
    private String id;

    private RecycleBinController() {
        id = null;
    }

    public static RecycleBinController getInstance() {
        if (instance == null) {
            instance = new RecycleBinController();
        }
        return instance;
    }

    private JSONObject getCurrentState() {
        final HashMap<String, String> currentState = new HashMap<>();
        currentState.put("DEVICE", "RECYCLE_BIN");
        currentState.put("ID", id);
        currentState.put("IS_BLOCKED", Boolean.toString(isBlocked()).toUpperCase());
        currentState.put("USAGE", Usage.toString(getUsage()));
        return new JSONObject(currentState);
    }

    private JSONObject toJSONObject(final String string) {
        return new JSONObject(string);
    }

    public void connect(final String ip, final int port) throws IOException {
        final ClientConnection newConnection = ClientConnection.builder(ip, port);
        newConnection.streamBuilder((inputStream, outputStream) -> {
            final JSONObject inputJSON = toJSONObject(inputStream.readUTF());
            id = inputJSON.get("ID").toString();
            outputStream.flush();
            outputStream.writeUTF(getCurrentState().toString());
        });
    }

}
