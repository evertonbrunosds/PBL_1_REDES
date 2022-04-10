package model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import org.json.JSONObject;
import uefs.ComumBase.interfaces.ClientConsumer;
import static uefs.ComumBase.interfaces.Status.*;
import static model.Constants.*;

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
        this.id = (UNDETERMINED.equals(request.getString(ID)) || request.getString(ID) == null) ? ID_GENERATOR.getStringId() : request.getString(ID);
        this.dataMap = dataMap;
    }

    /**
     * Método responsável por indicar se uma requisição não é básicamente
     * válida. Isto é: se não possui método e ou ID.
     *
     * @return Retorna indicativo de que a requisição é básicamente válida ou
     * não.
     */
    private boolean isNotBasicallyValidRequest() {
        final Map<String, Object> mapRequest = request.toMap();
        return !mapRequest.containsKey(ID);
    }

    /**
     * Método responsável por indicar se uma requisição não é totalmente válida.
     * Isto é: se não possui método, ID, campo de bloqueio e ou campo de uso.
     *
     * @return Retorna indicativo de que a requisição é totalmente válida ou
     * não.
     */
    private boolean isNotFullyValidRequest() {
        final Map<String, Object> mapRequest = request.toMap();
        final boolean containsIsBlocked = mapRequest.containsKey(IS_BLOCKED);
        final boolean containsIsUsage = mapRequest.containsKey(USAGE);
        return isNotBasicallyValidRequest() || !containsIsBlocked || !containsIsUsage;
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
        dataUser.put(STATUS, OK);
        dataUser.remove(PRIORITY);
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
        dataUser.put(PRIORITY, "FALSE");
        return getRequest();
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
     * Método responsável por buscar os dados de uma lideira.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public void get() throws IOException {
        final boolean userFound = dataMap.containsKey(id);
        response.writeUTF(isNotBasicallyValidRequest()
                ? unsuccessfulRequest(BAD_REQUEST)
                : userFound
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
        final boolean userFound = dataMap.containsKey(id);
        response.writeUTF(isNotFullyValidRequest()
                ? unsuccessfulRequest(BAD_REQUEST)
                : userFound
                        ? putRequest()
                        : unsuccessfulRequest(NOT_FOUND)
        );
    }

    /**
     * Método responsável por apagar os dados de uma lideira.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public void delete() throws IOException {
        final boolean userFound = dataMap.containsKey(id);
        response.writeUTF(isNotBasicallyValidRequest()
                ? unsuccessfulRequest(BAD_REQUEST)
                : userFound
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
        final boolean userNotFound = !dataMap.containsKey(id);
        response.writeUTF(isNotFullyValidRequest()
                ? unsuccessfulRequest(BAD_REQUEST)
                : userNotFound
                        ? postRequest()
                        : unsuccessfulRequest(FOUND)
        );
    }

}
