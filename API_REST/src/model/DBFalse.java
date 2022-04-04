package model;

import java.util.HashMap;
import org.json.JSONObject;

public class DBFalse {

    private final HashMap<String, JSONObject> recycleBins;

    public DBFalse() {
        recycleBins = new HashMap<>();
    }

    public HashMap<String, JSONObject> getRecycleBins() {
        return recycleBins;
    }

}
