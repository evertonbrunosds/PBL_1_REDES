package model;

import org.json.JSONObject;

public class RecycleBin {
    private String id;
    private JSONObject data;

    public RecycleBin() {
        
    }
    
    public boolean hasId() {
        return id != null;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(final JSONObject data) {
        this.data = data;
    }
    
}
