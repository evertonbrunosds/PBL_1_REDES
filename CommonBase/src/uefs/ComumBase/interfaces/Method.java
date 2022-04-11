package uefs.ComumBase.interfaces;

/**
 * Interface responsável por possibilitar o acesso aos métodos REST.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public interface Method {

    /**
     * Responsável por buscar dados.
     */
    public static String GET = "GET";
    /**
     * Responsável por atualizar dados.
     */
    public static String PUT = "PUT";
    /**
     * Responsável por criar dados.
     */
    public static String POST = "POST";
    /**
     * Responsável por apagar dados.
     */
    public static String DELETE = "DELETE";

}
