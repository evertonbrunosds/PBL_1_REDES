package model;

import util.AccessDeniedException;
import static util.Usage.*;

/**
 * Classe responsável por comportar-se como lixeira.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class RecycleBin {

    /**
     * Refere-se ao indicativo de que a lixeira está bloqueada.
     */
    private boolean isBlocked;
    /**
     * Refere-se ao indicativo de uso da lixeira.
     */
    private String usage;

    /**
     * Construtor responsável por instanciar a lixeira.
     */
    public RecycleBin() {
        isBlocked = false;
        usage = NONE;
    }

    /**
     * Método responsável pela obtenção de indicativo de uso da lixeira.
     *
     * @return Retorna indicativo de uso da lixeira.
     */
    public String getUsage() {
        return usage;
    }

    /**
     * Método responsável pela alteração de indicativo de uso da lixeira.
     *
     * @param usage Refere-se ao indicativo de uso da lixeira.
     */
    public void setUsage(final String usage) {
        open();
        this.usage = usage;
    }

    /**
     * Método responsável pela obtenção de indicativo de que a lixeira está
     * bloqueada.
     *
     * @return Retorna indicativo de que a lixeira está bloqueada.
     */
    public boolean isBlocked() {
        return isBlocked;
    }

    /**
     * Método responsável pela alteração de indicativo de que a lixeira está
     * bloqueada.
     *
     * @param isBlocked Refere-se ao indicativo de que a lixeira está bloqueada.
     */
    public void setIsBlocked(final boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    /**
     * Método responsável pela abertura da lixeira.
     *
     * @throws AccessDeniedException Exceção lançada no caso de haver a
     * tentativa de abrir a lixeira bloqueada.
     */
    private void open() throws AccessDeniedException {
        if (isBlocked) {
            throw new AccessDeniedException();
        }
    }

}
