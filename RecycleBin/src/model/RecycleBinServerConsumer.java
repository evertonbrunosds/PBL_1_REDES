package model;

import java.io.IOException;
import java.util.HashMap;
import org.json.JSONObject;
import uefs.ComumBase.enums.Method;
import uefs.ComumBase.interfaces.Container;
import uefs.ComumBase.interfaces.ServerConsumer;
import util.Usage;

public class RecycleBinServerConsumer implements ServerConsumer<ClientConnection, JSONObject> {

    private final RecycleBin recycleBin;

    public RecycleBinServerConsumer(final RecycleBin recycleBin) {
        this.recycleBin = recycleBin;
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

    /**
     * Método responsável por criar dados da lixeira.
     *
     * @param connection Refere-se a conexão usada no processo.
     * @return Retorna a resposta do servidor em JSON.
     * @throws IOException Refere-se a exceção de entrada/saída.
     */
    @Override
    public JSONObject poust(final ClientConnection connection) throws IOException {
        return runHighMethod(connection, Method.post);
    }

    /**
     * Método responsável por buscar dados da lixeira.
     *
     * @param connection Refere-se a conexão usada no processo.
     * @return Retorna a resposta do servidor em JSON.
     * @throws IOException Refere-se a exceção de entrada/saída.
     */
    @Override
    public JSONObject get(final ClientConnection connection) throws IOException {
        return runBasicMethod(connection, Method.get);
    }

    /**
     * Método responsável por atualizar dados da lixeira.
     *
     * @param connection Refere-se a conexão usada no processo.
     * @return Retorna a resposta do servidor em JSON.
     * @throws IOException Refere-se a exceção de entrada/saída.
     */
    @Override
    public JSONObject put(final ClientConnection connection) throws IOException {
        return runHighMethod(connection, Method.put);
    }

    /**
     * Método responsável por apagar dados da lixeira.
     *
     * @param connection Refere-se a conexão usada no processo.
     * @return Retorna a resposta do servidor em JSON.
     * @throws IOException Refere-se a exceção de entrada/saída.
     */
    @Override
    public JSONObject delete(final ClientConnection connection) throws IOException {
        return runBasicMethod(connection, Method.delete);
    }

    private JSONObject runBasicMethod(final ClientConnection connection, final Method method) throws IOException {
        final JSONObject request = getBasicRequest(method, connection);
        final Container<String, String> response = getContainerString();
        connection.streamBuilder((inputStream, outputStream) -> {
            outputStream.flush();
            outputStream.writeUTF(request.toString());
            response.setContent(inputStream.readUTF());
        });
        return new JSONObject(response.getContent());
    }

    private JSONObject runHighMethod(final ClientConnection connection, final Method method) throws IOException {
        final JSONObject request = getBasicRequest(method, connection);
        final Container<String, String> response = getContainerString();
        request.put("IS_BLOCKED", Boolean.toString(recycleBin.isBlocked()).toUpperCase());
        request.put("USAGE", Usage.toString(recycleBin.getUsage()));
        connection.streamBuilder((inputStream, outputStream) -> {
            outputStream.flush();
            outputStream.writeUTF(request.toString());
            response.setContent(inputStream.readUTF());
        });
        return new JSONObject(response.getContent());
    }

    private static JSONObject getBasicRequest(final Method method, final ClientConnection clientConnection) {
        final HashMap<String, String> currentState = new HashMap<>();
        currentState.put("METHOD", Method.toString(method));
        currentState.put("DEVICE", "RECYCLE_BIN");
        currentState.put("ID", clientConnection.getId());
        return new JSONObject(currentState);
    }

}
