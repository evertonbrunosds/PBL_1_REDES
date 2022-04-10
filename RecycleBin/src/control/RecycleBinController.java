package control;

import java.io.IOException;
import javax.imageio.IIOException;
import model.RecycleBin;
import org.json.JSONObject;
import model.ClientConnection;
import model.ServerConsumer;
import static uefs.ComumBase.interfaces.Status.*;
import static model.Constants.*;

/**
 * Classe responsável por comportar-se como controlador de lixeira.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class RecycleBinController extends RecycleBin {

    /**
     * Refere-se a instância singular do controlador de lixeira.
     */
    private static RecycleBinController instance;
    /**
     * Refere-se a conexão atualmente utilizada pelo controlador de lixeira.
     */
    private ClientConnection currentConnection;
    /**
     * Refere-se ao objeto responsável por efetuar requisições ao servidor.
     */
    private final ServerConsumer request;

    /**
     * Construtor responsável por manter a integridade de instância singular do
     * singleton.
     */
    private RecycleBinController() {
        request = new ServerConsumer(this);
        currentConnection = null;
    }

    /**
     * Método responsável por obiter a instância singular do controlador de
     * lixeira.
     *
     * @return Retorna a instância singular do controlador de lixeira.
     */
    public static RecycleBinController getInstance() {
        if (instance == null) {
            instance = new RecycleBinController();
        }
        return instance;
    }

    /**
     * Método responsável por obiter o número de identificação da conexão
     * utilizada pela lixeira no servidor atual.
     *
     * @return Retorna o número de identificação da conexão utilizada pela
     * lixeira no servidor atual.
     */
    public String getId() {
        return (currentConnection == null) ? UNDETERMINED : currentConnection.getId();
    }
    
    public boolean isConnected() {
        return (currentConnection == null) ? false : !UNDETERMINED.equals(currentConnection.getId());
    }

    /**
     * Método responsável por desconectar o controlador de lixeira do servidor.
     *
     * @throws IOException Exceção lançada para o caso do controlador não ser
     * capaz de desconectar-se do servidor com êxito.
     */
    public void disconnect() throws IOException {
        if (currentConnection != null) {
            final JSONObject response = request.delete(currentConnection);
            currentConnection = null;
            if (!response.getString(STATUS).equals(OK)) {
                throw new IIOException(response.getString(STATUS));
            }
        }
    }

    /**
     * Método responsável por conectar o controlador de lixeira ao servidor.
     *
     * @param ip Refere-se ao número de IP utilizado pelo servidor.
     * @param port Refere-se a porta utilizada pelo servidor.
     * @throws IOException Exceção lançada para o caso do controlador não ser
     * capaz de desconectar-se do servidor com êxito.
     */
    public void connectToServer(final String ip, final int port) throws IOException {
        final ClientConnection newConnection = new ClientConnection(ip, port);
        final JSONObject response = request.put(newConnection);
        switch (response.getString(STATUS)) {
            case OK:
                disconnect();
                currentConnection = newConnection;
                break;
            case NOT_FOUND:
                newConnection.setId(response.getString(ID));
                signUp(newConnection);
                break;
            case INTERNAL_SERVER_ERROR:
                throw new IOException(INTERNAL_SERVER_ERROR);
            case BAD_REQUEST:
                throw new IOException(BAD_REQUEST);
            default:
                throw new IOException(response.getString(STATUS));
        }
    }

    /**
     * Método responsável por registrar uma lixeira no servidor.
     *
     * @param connection Refere-se a conexão utilizada para fazer o registro.
     * @throws IOException Exceção lançada para o caso do controlador não ser
     * capaz de registrar uma lixeira no servidor com êxito.
     */
    private void signUp(final ClientConnection connection) throws IOException {
        final JSONObject response = request.post(connection);
        if (response.getString(STATUS).equals(OK)) {
            disconnect();
            currentConnection = connection;
        } else {
            throw new IOException(response.getString(STATUS));
        }
    }
}
