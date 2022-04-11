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
        dataMap.put("3", gerateUser("FALSE", "TRUE"));
        dataMap.get("3").put(USAGE, "0");
        dataMap.put("5", gerateUser("TRUE", "FALSE"));
        dataMap.get("5").put(USAGE, "25");
        dataMap.put("7", gerateUser("FALSE", "TRUE"));
        dataMap.get("7").put(USAGE, "50");
        dataMap.put("11", gerateUser("TRUE", "FALSE"));
        dataMap.get("11").put(USAGE, "75");
        dataMap.put("13", gerateUser("TRUE", "TRUE"));
        dataMap.get("13").put(USAGE, "100");
    }

    @After
    public void tearDown() throws IOException {
        dataMap.clear();
        serverConnection.close();
    }

    private void failTest(final Exception cause) {
        failTest(cause.getMessage());
    }

    private void failTest(final String cause) {
        fail(cause);
    }

    private static JSONObject gerateUser(final String isBlocked, final String priority) {
        final JSONObject dataUser = new JSONObject();
        dataUser.put(IS_BLOCKED, isBlocked);
        dataUser.put(IS_PRIORITY, priority);
        return dataUser;
    }

    private static JSONObject getRequest(final String id, final String method, final String isBlocked, final String priority) {
        final JSONObject request = gerateUser(isBlocked, priority);
        request.put(METHOD, method);
        if (id != null) {
            request.put(ID, id);
        }
        return request;
    }

    private static JSONObject getRequest(final String id, final String method) {
        final JSONObject request = new JSONObject();
        request.put(METHOD, method);
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
                    administrator = new Administrator(request, output, dataMap);
                    administrator.get();
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest(null, GET).toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(BAD_REQUEST, response.get(STATUS));
            assertEquals("11", response.getString(ALL_IDS).split(";")[0]);
            assertEquals("13", response.getString(ALL_IDS).split(";")[1]);
            assertEquals("3", response.getString(ALL_IDS).split(";")[2]);
            assertEquals("5", response.getString(ALL_IDS).split(";")[3]);
            assertEquals("7", response.getString(ALL_IDS).split(";")[4]);
        });
    }

    @Test
    public void getNotNullIDRegister() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    final JSONObject request = new JSONObject(input.readUTF());
                    administrator = new Administrator(request, output, dataMap);
                    administrator.get();
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest("222", GET).toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(NOT_FOUND, response.get(STATUS));
            assertEquals("11", response.getString(ALL_IDS).split(";")[0]);
            assertEquals("13", response.getString(ALL_IDS).split(";")[1]);
            assertEquals("3", response.getString(ALL_IDS).split(";")[2]);
            assertEquals("5", response.getString(ALL_IDS).split(";")[3]);
            assertEquals("7", response.getString(ALL_IDS).split(";")[4]);
        });
    }

    @Test
    public void getIDFoundRegister() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    final JSONObject request = new JSONObject(input.readUTF());
                    administrator = new Administrator(request, output, dataMap);
                    administrator.get();
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest("5", GET).toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(FOUND, response.getString(STATUS));
            assertEquals("TRUE", response.getString(IS_BLOCKED));
            assertEquals("25", response.getString(USAGE));
            assertEquals("FALSE", response.getString(IS_PRIORITY));
        });
    }

    @Test
    public void deleteNullIDRegister() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    final JSONObject request = new JSONObject(input.readUTF());
                    administrator = new Administrator(request, output, dataMap);
                    administrator.delete();
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest(null, DELETE).toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(BAD_REQUEST, response.get(STATUS));
            assertEquals("11", response.getString(ALL_IDS).split(";")[0]);
            assertEquals("13", response.getString(ALL_IDS).split(";")[1]);
            assertEquals("3", response.getString(ALL_IDS).split(";")[2]);
            assertEquals("5", response.getString(ALL_IDS).split(";")[3]);
            assertEquals("7", response.getString(ALL_IDS).split(";")[4]);
        });
    }

    @Test
    public void deleteNotNullIDRegister() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    final JSONObject request = new JSONObject(input.readUTF());
                    administrator = new Administrator(request, output, dataMap);
                    administrator.delete();
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest("222", DELETE).toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(NOT_FOUND, response.get(STATUS));
            assertEquals("11", response.getString(ALL_IDS).split(";")[0]);
            assertEquals("13", response.getString(ALL_IDS).split(";")[1]);
            assertEquals("3", response.getString(ALL_IDS).split(";")[2]);
            assertEquals("5", response.getString(ALL_IDS).split(";")[3]);
            assertEquals("7", response.getString(ALL_IDS).split(";")[4]);
        });
    }

    @Test
    public void deleteIDFoundRegister() throws IOException {
        assertTrue(dataMap.containsKey("5"));
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    final JSONObject request = new JSONObject(input.readUTF());
                    administrator = new Administrator(request, output, dataMap);
                    administrator.delete();
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest("5", GET).toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(FOUND, response.getString(STATUS));
            assertEquals("TRUE", response.getString(IS_BLOCKED));
            assertEquals("25", response.getString(USAGE));
            assertEquals("FALSE", response.getString(IS_PRIORITY));
            assertFalse(dataMap.containsKey("5"));
        });
    }

    @Test
    public void putNullIDRegister() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    final JSONObject request = new JSONObject(input.readUTF());
                    administrator = new Administrator(request, output, dataMap);
                    administrator.put();
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest(null, PUT).toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(BAD_REQUEST, response.get(STATUS));
            assertEquals("11", response.getString(ALL_IDS).split(";")[0]);
            assertEquals("13", response.getString(ALL_IDS).split(";")[1]);
            assertEquals("3", response.getString(ALL_IDS).split(";")[2]);
            assertEquals("5", response.getString(ALL_IDS).split(";")[3]);
            assertEquals("7", response.getString(ALL_IDS).split(";")[4]);
        });
    }

    @Test
    public void putNotNullIDRegister() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    final JSONObject request = new JSONObject(input.readUTF());
                    administrator = new Administrator(request, output, dataMap);
                    administrator.put();
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest("1700", PUT, "FALSE", "TRUE").toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(NOT_FOUND, response.get(STATUS));
            assertEquals("11", response.getString(ALL_IDS).split(";")[0]);
            assertEquals("13", response.getString(ALL_IDS).split(";")[1]);
            assertEquals("3", response.getString(ALL_IDS).split(";")[2]);
            assertEquals("5", response.getString(ALL_IDS).split(";")[3]);
            assertEquals("7", response.getString(ALL_IDS).split(";")[4]);
        });
    }

    @Test
    public void putIDFoundRegister() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    final JSONObject request = new JSONObject(input.readUTF());
                    administrator = new Administrator(request, output, dataMap);
                    administrator.put();
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest("5", PUT, "FALSE", "TRUE").toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(FOUND, response.getString(STATUS));
            assertEquals("FALSE", response.getString(IS_BLOCKED));
            assertEquals("25", response.getString(USAGE));
            assertEquals("TRUE", response.getString(IS_PRIORITY));
        });
    }

    @Test
    public void postNullIDRegister() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    final JSONObject request = new JSONObject(input.readUTF());
                    administrator = new Administrator(request, output, dataMap);
                    administrator.post();
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest(null, POST).toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(BAD_REQUEST, response.get(STATUS));
            assertEquals("11", response.getString(ALL_IDS).split(";")[0]);
            assertEquals("13", response.getString(ALL_IDS).split(";")[1]);
            assertEquals("3", response.getString(ALL_IDS).split(";")[2]);
            assertEquals("5", response.getString(ALL_IDS).split(";")[3]);
            assertEquals("7", response.getString(ALL_IDS).split(";")[4]);
        });
    }

    @Test
    public void postNotNullIDRegister() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    final JSONObject request = new JSONObject(input.readUTF());
                    administrator = new Administrator(request, output, dataMap);
                    administrator.post();
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest("1500", POST, "TRUE", "FALSE").toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(NOT_FOUND, response.get(STATUS));
            assertTrue(dataMap.containsKey("1500"));
            assertEquals("TRUE", dataMap.get("1500").getString(IS_BLOCKED));
            assertEquals("FALSE", dataMap.get("1500").getString(IS_PRIORITY));
            assertEquals("0", dataMap.get("1500").getString(USAGE));
        });
    }

    @Test
    public void postIDFoundRegister() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    final JSONObject request = new JSONObject(input.readUTF());
                    administrator = new Administrator(request, output, dataMap);
                    administrator.post();
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest("5", POST, "TRUE", "FALSE").toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(FOUND, response.getString(STATUS));
            assertEquals("11", response.getString(ALL_IDS).split(";")[0]);
            assertEquals("13", response.getString(ALL_IDS).split(";")[1]);
            assertEquals("3", response.getString(ALL_IDS).split(";")[2]);
            assertEquals("5", response.getString(ALL_IDS).split(";")[3]);
            assertEquals("7", response.getString(ALL_IDS).split(";")[4]);
        });
    }
}
