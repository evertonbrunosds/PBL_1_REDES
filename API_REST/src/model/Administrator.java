package model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import org.json.JSONObject;
import uefs.ComumBase.interfaces.ClientConsumer;
import static util.Constants.*;
import org.json.JSONException;
import static uefs.ComumBase.interfaces.Status.*;

/**
 * Classe responsável por comportar-se como um consumidor de clientes para
 * administradores.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class Administrator implements ClientConsumer {

    /**
     * Refere-se a mensagem de requisição efetuada por um administrador
     * conectado.
     */
    private final JSONObject request;
    /**
     * Refere-se ao meio de resposta para toda requisição efetuada por um
     * administrador conectado.
     */
    private final DataOutputStream response;
    /**
     * Refere-se aos dados mapeados de todas as lixeiras do servidor.
     */
    private final Map<String, JSONObject> dataMap;
    /**
     * Refere-se ao ID da lixeira atualmente conectada.
     */
    private final String recycleBinId;

    public Administrator(final JSONObject request, final DataOutputStream response, final Map<String, JSONObject> dataMap) {
        this.request = request;
        this.response = response;
        this.dataMap = dataMap;
        String tmpId = null;
        try {
            tmpId = request.getString(ID);
        } catch (final JSONException ex) {
            tmpId = "";
        } finally {
            recycleBinId = tmpId;
        }
    }

    /**
     * Método responsável por tratar de requisições mal succedidas.
     *
     * @param status Refere-se ao status que representa a falta de sucesso na
     * requisição.
     * @return Retorna resposta em JSON indicando o ocorrido.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    private String unsuccessfulRequest(final String status) throws IOException {
        final JSONObject msg = new JSONObject();
        msg.put(STATUS, status);
        final String allRecycleBinsId = dataMap.keySet().stream().map((id) -> id.concat(";")).reduce("", String::concat);
        msg.put(ALL_IDS, allRecycleBinsId.replaceFirst(".$", ""));
        response.flush();
        return msg.toString();
    }

    /**
     * Método responsável por indicar se uma requisição não possui campo de ID
     * de lixeira.
     *
     * @return Retorna indicativo de que a requisição não possui campo de ID de
     * lixeira.
     */
    private boolean notContainsID() {
        final Map<String, Object> mapRequest = request.toMap();
        return !mapRequest.containsKey(ID);
    }

    /**
     * Método responsável por tratar de requisições que visam buscar dados de
     * uma lixeira.
     *
     * @return Retorna resposta em JSON com os dados de uma lixeira.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    private String getRequest() throws IOException {
        final Map<String, Object> dataUser = dataMap.get(recycleBinId).toMap();
        final JSONObject msg = new JSONObject();
        msg.put(STATUS, FOUND);
        msg.put(IS_BLOCKED, dataUser.get(IS_BLOCKED));
        msg.put(USAGE, dataUser.get(USAGE));
        msg.put(IS_PRIORITY, dataUser.get(IS_PRIORITY));
        msg.put(LOCATION, dataUser.get(LOCATION));
        final String allRecycleBinsId = dataMap.keySet().stream().map((id) -> id.concat(";")).reduce("", String::concat);
        msg.put(ALL_IDS, allRecycleBinsId.replaceFirst(".$", ""));
        response.flush();
        return msg.toString();
    }

    /**
     * Método responsável por indicar se um usuário pode ser encontrado na base
     * de dados.
     *
     * @return Retorna indicativo de que um usuário pode se encontrado.
     */
    private boolean userFound() {
        return "".equals(recycleBinId) ? false : dataMap.containsKey(recycleBinId);
    }

    /**
     * Método responsável por tratar de requisições que visam apagar dados de
     * uma lixeira.
     *
     * @return Retorna resposta em JSON com os dados de uma recém apagada
     * lixeira.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    private String deleteRequest() throws IOException {
        final String getRequest = getRequest();
        dataMap.remove(recycleBinId);
        return getRequest;
    }

    /**
     * Método responsável por indicar se uma requisição não possui campo de
     * bloqueio de lixeira.
     *
     * @return Retorna indicativo de que a requisição não possui campo de
     * bloqueio de lixeira.
     */
    private boolean notContainsIsBlocked() {
        final Map<String, Object> mapRequest = request.toMap();
        return !mapRequest.containsKey(IS_BLOCKED);
    }

    /**
     * Método responsável por indicar se uma requisição não possui campo de
     * prioridade de lixeira.
     *
     * @return Retorna indicativo de que a requisição não possui campo de
     * prioridade de lixeira.
     */
    private boolean notContainsIsPriority() {
        final Map<String, Object> mapRequest = request.toMap();
        return !mapRequest.containsKey(IS_PRIORITY);
    }

    /**
     * Método responsável por tratar de requisições que visam atualizar dados de
     * uma lixeira.
     *
     * @return Retorna resposta em JSON com os dados de uma recém atualizada
     * lixeira.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    private String putRequest() throws IOException {
        final JSONObject dataUser = dataMap.get(recycleBinId);
        dataUser.put(IS_BLOCKED, request.getString(IS_BLOCKED));
        dataUser.put(IS_PRIORITY, request.getString(IS_PRIORITY));
        return getRequest();
    }

    /**
     * Método responsável por tratar de requisições que visam criar dados de uma
     * lixeira.
     *
     * @return Retorna resposta em JSON com os dados de uma recém criada
     * lixeira.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    private String postRequest() throws IOException {
        final JSONObject dataUser = new JSONObject();
        dataMap.put(recycleBinId, dataUser);
        dataUser.put(IS_BLOCKED, request.get(IS_BLOCKED));
        dataUser.put(USAGE, "0");
        dataUser.put(CLEAR, "FALSE");
        dataUser.put(IS_PRIORITY, request.get(IS_PRIORITY));
        final JSONObject msg = new JSONObject(getRequest());
        msg.put(STATUS, NOT_FOUND);
        return msg.toString();
    }

    /**
     * Método responsável por criar os dados de uma lixeira.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public void post() throws IOException {
        response.writeUTF(notContainsID() || notContainsIsBlocked() || notContainsIsPriority()
                ? unsuccessfulRequest(BAD_REQUEST)
                : !userFound()
                        ? postRequest()
                        : unsuccessfulRequest(FOUND)
        );
    }

    /**
     * Método responsável por buscar os dados de uma lixeira.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public void get() throws IOException {
        response.writeUTF(notContainsID()
                ? unsuccessfulRequest(BAD_REQUEST)
                : userFound()
                        ? getRequest()
                        : unsuccessfulRequest(NOT_FOUND)
        );
    }

    /**
     * Método responsável por atualizar os dados de uma lixeira.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public void put() throws IOException {
        response.writeUTF(notContainsID() || notContainsIsBlocked() || notContainsIsPriority()
                ? unsuccessfulRequest(BAD_REQUEST)
                : userFound()
                        ? putRequest()
                        : unsuccessfulRequest(NOT_FOUND)
        );
    }

    /**
     * Método responsável por apagar os dados de uma lixeira.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public void delete() throws IOException {
        response.writeUTF(notContainsID()
                ? unsuccessfulRequest(BAD_REQUEST)
                : userFound()
                        ? deleteRequest()
                        : unsuccessfulRequest(NOT_FOUND)
        );
    }

}
