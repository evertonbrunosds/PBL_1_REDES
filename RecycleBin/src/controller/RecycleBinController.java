package controller;

import java.io.IOException;
import java.util.HashMap;
import model.RecycleBin;
import org.json.simple.JSONObject;
import uefs.ComumBase.interfaces.ClientConnection;

public class RecycleBinController extends RecycleBin {

    private static RecycleBinController instance;
    private ClientConnection connection;

    private RecycleBinController() {
    }

    public static RecycleBinController getInstance() {
        if (instance == null) {
            instance = new RecycleBinController();
        }
        return instance;
    }
    
    public void connect(final String ip, final int port) throws IOException {
        final ClientConnection newConnection = ClientConnection.builder(ip, port);
        newConnection.streamBuilder((inputStream, outputStream) -> {
            final HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("DEVICE", "RECYCLE_BIN");
            hashMap.put("IS_BLOCKED", Boolean.toString(isBlocked()).toUpperCase());
            
            JSONObject recycleBin = new JSONObject();
        });
    }

}
