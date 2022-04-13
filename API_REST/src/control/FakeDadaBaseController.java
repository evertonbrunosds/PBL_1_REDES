package control;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class FakeDadaBaseController {

    private static FakeDadaBaseController instance;
    private final Map<String, JSONObject> recycleBinData;
    private final Map<String, JSONObject> garbageTruckData;

    private FakeDadaBaseController() {
        recycleBinData = new HashMap<>();
        garbageTruckData = new HashMap<>();
    }

    public static FakeDadaBaseController getInstance() {
        if (instance == null) {
            instance = new FakeDadaBaseController();
        }
        return instance;
    }

    public Map<String, JSONObject> getRecycleBinData() {
        return recycleBinData;
    }

    public Map<String, JSONObject> getGarbageTruckData() {
        return garbageTruckData;
    }

}
