package model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import org.json.JSONObject;
import uefs.ComumBase.interfaces.ClientConsumer;

public class Administrator implements ClientConsumer {
    private final JSONObject request;
    private final DataOutputStream response;
    private final Map<String, JSONObject> dataMap;

    public Administrator(final JSONObject request, final DataOutputStream response, final Map<String, JSONObject> dataMap) {
        this.request = request;
        this.response = response;
        this.dataMap = dataMap;
    }
    
    @Override
    public void post() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void get() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void put() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
