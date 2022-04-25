package model;

import util.RecycleBinComparator;
import util.MyPriorityQueue;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;
import uefs.ComumBase.interfaces.ClientConsumer;
import static uefs.ComumBase.interfaces.Status.*;
import static uefs.ComumBase.interfaces.Status.BAD_REQUEST;
import static uefs.ComumBase.interfaces.Status.FOUND;
import static util.Constants.*;

/**
 * Classe responsável por comportar-se como caminhão de lixo.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class GarbageTruck implements ClientConsumer {

    /**
     * Refere-se a requisição feita.
     */
    private final JSONObject request;
    /**
     * Refere-se ao meio de resposta utilizado.
     */
    private final DataOutputStream response;
    /**
     * Refere-se aos dados das lixeiras.
     */
    private final Map<String, JSONObject> recycleBinDataMap;
    /**
     * Refere-se aos dados do caminhão.
     */
    private final JSONObject garbageTruckDataJSON;
    /**
     * Refere-se ao id da lixeira.
     */
    private final String recycleBinId;

    /**
     * Construtor responsável por instanciar um caminhão de lixo.
     *
     * @param request Refere-se a requisição feita.
     * @param response Refere-se ao meio de resposta utilizado.
     * @param recycleBinDataMap Refere-se aos dados das lixeiras.
     * @param garbageTruckDataJSON Refere-se aos dados do caminhão.
     */
    public GarbageTruck(
            final JSONObject request,
            final DataOutputStream response,
            final Map<String, JSONObject> recycleBinDataMap,
            final JSONObject garbageTruckDataJSON
    ) {
        String tmpId = null;
        try {
            tmpId = request.getString(ID);
        } catch (final JSONException ex) {
            tmpId = "";
        } finally {
            this.recycleBinId = tmpId;
        }
        this.garbageTruckDataJSON = garbageTruckDataJSON;
        this.request = request;
        this.response = response;
        this.recycleBinDataMap = recycleBinDataMap;
    }

    /**
     * Método responsável por indicar se numa requisição não há campo de
     * dispositivo.
     *
     * @return Retorna indicativo de que numa requisição não há campo de
     * dispositivo.
     */
    private boolean notContainsDevice() {
        return !request.toMap().containsKey(DEVICE);
    }

    /**
     * Método responsável por indicar se numa requisição não há campo de ID.
     *
     * @return Retorna indicativo de que numa requisição não há campo de ID.
     */
    private boolean notContainsID() {
        return !request.toMap().containsKey(ID);
    }

    /**
     * Método responsável por indicar se numa requisição não há campo de uso.
     *
     * @return Retorna indicativo de que numa requisição não há campo de uso.
     */
    private boolean notContainsUsage() {
        return !request.toMap().containsKey(USAGE);
    }

    /**
     * Método responsável por indicar se numa requisição não há campo de
     * localização.
     *
     * @return Retorna indicativo de que numa requisição não há campo de
     * localização.
     */
    private boolean notContainsLocation() {
        return !request.toMap().containsKey(LOCATION);
    }

    /**
     * Método responsável por indicar se numa requisição não há campo de
     * localização correspondente.
     *
     * @return Retorna indicativo de que numa requisição não há campo de
     * localização correspondente.
     */
    private boolean notContainsCorrespondingLocation() {
        if (userNotFound()) {
            return true;
        }
        final String requestLocation = request.getString(LOCATION);
        final String recycleBinLocation = recycleBinDataMap.get(recycleBinId).getString(LOCATION);
        return !requestLocation.equals(recycleBinLocation);
    }

    /**
     * Método responsável por indicar o id de usuário contido numa requisição
     * foi encontrado.
     *
     * @return Retorna indicativo de que o id de usuário contido numa requisição
     * foi encontrado.
     */
    private boolean userFound() {
        return recycleBinDataMap.containsKey(recycleBinId);
    }

    /**
     * Método responsável por indicar o id de usuário contido numa requisição
     * não foi encontrado.
     *
     * @return Retorna indicativo de que o id de usuário contido numa requisição
     * não foi encontrado.
     */
    private boolean userNotFound() {
        return !userFound();
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
        msg.put(ALL_IDS, getAllIds());
        response.flush();
        return msg.toString();
    }

    /**
     * Método responsável por tratar de requisições que visam buscar dados de
     * uma lixeira.
     *
     * @return Retorna resposta em JSON com os dados de uma lixeira.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    private String getRequest() {
        final Map<String, Object> dataUser = recycleBinDataMap.get(recycleBinId).toMap();
        final JSONObject msg = new JSONObject();
        msg.put(STATUS, FOUND);
        msg.put(USAGE, dataUser.get(USAGE));
        msg.put(IS_PRIORITY, dataUser.get(IS_PRIORITY));
        msg.put(LOCATION, dataUser.get(LOCATION));
        msg.put(IS_BLOCKED, dataUser.get(IS_BLOCKED));
        msg.put(ALL_IDS, getAllIds());
        return msg.toString();
    }

    /**
     * Método responsável por tratar de requisições que visam alterar os dados
     * de uma lixeira ou caminhão de lixo.
     *
     * @return Retorna resposta em JSON com os dados de uma recém criada
     * lixeira.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    private String putRequest() throws IOException {
        System.out.println("GARBAGE_TRUCK: ".concat(request.toString()));
        switch (request.getString(DEVICE)) {
            case "RECYCLE_BIN":
                final JSONObject dataUser = recycleBinDataMap.get(recycleBinId);
                dataUser.put(CLEAR, request.getString(CLEAR));
                return getRequest();
            case "GARBAGE_TRUCK":
                garbageTruckDataJSON.put(USAGE, request.getString(USAGE));
                garbageTruckDataJSON.put(LOCATION, request.getString(LOCATION));
                final JSONObject msg = new JSONObject();
                msg.put(STATUS, FOUND);
                return msg.toString();
            default:
                return unsuccessfulRequest(BAD_REQUEST);
        }
    }

    /**
     * Método responsável por de forma ordenada retornar todos os IDs de lixeira
     * contidos no sistema.
     *
     * @return Retorna todos os IDs de lixeira contidos no sistema.
     */
    private String getAllIds() {
        final MyPriorityQueue<Entry<String, JSONObject>> entryQueue;
        entryQueue = new MyPriorityQueue<>(new RecycleBinComparator());
        recycleBinDataMap.entrySet().forEach(entry -> {
            if (entry.getValue().getString(LOCATION).equals(request.getString(LOCATION))) {
                if (entry.getValue().getString(IS_BLOCKED).equals("FALSE")) {
                    if (Double.parseDouble(entry.getValue().getString(USAGE)) > 0) {
                        entryQueue.add(entry);
                    }
                }
            }
        });
        String allRecycleBinsId = "";
        for (final Entry<String, JSONObject> entry : entryQueue) {
            allRecycleBinsId += entry.getKey().concat(";");
        }
        return allRecycleBinsId.replaceFirst(".$", "");
    }

    /**
     * Método responsável por criar dados do consumidor.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public void post() throws IOException {
        response.writeUTF(
                notContainsID() || notContainsLocation() || notContainsDevice()
                        ? unsuccessfulRequest(BAD_REQUEST)
                        : notContainsCorrespondingLocation()
                                ? unsuccessfulRequest(NOT_FOUND)
                                : putRequest()
        );
    }

    /**
     * Método responsável por buscar dados do consumidor.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public void get() throws IOException {
        response.writeUTF(notContainsID() || notContainsLocation()
                ? unsuccessfulRequest(BAD_REQUEST)
                : notContainsCorrespondingLocation()
                        ? unsuccessfulRequest(NOT_FOUND)
                        : getRequest()
        );
    }

    /**
     * Método responsável por atualizar dados do consumidor.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public void put() throws IOException {
        response.writeUTF(
                notContainsLocation() || notContainsDevice() || notContainsUsage()
                        ? unsuccessfulRequest(BAD_REQUEST)
                        : putRequest()
        );
    }

    /**
     * Método responsável por apagar dados do consumidor.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public void delete() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
