package interfaces;

import classes.Factory;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import static java.lang.Thread.sleep;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe responsável por fornecer os testes para um objeto de conexão.
 *
 * @author Everton Bruno Silva dos Santos - 1911746
 * @version 1.0
 */
public class ConnectionTest {

    private Connection<DataInputStream, DataOutputStream> connectionClient;
    private Connection<DataInputStream, DataOutputStream> connectionServer;

    public ConnectionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        final int port = 1997;
        connectionClient = Connection.biulder("127.0.0.1", port);
        connectionServer = Connection.biulder(port);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testInputStreamBiulderClient() throws IOException, InterruptedException {
        Factory.Thread.makeFree(() -> {
            try {
                connectionServer.outputStreamBiulder(stream -> {
                    stream.flush();
                    stream.writeUTF("Olá, mundo!");
                });
            } catch (final IOException ex) {
                fail("Falha de entrada/saída");
            }
        }).start();
        sleep(1000);
        connectionClient.inputStreamBiulder(stream -> {
            assertEquals("Olá, mundo!", stream.readUTF());
        });
    }
    
    @Test
    public void testInputStreamBiulderServer() throws IOException, InterruptedException {
        
    }
}
