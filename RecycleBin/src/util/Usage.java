package util;

import static model.Constants.UNDETERMINED;

/**
 * Enumerador responsável por enumerar os níveis de uso de uma lixeira.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public enum Usage {
    /**
     * Refere-se ao nível em que a lixeira está com 0% de sua capacidade total
     * em uso.
     */
    none,
    /**
     * Refere-se ao nível em que a lixeira está com 25% de sua capacidade total
     * em uso.
     */
    low,
    /**
     * Refere-se ao nível em que a lixeira está com 50% de sua capacidade total
     * em uso.
     */
    medium,
    /**
     * Refere-se ao nível em que a lixeira está com 75% de sua capacidade total
     * em uso.
     */
    high,
    /**
     * Refere-se ao nível em que a lixeira está com 100% de sua capacidade total
     * em uso.
     */
    total;

    /**
     * Método responsável por converter para String um uso enumerado.
     *
     * @param usage Refere-se ao uso enumerado.
     * @return Retorna em String o uso enumerado.
     */
    public static String toString(final Usage usage) {
        if (usage == null) {
            return UNDETERMINED;
        } else {
            switch (usage) {
                case none:
                    return "NONE";
                case low:
                    return "LOW";
                case medium:
                    return "MEDIUM";
                case high:
                    return "HIGH";
                case total:
                    return "TOTAL";
                default:
                    return UNDETERMINED;
            }
        }
    }

}
