package control;

import java.io.IOException;
import java.util.HashMap;
import model.RecycleBin;
import org.json.JSONObject;
import uefs.ComumBase.enums.Method;
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

    private JSONObject getCurrentState(final Method method, final boolean success) {
        final HashMap<String, String> currentState = new HashMap<>();
        currentState.put("METHOD", Method.toString(method));
        currentState.put("SUCCESS", Boolean.toString(success).toUpperCase());
        currentState.put("DEVICE", "RECYCLE_BIN");
        currentState.put("ID", (id == null) ? "UNDETERMINED" : id);
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
        });
    }

}
