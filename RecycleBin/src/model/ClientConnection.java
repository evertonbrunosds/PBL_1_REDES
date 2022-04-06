package model;

public class ClientConnection extends uefs.ComumBase.classes.ClientConnection {

    private static final String UNDETERMINED = "UNDETERMINED";
    private String id;
    

    public ClientConnection(final String ip, final int port) {
        super(ip, port);
        this.id = UNDETERMINED;
    }
    
    public ClientConnection(final String id, final String ip, final int port) {
        super(ip, port);
        this.id = (id == null) ? UNDETERMINED : id;
    }

    public void setId(final String id) {
        this.id = (id == null) ? UNDETERMINED : id;
    }

    public String getId() {
        return id;
    }

}
