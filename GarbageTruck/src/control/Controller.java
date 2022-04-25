package control;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import model.ClientConnection;
import model.ServerConsumer;
import model.RecycleBinAdministrator;
import org.json.JSONObject;
import static util.Constants.*;
import uefs.ComumBase.interfaces.Receiver;
import static uefs.ComumBase.interfaces.Status.*;

/**
 * Classe responsável por comportar-se como controlador de lixeira.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class Controller {

    /**
     * Refere-se a instância singular do controlador de lixeira.
     */
    private static Controller instance;
    /**
     * Refere-se a conexão atual utilizada pela lixeira conectada.
     */
    private ClientConnection currentConnection;
    /**
     * Refere-se ao objeto de requisição utilizado pela lixeira conectada.
     */
    private final ServerConsumer request;
    /**
     * Refere-se a lista de ações a serem tomadas quando uma conexão é alterada.
     */
    private final List<Receiver<Boolean>> actionListChangeConnection;
    /**
     * Refere-se a lista de ações a serem tomadas quando uma lixeira é alterada.
     */
    private final List<Receiver<JSONObject>> actionListChangeRecycle;
    private final RecycleBinAdministrator recycleBinAdministrator;

    /**
     * Construtor responsável por instanciar um controlador de lixeira.
     */
    private Controller() {
        recycleBinAdministrator = new RecycleBinAdministrator();
        request = new ServerConsumer(recycleBinAdministrator);
        actionListChangeConnection = new LinkedList<>();
        actionListChangeRecycle = new LinkedList<>();
    }

    /**
     * Método responsável por retornar instância singular do controlador de
     * lixeira.
     *
     * @return Retorna instância singular do controlador de lixeira.
     */
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    /**
     * Método responsável por adicionar ações a serem tomadas quando uma conexão
     * for alterada.
     *
     * @param action Refere-se a nova ação adicionada a lista de ações a serem
     * tomadas.
     */
    public void addActionChangeConnection(final Receiver<Boolean> action) {
        actionListChangeConnection.add(action);
    }

    /**
     * Método responsável por adicionar ações a serem tomadas quando uma lixeira
     * for alterada.
     *
     * @param action Refere-se a nova ação adicionada a lista de ações a serem
     * tomadas.
     */
    public void addActionChangeRecycle(final Receiver<JSONObject> action) {
        actionListChangeRecycle.add(action);
    }

    /**
     * Método responsável por alterar os dados contidos numa lixeira.
     *
     * @param data Refere-se aos dados da lixeira.
     */
    public void setRecycleBinData(final JSONObject data) {
        recycleBinAdministrator.setRecycleBinData(data);
        actionListChangeRecycle.forEach(action -> action.receive(data));
    }

    /**
     * Método responsável por obiter indicativo de que o controlador está
     * conectado.
     *
     * @return Retorna indicativo de que o controlador está conectado.
     */
    public boolean isConnected() {
        return currentConnection != null;
    }

    /**
     * Método responsável por desconectar o controlador de lixeira.
     */
    public void disconnect() {
        currentConnection = null;
        actionListChangeConnection.forEach(action -> action.receive(isConnected()));
    }

    /**
     * Método responsável por conectar o controlador de lixeira ao servidor.
     *
     * @param ip Refere-se ao IP utilizado na conexão.
     * @param port Refere-se a porta utilizada na conexão.
     * @param latitude Refere-se a latitude da conexão do caminhão.
     * @param longitude Refere-se a longitude da conexão do cainhão.
     * @throws IOException exceção lançada no caso de haver um problema de
     * entrada/saída.
     */
    public void connectToServer(final String ip, final int port, final String latitude, final String longitude) throws IOException {
        final ClientConnection newConnection = new ClientConnection(ip, port, latitude, longitude);
        final JSONObject response = request.get(newConnection);

        if (response.toMap().containsKey(STATUS)) {
            currentConnection = newConnection;
        }
        actionListChangeConnection.forEach(action -> action.receive(isConnected()));
    }

    /**
     * Método responsável por listar os IDs de todas as lixeiras conectadas.
     *
     * @param action Refere-se a ação tomada ao se receber os IDs.
     * @throws IOException exceção lançada no caso de haver um problema de
     * entrada/saída.
     */
    public void listRecycleBins(final Receiver<String[]> action) throws IOException {
        final JSONObject response = request.get(currentConnection);
        if (response.toMap().containsKey(ALL_IDS)) {
            action.receive(response.getString(ALL_IDS).split(";"));
        }
    }

    /**
     * Método responsável por exibir os detalhes atualizados de uma lixeira.
     *
     * @param id Refere-se ao ID da lixeira.
     * @throws IOException exceção lançada no caso de haver um problema de
     * entrada/saída.
     */
    public void showRecycleDetails(final String id) throws IOException {
        recycleBinAdministrator.setRecycleBinId(id);
        final JSONObject response = request.get(currentConnection);
        if (response.getString(STATUS).equals(FOUND)) {
            setRecycleBinData(response);
        } else {
            throw new IOException(response.getString(STATUS));
        }
    }

    private boolean isBlocked(final String id) throws IOException {
        recycleBinAdministrator.setRecycleBinId(id);
        final JSONObject response = request.get(currentConnection);
        return response.getString(STATUS).equals(FOUND)
                ? response.getString(IS_BLOCKED).equals("TRUE")
                : false;
    }
    
    
    private boolean isNotUsed(final String id) throws IOException {
        recycleBinAdministrator.setRecycleBinId(id);
        final JSONObject response = request.get(currentConnection);
        return response.getString(STATUS).equals(FOUND)
                ? Double.parseDouble(response.getString(USAGE)) == 0.00
                : false;
    }

    public void clearRecycle(final String id) throws IOException {
        if (isBlocked(id)) {
            throw new IOException("Lixeira Bloqueada!");
        }
        if (isNotUsed(id)) {
            throw new IOException("Lixeira Esvaziada Antes da Coleta!");
        }
        recycleBinAdministrator.getRecycleBinData().put(CLEAR, "TRUE");
        final JSONObject response = request.post(currentConnection);
        if (response.getString(STATUS).equals(FOUND)) {
            response.put(USAGE, "0.00");
            setRecycleBinData(response);
        } else {
            throw new IOException(response.getString(STATUS));
        }
    }

}
