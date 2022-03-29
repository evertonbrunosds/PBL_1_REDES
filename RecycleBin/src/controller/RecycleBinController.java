package controller;

import interfaces.Connection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import model.RecycleBin;

public class RecycleBinController extends RecycleBin {

    private static RecycleBinController instance;
    private Connection<DataInputStream, DataOutputStream> connection;

    private RecycleBinController() {
    }

    public static RecycleBinController getInstance() {
        if (instance == null) {
            instance = new RecycleBinController();
        }
        return instance;
    }
    
    public void connect(final String ip, final int port) throws IOException {
        connection = Connection.builder(ip, port);
        connection.test();
    }

}
