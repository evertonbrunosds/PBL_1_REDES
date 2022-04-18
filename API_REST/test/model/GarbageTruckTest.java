package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
import static util.Constants.*;
import static uefs.ComumBase.interfaces.Status.*;

public class GarbageTruckTest {

    private ServerConnection serverConnection;
    private final ClientConnection clientConnection;
    final Map<String, JSONObject> recycleBinDataMap;
    private GarbageTruck garbageTruck;
    private final JSONObject garbageTruckDataSJON;

    public GarbageTruckTest() {
        recycleBinDataMap = new HashMap<>();
        clientConnection = new ClientConnection("127.0.0.1", 1992);
        garbageTruckDataSJON = new JSONObject();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        serverConnection = new ServerConnection(1992);
        garbageTruckDataSJON.put(USAGE, "5.0");
        garbageTruckDataSJON.put(LOCATION, "123;321");
        recycleBinDataMap.put("2", gerateRecycleBin("TRUE", "0.25", "TRUE", "123;321")); //->NÃO É EXIBIDO POR ESTAR BLOQUEADO
        recycleBinDataMap.put("3", gerateRecycleBin("FALSE", "0.25", "TRUE", "123;321"));
        recycleBinDataMap.put("4", gerateRecycleBin("FALSE", "0.00", "TRUE", "123;321")); //->NÃO É EXIBIDO POR NÃO TER USO
        recycleBinDataMap.put("5", gerateRecycleBin("FALSE", "0.25", "TRUE", "123;321"));
        recycleBinDataMap.put("6", gerateRecycleBin("FALSE", "0.25", "TRUE", "567;765")); //->NÃO É EXIBIDO POR NÃO ESTAR NA LOCALIZAÇÃO CORRETA
        recycleBinDataMap.put("7", gerateRecycleBin("FALSE", "1.0", "FALSE", "123;321"));
        recycleBinDataMap.put("11", gerateRecycleBin("FALSE", "0.50", "TRUE", "123;321"));
        recycleBinDataMap.put("13", gerateRecycleBin("FALSE", "0.75", "FALSE", "123;321"));
    }

    @After
    public void tearDown() throws IOException {
        recycleBinDataMap.clear();
        garbageTruckDataSJON.clear();
        serverConnection.close();
    }

    private void failTest(final Exception cause) {
        failTest(cause.getMessage());
    }

    private void failTest(final String cause) {
        fail(cause);
    }

    private static JSONObject gerateRecycleBin(final String isBlocked, final String usage, final String priority, final String location) {
        final JSONObject dataUser = new JSONObject();
        dataUser.put(IS_BLOCKED, isBlocked);
        dataUser.put(USAGE, usage);
        dataUser.put(IS_PRIORITY, priority);
        dataUser.put(LOCATION, location);
        dataUser.put(CLEAR, "FALSE");
        return dataUser;
    }

    private static JSONObject getRequest(final String id, final String method, final String location) {
        final JSONObject request = new JSONObject();
        request.put(METHOD, method);
        request.put(LOCATION, location);
        if (id != null) {
            request.put(ID, id);
        }
        return request;
    }

    @Test
    public void getNullIDRegister() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    final JSONObject request = new JSONObject(input.readUTF());
                    garbageTruck = new GarbageTruck(request, output, recycleBinDataMap, garbageTruckDataSJON);
                    garbageTruck.get();
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest(null, GET, "123;321").toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(BAD_REQUEST, response.get(STATUS));
            assertEquals("11", response.getString(ALL_IDS).split(";")[0]);
            assertEquals("7", response.getString(ALL_IDS).split(";")[1]);
            assertEquals("5", response.getString(ALL_IDS).split(";")[2]);
            assertEquals("3", response.getString(ALL_IDS).split(";")[3]);
            assertEquals("13", response.getString(ALL_IDS).split(";")[4]);
        });
    }

    @Test
    public void getNotFoundIDRegister() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    final JSONObject request = new JSONObject(input.readUTF());
                    garbageTruck = new GarbageTruck(request, output, recycleBinDataMap, garbageTruckDataSJON);
                    garbageTruck.get();
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest("577", GET, "123;321").toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(NOT_FOUND, response.get(STATUS));
            assertEquals("11", response.getString(ALL_IDS).split(";")[0]);
            assertEquals("7", response.getString(ALL_IDS).split(";")[1]);
            assertEquals("5", response.getString(ALL_IDS).split(";")[2]);
            assertEquals("3", response.getString(ALL_IDS).split(";")[3]);
            assertEquals("13", response.getString(ALL_IDS).split(";")[4]);
        });
    }

    @Test
    public void getFoundIDRegister() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    final JSONObject request = new JSONObject(input.readUTF());
                    garbageTruck = new GarbageTruck(request, output, recycleBinDataMap, garbageTruckDataSJON);
                    garbageTruck.get();
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest("5", GET, "123;321").toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(FOUND, response.get(STATUS));
            assertEquals("TRUE", response.getString(IS_PRIORITY));
            assertEquals("0.25", response.getString(USAGE));
            assertEquals("TRUE", response.getString(IS_PRIORITY));
        });
    }

    @Test
    public void updateRecycle() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    final JSONObject request = new JSONObject(input.readUTF());
                    garbageTruck = new GarbageTruck(request, output, recycleBinDataMap, garbageTruckDataSJON);
                    garbageTruck.put();
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        assertEquals("FALSE", recycleBinDataMap.get("5").getString(CLEAR));
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            final JSONObject request = getRequest("5", PUT, "123;321");
            request.put(DEVICE, "RECYCLE_BIN");
            output.writeUTF(request.toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(FOUND, response.get(STATUS));
            assertEquals("TRUE", recycleBinDataMap.get("5").getString(CLEAR));
        });
    }

    @Test
    public void updateGarbageTruck() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    final JSONObject request = new JSONObject(input.readUTF());
                    garbageTruck = new GarbageTruck(request, output, recycleBinDataMap, garbageTruckDataSJON);
                    garbageTruck.post();
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            final JSONObject request = getRequest(null, POST, "123;321");
            request.put(DEVICE, "GARBAGE_TRUCK");
            request.put(USAGE, "75.22");
            output.writeUTF(request.toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(this.garbageTruckDataSJON.getString(USAGE), "75.22");
        });
    }

}
