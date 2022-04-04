package control;

import model.DBFalse;

public class DBFalseController extends DBFalse {

    private static DBFalseController instance;

    private DBFalseController() {
    }

    public static DBFalseController getInstance() {
        if (instance == null) {
            instance = new DBFalseController();
        }
        return instance;
    }

}
