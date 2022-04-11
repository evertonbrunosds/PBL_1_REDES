package uefs.ComumBase.interfaces;

/**
 * Interface responsável por possibilitar o acesso a StatusCodeHTTP.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public interface Status {

    /**
     * O servidor pode encontrar o recurso solicitado.
     */
    public static final String FOUND = "302";

    /**
     * O servidor não pode encontrar o recurso solicitado.
     */
    public static final String NOT_FOUND = "404";

    /**
     * O servidor não pode ou não irá processar a requisição devido a alguma
     * coisa que foi entendida como um erro do cliente.
     */
    public static final String BAD_REQUEST = "400";

    /**
     * O servidor encontrou uma situação com a qual não sabe lidar.
     */
    public static final String INTERNAL_SERVER_ERROR = "500";

}
