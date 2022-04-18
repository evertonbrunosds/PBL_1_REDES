package model;

/**
 * Classe responsável por comportar-se como conexão de cliente detentora de
 * número de identificação, latitude e longitude.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class ClientConnection extends uefs.ComumBase.classes.ClientConnection {

    /**
     * Refere-se ao número de identificação da conexão de cliente.
     */
    private String id;
    /**
     * Refere-se a latitude da conexão.
     */
    public final String latitude;
    /**
     * Refere-se a longitude da conexão.
     */
    public final String longitude;

    /**
     * Construtor responsável por instanciar uma conexão de cliente.
     *
     * @param ip Refere-se ao número de IP do servidor.
     * @param port Refere-se a porta do servidor.
     * @param latitude Refere-se a latitude da conexão.
     * @param longitude Refere-se a longitude da conexão.
     */
    public ClientConnection(final String ip, final int port, final String latitude, final String longitude) {
        super(ip, port);
        this.id = null;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Método responsável por alterar o número de identificação da conexão de
     * cliente.
     *
     * @param id Refere-se ao número de identificação da conexão de cliente.
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Método responsável por obiter o número de identificação da conexão de
     * cliente.
     *
     * @return Retorna o número de identificação da conexão de cliente.
     */
    public String getId() {
        return id;
    }

    /**
     * Método responsável por obiter indicativo de que o número de identificação
     * da conexão de cliente existe.
     *
     * @return Retorna indicativo de que o número de identificação da conexão de
     * cliente existe.
     */
    public boolean hasId() {
        return id != null;
    }

    /**
     * Método responsável por retornar a latitude da conexão.
     *
     * @return Retorna a latitude da conexão.
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Método responsável por retornar a longitude da conexão.
     *
     * @return Retorna a longitude da conexão.
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Método responsável por retornar a localização da conexão.
     *
     * @return Retorna a localização da conexão.
     */
    public String getLocation() {
        return getLatitude().concat(";").concat(getLongitude());
    }

}
