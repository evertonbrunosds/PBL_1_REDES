package model;

import java.io.IOException;
import org.json.JSONObject;
import uefs.ComumBase.interfaces.ClientConnection;
import uefs.ComumBase.interfaces.Container;
import static uefs.ComumBase.interfaces.Method.*;
import static util.Constants.*;

public class ServerConsumer implements uefs.ComumBase.interfaces.ServerConsumer<ClientConnection, JSONObject> {
    private final RecycleBin recycleBin;

    public ServerConsumer(final RecycleBin recycleBin) {
        this.recycleBin = recycleBin;
    }
    
    private JSONObject getBasicRequest(final String method, final ClientConnection connection) {
        final JSONObject currentState = new JSONObject();
        currentState.put(METHOD, method);
        if (recycleBin.hasId()) {
            currentState.put(ID, recycleBin.getId());
        }
        return currentState;
    }
    
    private JSONObject runBasicMethod(final ClientConnection connection, final String method) throws IOException {
        final JSONObject request = getBasicRequest(method, connection);
        final Container<String, String> response = getContainerString();
        connection.streamBuilder((inputStream, outputStream) -> {
            outputStream.flush();
            outputStream.writeUTF(request.toString());
            response.setContent(inputStream.readUTF());
        });
        return new JSONObject(response.getContent());
    }
    
    @Override
    public JSONObject post(final ClientConnection connection) throws IOException {
        //return runHighMethod(connection, POST);
        throw new IOException();
    }

    @Override
    public JSONObject get(final ClientConnection connection) throws IOException {
        return runBasicMethod(connection, GET);
    }

    @Override
    public JSONObject put(final ClientConnection connection) throws IOException {
        //return runHighMethod(connection, PUT);
        throw new IOException();
    }

    @Override
    public JSONObject delete(final ClientConnection connection) throws IOException {
        return runBasicMethod(connection, DELETE);
    }
    
    private static Container<String, String> getContainerString() {
        return new Container<String, String>() {
            private String content = null;

            @Override
            public void setContent(final String content) {
                this.content = content;
            }

            @Override
            public String getContent() {
                return this.content;
            }
        };
    }
    
}
