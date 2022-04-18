package model;

public class ClientConnection extends uefs.ComumBase.classes.ClientConnection{
    public final String latitude;
    public final String longitude;
    
    public ClientConnection(final String ip, final int port, final String latitude, final String longitude) {
        super(ip, port);
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public String getLocation() {
        return latitude.concat(";").concat(longitude);
    }
    
}
