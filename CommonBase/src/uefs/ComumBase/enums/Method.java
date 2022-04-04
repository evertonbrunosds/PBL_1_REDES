package uefs.ComumBase.enums;

import uefs.ComumBase.classes.ConversionException;

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
        if (method == null) {
            return "UNDETERMINED";
        } else {
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
                    return "UNDETERMINED";
            }
        }
    }

    /**
     * Método responsável por converter num Method enumerado uma string que o
     * represente.
     *
     * @param methodString Refere-se eo método transformado em String.
     * @return Retorna em Method o valor em String.
     * @throws ConversionException Exceção lançada caso a conversão não seja
     * possível.
     */
    public static Method toMethod(final String methodString) throws ConversionException {
        if (methodString == null) {
            throw new ConversionException(methodString);
        } else {
            switch (methodString) {
                case "GET":
                    return get;
                case "PUT":
                    return put;
                case "POST":
                    return post;
                case "DELETE":
                    return delete;
                default:
                    throw new ConversionException(methodString);
            }
        }
    }

}
