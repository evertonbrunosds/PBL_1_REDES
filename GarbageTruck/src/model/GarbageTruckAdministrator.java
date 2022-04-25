package model;

import uefs.ComumBase.interfaces.Sender;

/**
 * Classe responsável por comportar-se como administrador de caminhão.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class GarbageTruckAdministrator {

    /**
     * Refere-se ao meio pelo qual se faz possível ter acesso so nível de uso do
     * caminão.
     */
    private Sender<String> sender;

    /**
     * Construtor responsável pelo instanciamento do administrador de caminhão.
     */
    public GarbageTruckAdministrator() {

    }

    /**
     * Método responsável por alterar o meio pelo qual se faz possível ter
     * acesso so nível de uso do caminão.
     *
     * @param sender Refere-se ao meio pelo qual se faz possível ter acesso so
     * nível de uso do caminão.
     */
    public void setSender(final Sender<String> sender) {
        this.sender = sender;
    }

    /**
     * Método responsável por retornar o nível de uso do caminhão.
     *
     * @return Retorna o nível de uso do caminhão.
     */
    public String getUsage() {
        return sender == null ? null : sender.send();
    }

}
