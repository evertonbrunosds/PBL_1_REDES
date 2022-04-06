package uefs.ComumBase.interfaces;

public interface Status {
    
    /**
     * O servidor não pode encontrar o recurso solicitado. Este código de
     * resposta talvez seja o mais famoso devido à frequência com que acontece
     * na web.
     */
    public static final String NOT_FOUND = "404";

    /**
     * O servidor encontrou uma situação com a qual não sabe lidar.
     */
    public static final String INTERNAL_SERVER_ERROR = "500";

    /**
     * Estas requisição foi bem sucedida.
     */
    public static final String OK = "200";
    
}
