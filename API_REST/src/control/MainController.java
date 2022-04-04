package control;

import java.io.IOException;
import uefs.ComumBase.interfaces.ServerConnection;
import uefs.ComumBase.interfaces.Treatable;

public class MainController {

    private static MainController instance;
    final ServerConnection serverConnection;

    private MainController() {
        serverConnection = ServerConnection.builder(1997);
    }

    public static MainController getInstance() {
        if (instance != null) {
            instance = new MainController();
        }
        return instance;
    }

    public void listenToClients(final Treatable<IOException> internalTratament) throws IOException {
        serverConnection.streamFuture(internalTratament::toTreate).then((inputStream, outputStream) -> {
            
        });
    }

}
