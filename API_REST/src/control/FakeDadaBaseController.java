package control;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import static util.Constants.*;

public class FakeDadaBaseController {

    private static FakeDadaBaseController instance;
    private final Map<String, JSONObject> recycleBinData;
    private final JSONObject garbageTruckData;

    private FakeDadaBaseController() {
        recycleBinData = new HashMap<>();
        garbageTruckData = new JSONObject();
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

    public JSONObject getGarbageTruckData() {
        return garbageTruckData;
    }
    
    @Deprecated
    public void insertFakeRecycle() {
        recycleBinData.put("1", createRecycleDataUser(
                "FALSE",
                "TRUE",
                "0.25",
                "FALSE",
                "123;321")
        );
        recycleBinData.put("2", createRecycleDataUser(
                "TRUE",
                "FALSE",
                "0.25",
                "FALSE",
                "123;321")
        );
        recycleBinData.put("3", createRecycleDataUser(
                "FALSE",
                "FALSE",
                "0.75",
                "FALSE",
                "123;321")
        );
        recycleBinData.put("4", createRecycleDataUser(
                "FALSE",
                "FALSE",
                "0.00",
                "FALSE",
                "123;321")
        );
        recycleBinData.put("5", createRecycleDataUser(
                "FALSE",
                "TRUE",
                "0.25",
                "FALSE",
                "123;321")
        );
    }
    
    @Deprecated
    private static JSONObject createRecycleDataUser(
            final String isBlocked,
            final String isPriority,
            final String usage,
            final String clear,
            final String location
            ) {
        final JSONObject recycleDataUser = new JSONObject();
        recycleDataUser.put(IS_BLOCKED, isBlocked);
        recycleDataUser.put(IS_PRIORITY, isPriority);
        recycleDataUser.put(USAGE, usage);
        recycleDataUser.put(CLEAR, clear);
        recycleDataUser.put(LOCATION, location);
        return recycleDataUser;
    }

}
