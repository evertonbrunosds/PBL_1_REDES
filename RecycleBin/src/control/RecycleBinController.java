package control;

import java.io.IOException;
import java.util.HashMap;
import model.RecycleBin;
import org.json.JSONObject;
import uefs.ComumBase.enums.Method;
import uefs.ComumBase.interfaces.ClientConnection;
import util.Status;
import util.Transmitter;

public class RecycleBinController extends RecycleBin {

    private static RecycleBinController instance;
    private static final String STATUS = "STATUS";
    private ClientConnection currentConnection;
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

    public String getId() {
        return (id == null) ? "UNDETERMINED" : id;
    }

    private JSONObject getBasicRequest(final Method method) {
        final HashMap<String, String> currentState = new HashMap<>();
        currentState.put("METHOD", Method.toString(method));
        currentState.put("DEVICE", "RECYCLE_BIN");
        currentState.put("ID", getId());
        return new JSONObject(currentState);
    }

    /**
     * Método responsável por buscar dados do consumidor.
     *
     * @param clientConnection Refere-se a conexão usada no processo.
     * @return Retorna a resposta do servidor em JSON.
     * @throws IOException Refere-se a exceção de entrada/saída.
     */
    private JSONObject get(final ClientConnection clientConnection) throws IOException {
        final Transmitter<String, String> response = getTransmitterString();
        clientConnection.streamBuilder((inputStream, outputStream) -> {
            outputStream.flush();
            outputStream.writeUTF(getBasicRequest(Method.get).toString());
            response.input(inputStream.readUTF());
        });
        return new JSONObject(response.output());
    }

    /**
     * Método responsável por atualizar dados do consumidor.
     *
     * @param clientConnection Refere-se a conexão usada no processo.
     * @return Retorna a resposta do servidor em JSON.
     */
    private JSONObject put(final ClientConnection clientConnection) {
        final Transmitter<String, String> response = getTransmitterString();
        return new JSONObject(response.output());
    }

    /**
     * Método responsável por apagar dados do consumidor.
     *
     * @param clientConnection Refere-se a conexão usada no processo.
     * @return Retorna a resposta do servidor em JSON.
     */
    private JSONObject delete(final ClientConnection clientConnection) {
        final Transmitter<String, String> response = getTransmitterString();
        return new JSONObject(response.output());
    }

    public void connect(final String ip, final int port) throws IOException {
        final ClientConnection newConnection = ClientConnection.builder(ip, port);
        final JSONObject responseGet = get(newConnection);
        if (responseGet.getString(STATUS).equals(Status.NOT_FOUND)) {
            id = responseGet.getString("ID");
            final JSONObject responsePut = put(newConnection);
            if (responsePut.getString(STATUS).equals(Status.OK)) {
                delete(currentConnection);
                currentConnection = newConnection;
            } else {
                id = "UNDETERMINED";
                throw new IOException();
            }
        } else if (responseGet.getString(STATUS).equals(Status.OK)) {
            
        }
    }
    
    private static Transmitter<String, String> getTransmitterString() {
        return new Transmitter<String, String>() {
            String input = null;

            @Override
            public void input(final String input) {
                this.input = input;
            }

            @Override
            public String output() {
                return input;
            }
        };
    }

}
