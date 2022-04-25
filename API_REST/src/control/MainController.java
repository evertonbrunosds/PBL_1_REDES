package control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import model.Administrator;
import model.GarbageTruck;
import static util.Constants.DELETE;
import static util.Constants.METHOD;
import static util.Constants.POST;
import static util.Constants.PUT;
import static util.Constants.STATUS;
import org.json.JSONObject;
import uefs.ComumBase.classes.ServerConnection;
import uefs.ComumBase.interfaces.Treatable;
import model.RecycleBin;
import uefs.ComumBase.interfaces.ClientConsumer;
import static uefs.ComumBase.interfaces.Status.INTERNAL_SERVER_ERROR;

/**
 * Classe responsável por comportar-se como controlador principal da API.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class MainController {

    /**
     * Refere-se a instância singular do controlador principal da API.
     */
    private static MainController instance;
    /**
     * Refere-se a conexão servidor das lixeiras.
     */
    final ServerConnection recycleBinsServer;
    /**
     * Refere-se a conexão servidor dos administradores.
     */
    final ServerConnection administratorsServer;
    /**
     * Refere-se a conexão servidor dos caminhões de lixo.
     */
    final ServerConnection garbageTruckServer;

    /**
     * Construtor responsável pelo instanciamento singular do controlador
     * principal da API.
     */
    private MainController() {
        recycleBinsServer = new ServerConnection(1990);
        administratorsServer = new ServerConnection(1991);
        garbageTruckServer = new ServerConnection(1992);
    }

    /**
     * Método responsável por retornar instânca singular do controlador
     * principal da API.
     *
     * @return
     */
    public static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    /**
     * Método responsável por atender aos chamados dos clientes lixeira.
     *
     * @param internalException Refere-se ao modo como devem ser tratadas as
     * exceções internas.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public void listenToRecycleBins(final Treatable<IOException> internalException) throws IOException {
        recycleBinsServer.streamFuture(internalException::toTreat).then(this::listenToRecycleBins);
    }

    /**
     * Método responsável por atender aos chamados dos clientes caminhão.
     *
     * @param internalException Refere-se ao modo como devem ser tratadas as
     * exceções internas.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public void listenToGarbageTruck(final Treatable<IOException> internalException) throws IOException {
        garbageTruckServer.streamFuture(internalException::toTreat).then(this::listenToGarbageTruck);
    }

    /**
     * Método responsável por atender aos chamados dos clientes administradores.
     *
     * @param internalException Refere-se ao modo como devem ser tratadas as
     * exceções internas.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public void listenToAdministrators(final Treatable<IOException> internalException) throws IOException {
        administratorsServer.streamFuture(internalException::toTreat).then(this::listenToAdministrators);
    }

    /**
     * Método responsável por encaminhar os clientes lixeira ao método rest
     * correto.
     *
     * @param inputStream Refere-se ao objeto de entrada de dados.
     * @param outputStream Refere-se ao objeto de saída de dados.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    private void listenToRecycleBins(final DataInputStream inputStream, final DataOutputStream outputStream) throws IOException {
        final JSONObject request = new JSONObject(inputStream.readUTF());
        try {
            runConsumer(new RecycleBin(request, outputStream, FakeDadaBaseController.getInstance().getRecycleBinData()), request);
        } catch (final InterruptedException ex) {
            unsuccessfulRequest(outputStream);
        }
    }

    /**
     * Método responsável por encaminhar os clientes caminhão ao método rest
     * correto.
     *
     * @param inputStream Refere-se ao objeto de entrada de dados.
     * @param outputStream Refere-se ao objeto de saída de dados.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    private void listenToGarbageTruck(final DataInputStream inputStream, final DataOutputStream outputStream) throws IOException {
        final JSONObject request = new JSONObject(inputStream.readUTF());
        runConsumer(new GarbageTruck(
                request,
                outputStream,
                FakeDadaBaseController.getInstance().getRecycleBinData(),
                FakeDadaBaseController.getInstance().getGarbageTruckData()
        ), request);
    }

    /**
     * Método responsável por encaminhar os clientes administradores ao método
     * rest correto.
     *
     * @param inputStream Refere-se ao objeto de entrada de dados.
     * @param outputStream Refere-se ao objeto de saída de dados.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    private void listenToAdministrators(final DataInputStream inputStream, final DataOutputStream outputStream) throws IOException {
        final JSONObject request = new JSONObject(inputStream.readUTF());
        runConsumer(new Administrator(request, outputStream, FakeDadaBaseController.getInstance().getRecycleBinData()), request);
    }

    /**
     * Método responsável por consumar a execução do método rest correto para
     * cada cliente do servidor, idependente de qual seja.
     *
     * @param clientConsumer Refere-se ao cliente a ter sua requisição
     * consumada.
     * @param request Refere-se a requisição efetuada pelo dito cliente.
     * @throws IOException
     */
    private static void runConsumer(final ClientConsumer clientConsumer, final JSONObject request) throws IOException {
        if (request.toMap().containsKey(METHOD)) {
            switch (request.getString(METHOD)) {
                case POST:
                    clientConsumer.post();
                    break;
                case PUT:
                    clientConsumer.put();
                    break;
                case DELETE:
                    clientConsumer.delete();
                    break;
                default:
                    clientConsumer.get();
                    break;
            }
        } else {
            clientConsumer.get();
        }
    }

    /**
     * Método responsável por indicar que o servidor falhou ao processar dada
     * requisição.
     *
     * @param outputStream Refere-se ao objeto de saída de dados.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    private static void unsuccessfulRequest(final DataOutputStream outputStream) throws IOException {
        final JSONObject msg = new JSONObject();
        msg.put(STATUS, INTERNAL_SERVER_ERROR);
        outputStream.flush();
        outputStream.writeUTF(msg.toString());
    }

}
