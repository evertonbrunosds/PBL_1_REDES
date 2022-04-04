package uefs.ComumBase.enums;

/**
 * Enumerador responsável por indicar a que se refere um método REST.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public enum Method {

    /**
     * Responsável por buscar dados.
     */
    get,
    /**
     * Responsável por atualizar dados.
     */
    put,
    /**
     * Responsável por criar dados.
     */
    post,
    /**
     * Responsável por apagar dados.
     */
    delete;

    /**
     * Método responsável por converter em String os valores enumerados.
     *
     * @param method Refere-se ao dito valor enumerado.
     * @return Retorna em String o valor enumerado.
     */
    public static String toString(final Method method) {
        switch (method) {
            case get:
                return "GET";
            case put:
                return "PUT";
            case post:
                return "POST";
            case delete:
                return "DELETE";
            default:
                return "ERROR";
        }
    }

}
