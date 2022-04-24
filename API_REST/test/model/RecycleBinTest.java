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
import uefs.ComumBase.classes.ServerConnection;
import uefs.ComumBase.interfaces.Factory;
import static util.Constants.*;
import uefs.ComumBase.classes.ClientConnection;
import static uefs.ComumBase.interfaces.Status.*;

public class RecycleBinTest {

    RecycleBin recycleBin;
    ServerConnection serverConnection;
    ClientConnection clientConnection;
    final Map<String, JSONObject> dataMap;

    public RecycleBinTest() {
        dataMap = new HashMap<>();
        clientConnection = new ClientConnection("127.0.0.1", 1990);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws IOException {
        serverConnection = new ServerConnection(1990);
    }

    @After
    public void tearDown() throws IOException {
        dataMap.clear();
        serverConnection.close();
    }

    private static JSONObject getDataUser() {
        final JSONObject dataUser = new JSONObject();
        dataUser.put(IS_BLOCKED, "FALSE");
        dataUser.put(USAGE, "NONE");
        dataUser.put(CLEAR, "FALSE");
        dataUser.put(IS_PRIORITY, "FALSE");
        return dataUser;
    }

    private static JSONObject getRequest(final String id, final String method) {
        final JSONObject request = getDataUser();
        request.put(LOCATION, "123;321");
        if (id != null) {
            request.put(ID, id);
        }
        request.put(METHOD, method);
        return request;
    }

    private void failTest(final Exception cause) {
        failTest(cause.getMessage());
    }

    private void failTest(final String cause) {
        fail(cause);
    }

    @Test
    public void getNotRegister() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    try {
                        final JSONObject request = new JSONObject(input.readUTF());
                        recycleBin = new RecycleBin(request, output, dataMap);
                        recycleBin.get();
                    } catch (final InterruptedException ex) {
                        failTest(ex);
                    }
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest(null, GET).toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(NOT_FOUND, response.get(STATUS));
            assertNotNull(response.get(ID));
        });
    }

    @Test
    public void getRegister() throws IOException {
        dataMap.put("3", getDataUser());
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    try {
                        final JSONObject request = new JSONObject(input.readUTF());
                        recycleBin = new RecycleBin(request, output, dataMap);
                        recycleBin.get();
                    } catch (final InterruptedException ex) {
                        failTest(ex);
                    }
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest("3", GET).toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(FOUND, response.get(STATUS));
            assertEquals("FALSE", response.get(IS_BLOCKED));
            assertEquals("NONE", response.get(USAGE));
            assertEquals("FALSE", response.get(CLEAR));
        });
    }

    @Test
    public void putBadRequestRegister() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    try {
                        final JSONObject request = new JSONObject(input.readUTF());
                        recycleBin = new RecycleBin(request, output, dataMap);
                        recycleBin.put();
                    } catch (final InterruptedException ex) {
                        failTest(ex);
                    }
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
            assertNotNull(response.get(ID));
        });
    }

    @Test
    public void putNotFoundRegister() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    try {
                        final JSONObject request = new JSONObject(input.readUTF());
                        recycleBin = new RecycleBin(request, output, dataMap);
                        recycleBin.put();
                    } catch (final InterruptedException ex) {
                        failTest(ex);
                    }
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest("144", PUT).toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(NOT_FOUND, response.get(STATUS));
            assertNotNull(response.get(ID));
        });
    }

    @Test
    public void putRegister() throws IOException {
        dataMap.put("3", getDataUser());
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    try {
                        final JSONObject request = new JSONObject(input.readUTF());
                        recycleBin = new RecycleBin(request, output, dataMap);
                        recycleBin.put();
                    } catch (final InterruptedException ex) {
                        failTest(ex);
                    }
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            final JSONObject request = getRequest("3", PUT);
            request.put(IS_BLOCKED, "TRUE"); // NÃO HÁ PERMISSÃO PARA ALTERAR
            request.put(USAGE, "TOTAL"); // HÁ PERMISSÃO PARA ALTERAR
            request.put(CLEAR, "TRUE"); // NÃO HÁ PERMISSÃO PARA ALTERAR
            request.put(IS_PRIORITY, "TRUE"); // NÃO HÁ PERMISSÃO PARA ALTERAR
            output.writeUTF(request.toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(FOUND, response.get(STATUS));
            assertEquals("FALSE", response.get(IS_BLOCKED));
            assertEquals("TOTAL", response.get(USAGE));
            assertEquals("FALSE", response.get(CLEAR));
        });
    }

    @Test
    public void deleteBadRequestRegister() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    try {
                        final JSONObject request = new JSONObject(input.readUTF());
                        recycleBin = new RecycleBin(request, output, dataMap);
                        recycleBin.delete();
                    } catch (final InterruptedException ex) {
                        failTest(ex);
                    }
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
            assertNotNull(response.get(ID));
        });
    }

    @Test
    public void deleteNotFoundRegister() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    try {
                        final JSONObject request = new JSONObject(input.readUTF());
                        recycleBin = new RecycleBin(request, output, dataMap);
                        recycleBin.delete();
                    } catch (final InterruptedException ex) {
                        failTest(ex);
                    }
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest("144", DELETE).toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(NOT_FOUND, response.get(STATUS));
            assertNotNull(response.get(ID));
        });
    }

    @Test
    public void deleteRegister() throws IOException {
        dataMap.put("3", getDataUser());
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    try {
                        final JSONObject request = new JSONObject(input.readUTF());
                        recycleBin = new RecycleBin(request, output, dataMap);
                        recycleBin.delete();
                    } catch (final InterruptedException ex) {
                        failTest(ex);
                    }
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest("3", DELETE).toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(FOUND, response.get(STATUS));
            assertEquals("FALSE", response.get(IS_BLOCKED));
            assertEquals("NONE", response.get(USAGE));
            assertEquals("FALSE", response.get(CLEAR));
            assertFalse(dataMap.containsKey("3"));
        });
    }

    @Test
    public void postBadRequestRegister() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    try {
                        final JSONObject request = new JSONObject(input.readUTF());
                        recycleBin = new RecycleBin(request, output, dataMap);
                        recycleBin.post();
                    } catch (final InterruptedException ex) {
                        failTest(ex);
                    }
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
        });
    }
    
    @Test
    public void postNotRegister() throws IOException {
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    try {
                        final JSONObject request = new JSONObject(input.readUTF());
                        recycleBin = new RecycleBin(request, output, dataMap);
                        recycleBin.post();
                    } catch (final InterruptedException ex) {
                        failTest(ex);
                    }
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest("1997", POST).toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(NOT_FOUND, response.get(STATUS));
            assertEquals("FALSE", response.get(IS_BLOCKED));
            assertEquals("NONE", response.get(USAGE));
            assertEquals("FALSE", response.get(CLEAR));
        });
    }

    @Test
    public void postRegister() throws IOException {
        dataMap.put("3", getDataUser());
        Factory.thread(() -> {
            try {
                serverConnection.streamFuture(this::failTest).then((input, output) -> {
                    try {
                        final JSONObject request = new JSONObject(input.readUTF());
                        recycleBin = new RecycleBin(request, output, dataMap);
                        recycleBin.post();
                    } catch (final InterruptedException ex) {
                        failTest(ex);
                    }
                });
            } catch (final IOException ex) {
                failTest(ex);
            }
        }).start();
        clientConnection.streamBuilder((input, output) -> {
            output.flush();
            output.writeUTF(getRequest("3", POST).toString());
            final JSONObject response = new JSONObject(input.readUTF());
            assertEquals(FOUND, response.get(STATUS));
            assertEquals("3", response.get(ID));
            assertTrue(dataMap.containsKey("3"));
        });
    }

}
