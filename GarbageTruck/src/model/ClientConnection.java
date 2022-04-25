package model;

/**
 * Classe responsável por comportar-se como cliente de conexão para caminões de
 * lixo.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class ClientConnection extends uefs.ComumBase.classes.ClientConnection {

    /**
     * Refere-se a latitude da conexão.
     */
    public final String latitude;
    /**
     * Refere-se a longitude da conexão.
     */
    public final String longitude;

    /**
     * Construtor responsável por instanciar um cliente de conexão para caminões
     * de lixo.
     *
     * @param ip Refere-se ao IP utilizado na conexão.
     * @param port Refere-se a porta utilizada na conexão.
     * @param latitude Refere-se a latitude da conexão.
     * @param longitude Refere-se a longitude da conexão.
     */
    public ClientConnection(final String ip, final int port, final String latitude, final String longitude) {
        super(ip, port);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Método responsável por retornar a localização de um caminhão.
     *
     * @return Retorna a a localização de um caminhão.
     */
    public String getLocation() {
        return latitude.concat(";").concat(longitude);
    }

}
