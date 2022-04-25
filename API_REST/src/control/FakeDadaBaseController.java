package control;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/**
 * Classe responsável por comportar-se como um falso banco de dados.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class FakeDadaBaseController {

    /**
     * Refere-se a instância singular do banco de dados.
     */
    private static FakeDadaBaseController instance;
    /**
     * Refere-se aos dados das lixeiras.
     */
    private final Map<String, JSONObject> recycleBinData;
    /**
     * Refere-se aos dados do caminhão.
     */
    private final JSONObject garbageTruckData;

    /**
     * Construtor responsável por instanciar o falso banco de dados.
     */
    private FakeDadaBaseController() {
        recycleBinData = new HashMap<>();
        garbageTruckData = new JSONObject();
    }

    /**
     * Método responsável por retornar a instância singular do falso banco de
     * dados.
     *
     * @return retorna a instância singular do falso banco de dados.
     */
    public static FakeDadaBaseController getInstance() {
        if (instance == null) {
            instance = new FakeDadaBaseController();
        }
        return instance;
    }

    /**
     * Método responsável por retornar os dados da lixeira.
     *
     * @return
     */
    public Map<String, JSONObject> getRecycleBinData() {
        return recycleBinData;
    }

    /**
     * Método responsável por retornar os dados do caminhão.
     *
     * @return
     */
    public JSONObject getGarbageTruckData() {
        return garbageTruckData;
    }

}
