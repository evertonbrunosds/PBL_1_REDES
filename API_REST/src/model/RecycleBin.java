package model;

import util.IdGenerator;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import org.json.JSONObject;
import uefs.ComumBase.interfaces.ClientConsumer;
import static uefs.ComumBase.interfaces.Status.*;
import static util.Constants.*;
import org.json.JSONException;

/**
 * Classe responsável por comportar-se como um consumidor de clientes para
 * lixeiras.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class RecycleBin implements ClientConsumer {

    /**
     * Refere-se ao gerador de ID utilizado para dar ID as lixeiras recém
     * conectadas.
     */
    private static final IdGenerator ID_GENERATOR = new IdGenerator();

    /**
     * Refere-se ao ID da lixeira atualmente conectada.
     */
    private final String id;
    /**
     * Refere-se a mensagem de requisição efetuada por uma lixeira conectada.
     */
    private final JSONObject request;
    /**
     * Refere-se ao meio de resposta para toda requisição efetuada por uma
     * lixeira conectada.
     */
    private final DataOutputStream response;
    /**
     * Refere-se aos dados mapeados de todas as lixeiras do servidor.
     */
    private final Map<String, JSONObject> dataMap;

    /**
     * Construtor responsável por instanciar um consumidor de clientes para
     * lixeiras.
     *
     * @param request Refere-se a mensagem de requisição efetuada por uma
     * lixeira conectada.
     * @param response Refere-se ao meio de resposta para toda requisição
     * efetuada por uma lixeira conectada.
     * @param dataMap Refere-se aos dados mapeados de todas as lixeiras do
     * servidor.
     * @throws InterruptedException Exceção lançada para o caso de não ser
     * possível gerar um ID para a lixeira recém conectada.
     */
    public RecycleBin(final JSONObject request, final DataOutputStream response, final Map<String, JSONObject> dataMap) throws InterruptedException {
        this.request = request;
        this.response = response;
        this.dataMap = dataMap;
        String tmpId = null;
        try {
            tmpId = request.getString(ID);
        } catch (final JSONException ex) {
            tmpId = ID_GENERATOR.getStringId();
        } finally {
            id = tmpId;
        }
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
     * nível de uso a lixeira.
     *
     * @return Retorna indicativo de que a requisição não possui campo de nível
     * de uso a lixeira.
     */
    private boolean notContainsUsage() {
        final Map<String, Object> mapRequest = request.toMap();
        return !mapRequest.containsKey(USAGE);
    }

    /**
     * Método responsável por indicar se uma lixeira pode ser encontrada na base
     * de dados.
     *
     * @return Retorna indicativo de que uma lixeira pode se encontrada.
     */
    private boolean userFound() {
        return dataMap.containsKey(id);
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
        final JSONObject newDataUser = new JSONObject();
        newDataUser.put(STATUS, status);
        newDataUser.put(ID, id);
        response.flush();
        return newDataUser.toString();
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
        final JSONObject dataUser = new JSONObject(dataMap.get(id).toMap());
        if (dataMap.get(id).toMap().containsKey(CLEAR)) {
            dataMap.get(id).put(CLEAR, "FALSE");
        }
        dataUser.put(STATUS, FOUND);
        dataUser.remove(IS_PRIORITY);
        response.flush();
        return dataUser.toString();
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
        dataMap.put(id, dataUser);
        dataUser.put(IS_BLOCKED, request.get(IS_BLOCKED));
        dataUser.put(USAGE, request.get(USAGE));
        dataUser.put(CLEAR, "FALSE");
        dataUser.put(IS_PRIORITY, "FALSE");
        dataUser.put(LOCATION, request.get(LOCATION));
        final JSONObject msg = new JSONObject(getRequest());
        msg.put(STATUS, NOT_FOUND);
        return msg.toString();
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
        final JSONObject dataUser = dataMap.get(id);
        dataUser.put(USAGE, request.getString(USAGE));
        return getRequest();
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
        dataMap.remove(id);
        return getRequest;
    }

    /**
     * Método responsável por buscar os dados de uma lixeira.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public void get() throws IOException {
        response.writeUTF(userFound()
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
        response.writeUTF(notContainsID() || notContainsIsBlocked() || notContainsUsage()
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

    /**
     * Método responsável por criar os dados de uma lixeira.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public void post() throws IOException {
        response.writeUTF(notContainsID() || notContainsIsBlocked() || notContainsUsage()
                ? unsuccessfulRequest(BAD_REQUEST)
                : !userFound()
                        ? postRequest()
                        : unsuccessfulRequest(FOUND)
        );
    }

}
