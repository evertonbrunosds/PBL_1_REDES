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

public class MainController {

    private static MainController instance;
    final ServerConnection recycleBinsServer;
    final ServerConnection administratorsServer;
    final ServerConnection garbageTruckServer;

    private MainController() {
        recycleBinsServer = new ServerConnection(1990);
        administratorsServer = new ServerConnection(1991);
        garbageTruckServer = new ServerConnection(1992);
    }

    public static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
            FakeDadaBaseController.getInstance().insertFakeRecycle();
        }
        return instance;
    }

    public ServerConnection getRecycleBinsServer() {
        return recycleBinsServer;
    }

    public void listenToRecycleBins(final Treatable<IOException> internalException) throws IOException {
        recycleBinsServer.streamFuture(internalException::toTreat).then(this::listenToRecycleBins);
    }

    public void listenToGarbageTruck(final Treatable<IOException> internalException) throws IOException {
        garbageTruckServer.streamFuture(internalException::toTreat).then(this::listenToGarbageTruck);
    }

    public void listenToAdministrators(final Treatable<IOException> internalException) throws IOException {
        administratorsServer.streamFuture(internalException::toTreat).then(this::listenToAdministrators);
    }

    private void listenToRecycleBins(final DataInputStream inputStream, final DataOutputStream outputStream) throws IOException {
        final JSONObject request = new JSONObject(inputStream.readUTF());
        System.out.println("RECYCLE_BIN: ".concat(request.toString()));
        try {
            runConsumer(new RecycleBin(request, outputStream, FakeDadaBaseController.getInstance().getRecycleBinData()), request);
        } catch (final InterruptedException ex) {
            unsuccessfulRequest(outputStream);
        }
    }

    private void listenToGarbageTruck(final DataInputStream inputStream, final DataOutputStream outputStream) throws IOException {
        final JSONObject request = new JSONObject(inputStream.readUTF());
        System.out.println("GARBAGE_TRUCK: ".concat(request.toString()));
        runConsumer(new GarbageTruck(
                request,
                outputStream,
                FakeDadaBaseController.getInstance().getRecycleBinData(),
                FakeDadaBaseController.getInstance().getGarbageTruckData()
        ), request);
    }

    private void listenToAdministrators(final DataInputStream inputStream, final DataOutputStream outputStream) throws IOException {
        final JSONObject request = new JSONObject(inputStream.readUTF());
        System.out.println("ADMINISTRATOR: ".concat(request.toString()));
        runConsumer(new Administrator(request, outputStream, FakeDadaBaseController.getInstance().getRecycleBinData()), request);
    }

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

    private static void unsuccessfulRequest(final DataOutputStream outputStream) throws IOException {
        final JSONObject msg = new JSONObject();
        msg.put(STATUS, INTERNAL_SERVER_ERROR);
        outputStream.flush();
        outputStream.writeUTF(msg.toString());
    }

}
