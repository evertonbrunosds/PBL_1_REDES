package model;

import java.io.IOException;
import org.json.JSONObject;

public class ServerConsumer implements uefs.ComumBase.interfaces.ServerConsumer<ClientConnection, JSONObject> { 

    @Override
    public JSONObject post(ClientConnection connection) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JSONObject get(ClientConnection connection) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JSONObject put(ClientConnection connection) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JSONObject delete(ClientConnection connection) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
