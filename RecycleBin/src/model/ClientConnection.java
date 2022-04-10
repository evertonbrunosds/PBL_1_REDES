package model;

import static model.Constants.*;

/**
 * Classe responsável por comportar-se como conexão de cliente detentora de
 * número de identificação.
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
     * Construtor responsável por instanciar uma conexão de cliente.
     *
     * @param ip Refere-se ao número de IP do servidor.
     * @param port Refere-se a porta do servidor.
     */
    public ClientConnection(final String ip, final int port) {
        super(ip, port);
        this.id = UNDETERMINED;
    }

    /**
     * Construtor responsável por instanciar uma conexão de cliente.
     *
     * @param id Refere-se ao número de identificação da conexão de cliente.
     * @param ip Refere-se ao número de IP do servidor.
     * @param port Refere-se a porta do servidor.
     */
    public ClientConnection(final String id, final String ip, final int port) {
        super(ip, port);
        this.id = (id == null) ? UNDETERMINED : id;
    }

    /**
     * Método responsável por alterar o número de identificação da conexão de
     * cliente.
     *
     * @param id Refere-se ao número de identificação da conexão de cliente.
     */
    public void setId(final String id) {
        this.id = (id == null) ? UNDETERMINED : id;
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

}
