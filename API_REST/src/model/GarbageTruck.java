package model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import uefs.ComumBase.interfaces.ClientConsumer;
import static uefs.ComumBase.interfaces.Status.*;
import static util.Constants.*;

/**
 * Classe responsável por comportar-se como um consumidor de clientes para
 * caminhão de lixo.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class GarbageTruck implements ClientConsumer {
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
     * caminhões de lixo.
     *
     * @param request Refere-se a mensagem de requisição efetuada por um
     * caminhão de lixo conectado.
     * @param response Refere-se ao meio de resposta para toda requisição
     * efetuada por um caminhão de lixo conectado.
     * @param dataMap Refere-se aos dados mapeados de todos as caminhãões de lixo do
     * servidor.
     * @throws InterruptedException Exceção lançada para o caso de não ser
     * possível gerar um ID para o caminhão de lixo recém conectado.
     */
    public GarbageTruck(final JSONObject request, final DataOutputStream response, final Map<String, JSONObject> dataMap) throws InterruptedException {
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
     * Método responsável por indicar se uma lixeira pode ser encontrada na base de dados.
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

    @Override
    public void post() throws IOException {
        response.writeUTF(notContainsID() || notContainsUsage()
                ? unsuccessfulRequest(BAD_REQUEST)
                : !userFound()
                        ? postRequest()
                        : unsuccessfulRequest(FOUND)
        );
    }

    @Override
    public void get() throws IOException {
        response.writeUTF(userFound()
                ? getRequest()
                : unsuccessfulRequest(NOT_FOUND)
        );
    }

    @Override
    public void put() throws IOException {
        response.writeUTF(notContainsID() || notContainsUsage()
                ? unsuccessfulRequest(BAD_REQUEST)
                : userFound()
                        ? putRequest()
                        : unsuccessfulRequest(NOT_FOUND)
        );
    }

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
