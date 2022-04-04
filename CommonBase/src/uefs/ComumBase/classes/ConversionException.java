package uefs.ComumBase.classes;

/**
 * Classe responsável por comportar-se como exceção de conversão.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class ConversionException extends RuntimeException {

    /**
     * Construtor responsável por instanciar uma exceção de conversão.
     *
     * @param cause Refere-se a causa de lançamento de exceção.
     */
    public ConversionException(final String cause) {
        super("ConversionException: ".concat(cause).concat(" is not compatible!"));
    }

}
