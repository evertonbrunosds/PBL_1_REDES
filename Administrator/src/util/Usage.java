package util;

/**
 * Interface responsável por possibilitar o acesso aos modos de uso da lixeira.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public interface Usage {

    /**
     * Refere-se ao nível em que a lixeira está com 0% de sua capacidade total
     * em uso.
     */
    public static String NONE = "0.0";
    /**
     * Refere-se ao nível em que a lixeira está com 25% de sua capacidade total
     * em uso.
     */
    public static String LOW = "0.25";
    /**
     * Refere-se ao nível em que a lixeira está com 50% de sua capacidade total
     * em uso.
     */
    public static String MEDIUM = "0.50";
    /**
     * Refere-se ao nível em que a lixeira está com 75% de sua capacidade total
     * em uso.
     */
    public static String HIGH = "0.75";
    /**
     * Refere-se ao nível em que a lixeira está com 100% de sua capacidade total
     * em uso.
     */
    public static String TOTAL = "0.100";
    /**
     * Refere-se a chave de valor que indica localização.
     */
    public static final String LOCATION = "LOCATION";

}
