package model;

import java.io.IOException;
import org.json.JSONObject;
import uefs.ComumBase.interfaces.ClientConnection;
import uefs.ComumBase.interfaces.Container;
import static uefs.ComumBase.interfaces.Method.*;
import static util.Constants.*;

/**
 * Classe responsável por comportar-se como como um consumidor de servidor.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class ServerConsumer implements uefs.ComumBase.interfaces.ServerConsumer<ClientConnection, JSONObject> {

    /**
     * Refere-se ao administrador de lixeira com o qual será trabalhado no
     * servidor.
     */
    private final RecycleBinAdministrator recycleBinAdministrator;

    /**
     * Construtor responsável por instanciar um consumidor de servidor.
     *
     * @param recycleBinAdministrator Refere-se ao administrador de lixeira com
     * o qual será trabalhado no servidor.
     */
    public ServerConsumer(final RecycleBinAdministrator recycleBinAdministrator) {
        this.recycleBinAdministrator = recycleBinAdministrator;
    }

    /**
     * Método responsável por obiter uma requisição básica.
     *
     * @param method Refere-se ao método da requisição.
     * @return Retorna a resposta da requisição.
     */
    private JSONObject getBasicRequest(final String method) {
        final JSONObject currentState = new JSONObject();
        currentState.put(METHOD, method);
        if (recycleBinAdministrator.hasId()) {
            currentState.put(ID, recycleBinAdministrator.getRecycleBinId());
        }
        return currentState;
    }

    /**
     * Método responsável por executar um método REST básico. Isto é: no
     * contexto dessa classe ele pode solicitar ou apagar dados.
     *
     * @param connection Refere-se a conexão utilizada no processo.
     * @param method Refere-se ao método que nesse contexto pode ser GET ou
     * DELETE.
     * @return Retorna o resultado da execução do método básico.
     * @throws IOException exceção lançada no caso de haver um problema de
     * entrada/saída.
     */
    private JSONObject runBasicMethod(final ClientConnection connection, final String method) throws IOException {
        final JSONObject request = getBasicRequest(method);
        final Container<String, String> response = getContainerString();
        connection.streamBuilder((inputStream, outputStream) -> {
            outputStream.flush();
            outputStream.writeUTF(request.toString());
            response.setContent(inputStream.readUTF());
        });
        return new JSONObject(response.getContent());
    }

    /**
     * Método responsável por executar um método REST avançado. Isto é: no
     * contexto dessa classe ele pode criar ou atualizar dados.
     *
     * @param connection Refere-se a conexão utilizada no processo.
     * @param method Refere-se ao método que nesse contexto pode ser GET ou
     * DELETE.
     * @return Retorna o resultado da execução do método básico.
     * @throws IOException exceção lançada no caso de haver um problema de
     * entrada/saída.
     */
    private JSONObject runHighMethod(final ClientConnection connection, final String method) throws IOException {
        final JSONObject request = getBasicRequest(method);
        final Container<String, String> response = getContainerString();
        request.put(IS_BLOCKED, recycleBinAdministrator.getRecycleBinData().getString(IS_BLOCKED));
        request.put(IS_PRIORITY, recycleBinAdministrator.getRecycleBinData().getString(IS_PRIORITY));
        connection.streamBuilder((inputStream, outputStream) -> {
            outputStream.flush();
            outputStream.writeUTF(request.toString());
            response.setContent(inputStream.readUTF());
        });
        return new JSONObject(response.getContent());
    }

    /**
     * Método responsável por criar dados da lixeira.
     *
     * @param connection Refere-se ao objeto de conexão.
     * @return Retorna a resposta da ação.
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public JSONObject post(final ClientConnection connection) throws IOException {
        return runHighMethod(connection, POST);
    }

    /**
     * Método responsável por buscar dados da lixeira.
     *
     * @param connection Refere-se ao objeto de conexão.
     * @return Retorna a resposta da ação.
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public JSONObject get(final ClientConnection connection) throws IOException {
        return runBasicMethod(connection, GET);
    }

    /**
     * Método responsável por atualizar dados da lixeira.
     *
     * @param connection Refere-se ao objeto de conexão.
     * @return Retorna a resposta da ação.
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public JSONObject put(final ClientConnection connection) throws IOException {
        return runHighMethod(connection, PUT);
    }

    /**
     * Método responsável por apagar dados da lixeira.
     *
     * @param connection Refere-se ao objeto de conexão.
     * @return Retorna a resposta da ação.
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public JSONObject delete(final ClientConnection connection) throws IOException {
        return runBasicMethod(connection, DELETE);
    }

    /**
     * Método responsável por instanciar um container.
     *
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
