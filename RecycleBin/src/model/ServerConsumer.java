package model;

import java.io.IOException;
import org.json.JSONObject;
import uefs.ComumBase.interfaces.Container;
import static uefs.ComumBase.interfaces.Method.*;
import static util.Constants.*;

/**
 * Classe responsável por comportar-se como consumidor de servidor.
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class ServerConsumer implements uefs.ComumBase.interfaces.ServerConsumer<ClientConnection, JSONObject> {

    /**
     * Refere-se a lixeira monitorada.
     */
    private final RecycleBin recycleBin;

    /**
     * Construtor responsável por instanciar o consumidor de servidor.
     * @param recycleBin Refere-se a lixeira monitorada.
     */
    public ServerConsumer(final RecycleBin recycleBin) {
        this.recycleBin = recycleBin;
    }

    /**
     * Método responsável por criar dados da lixeira.
     *
     * @param connection Refere-se a conexão usada no processo.
     * @return Retorna a resposta dada pelo servidor pela ação executada em JSON.
     * @throws IOException Refere-se a exceção de entrada/saída.
     */
    @Override
    public JSONObject post(final ClientConnection connection) throws IOException {
        return runHighMethod(connection, POST);
    }

    /**
     * Método responsável por buscar dados da lixeira.
     *
     * @param connection Refere-se a conexão usada no processo.
     * @return Retorna a resposta dada pelo servidor pela ação executada em JSON.
     * @throws IOException Refere-se a exceção de entrada/saída.
     */
    @Override
    public JSONObject get(final ClientConnection connection) throws IOException {
        return runBasicMethod(connection, GET);
    }

    /**
     * Método responsável por atualizar dados da lixeira.
     *
     * @param connection Refere-se a conexão usada no processo.
     * @return Retorna a resposta dada pelo servidor pela ação executada em JSON.
     * @throws IOException Refere-se a exceção de entrada/saída.
     */
    @Override
    public JSONObject put(final ClientConnection connection) throws IOException {
        return runHighMethod(connection, PUT);
    }

    /**
     * Método responsável por apagar dados da lixeira.
     *
     * @param connection Refere-se a conexão usada no processo.
     * @return Retorna a resposta dada pelo servidor pela ação executada em JSON.
     * @throws IOException Refere-se a exceção de entrada/saída.
     */
    @Override
    public JSONObject delete(final ClientConnection connection) throws IOException {
        return runBasicMethod(connection, DELETE);
    }

    /**
     * Método responsável por executar um método REST com dados básicos (tipo de método e ID).
     * @param connection Refere-se a conexão usada no processo. 
     * @param method Refere-se ao tipo método REST utilizado.
     * @return Retorna a resposta dada pelo servidor pela ação executada em JSON.
     * @throws IOException Refere-se a exceção de entrada/saída.
     */
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

    /**
     * Método responsável por executar um método REST com dados avançados (tipo de método, ID, estado de bloqueio e informações de uso).
     * @param connection Refere-se a conexão usada no processo. 
     * @param method Refere-se ao tipo método REST utilizado.
     * @return Retorna a resposta dada pelo servidor pela ação executada em JSON.
     * @throws IOException Refere-se a exceção de entrada/saída.
     */
    private JSONObject runHighMethod(final ClientConnection connection, final String method) throws IOException {
        final JSONObject request = getBasicRequest(method, connection);
        final Container<String, String> response = getContainerString();
        request.put(IS_BLOCKED, Boolean.toString(recycleBin.isBlocked()).toUpperCase());
        request.put(USAGE, recycleBin.getUsage());
        request.put(LOCATION, connection.getLocation());
        connection.streamBuilder((inputStream, outputStream) -> {
            outputStream.flush();
            outputStream.writeUTF(request.toString());
            response.setContent(inputStream.readUTF());
        });
        return new JSONObject(response.getContent());
    }

    /**
     * Método responsável por obiter uma requisição com dados básicos (tipo de método e ID).
     * @param method Refere-se ao tipo de método REST.
     * @param connection Refere-se a conexão usada no processo. 
     * @return Retorna a requisição básica que será enviada ao servidor.
     */
    private static JSONObject getBasicRequest(final String method, final ClientConnection connection) {
        final JSONObject currentState = new JSONObject();
        currentState.put(METHOD, method);
        if (connection.hasId()) {
            currentState.put(ID, connection.getId());
        }
        return currentState;
    }
    
    /**
     * Método responsável por instanciar um container.
     * @return Retorna uma instância de container.
     */
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
