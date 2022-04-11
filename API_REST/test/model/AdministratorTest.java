package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static model.Constants.*;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uefs.ComumBase.classes.ClientConnection;
import uefs.ComumBase.classes.ServerConnection;
import uefs.ComumBase.interfaces.Factory;
import static uefs.ComumBase.interfaces.Status.*;

public class AdministratorTest {

    ServerConnection serverConnection;
    ClientConnection clientConnection;
    final Map<String, JSONObject> dataMap;
    Administrator administrator;

    public AdministratorTest() {
        dataMap = new HashMap<>();
        clientConnection = new ClientConnection("127.0.0.1", 1991);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        serverConnection = new ServerConnection(1991);
        dataMap.put("3", gerateUser("FALSE", "NONE", "TRUE"));
        dataMap.put("5", gerateUser("TRUE", "LOW", "FALSE"));
        dataMap.put("7", gerateUser("FALSE", "MEDIUM", "TRUE"));
        dataMap.put("11", gerateUser("TRUE", "HIGH", "FALSE"));
        dataMap.put("13", gerateUser("TRUE", "TOTAL", "TRUE"));
    }

    @After
    public void tearDown() throws IOException {
        dataMap.clear();
        serverConnection.close();
    }

    private static JSONObject gerateUser(final String isBlocked, final String usage, final String priority) {
        final JSONObject dataUser = new JSONObject();
        dataUser.put(IS_BLOCKED, isBlocked);
        dataUser.put(USAGE, usage);
        dataUser.put(PRIORITY, priority);
        return dataUser;
    }

}
