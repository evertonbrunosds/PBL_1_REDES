package control;

import java.util.HashMap;
import org.json.JSONObject;

public class FakeDadaBaseController extends HashMap<String, JSONObject> {

    private static FakeDadaBaseController instance;

    private FakeDadaBaseController() {
    }

    public static FakeDadaBaseController getInstance() {
        if (instance == null) {
            instance = new FakeDadaBaseController();
        }
        return instance;
    }

}
