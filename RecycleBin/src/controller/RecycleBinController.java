package controller;

import interfaces.ClientConnection;
import java.io.IOException;
import model.RecycleBin;

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
        connection = ClientConnection.builder(ip, port);
        connection.test();
    }

}
