package model;

import org.json.JSONObject;

/**
 * Classe responsável por comportar-se como administrador de lixeira.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class RecycleBinAdministrator {

    /**
     * Refere-se ao número de identificação da lixeira.
     */
    private String recycleBinId;
    /**
     * Refere-se aos dados da lixeira.
     */
    private JSONObject recycleBinData;

    /**
     * Construtor responsável por instanciar o administrador de lixeira.
     */
    public RecycleBinAdministrator() {
        recycleBinData = null;
        recycleBinData = null;
    }

    /**
     * Método responsável por obiter indicativo de há um número de identificação
     * disponível.
     *
     * @return Retorna indicativo de há um número de identificação disponível.
     */
    public boolean hasId() {
        return recycleBinId != null;
    }

    /**
     * Método responsável por obiter o número de identificação da lixeira.
     *
     * @return Retorna o número de identificação da lixeira.
     */
    public String getRecycleBinId() {
        return recycleBinId;
    }

    /**
     * Método responsável por alterar o número de identificação da lixeira.
     *
     * @param recycleBinId Refere-se ao novo número de identificação da lixeira.
     */
    public void setRecycleBinId(final String recycleBinId) {
        this.recycleBinId = recycleBinId;
    }

    /**
     * Método responsável por obiter os dados da lixeira.
     *
     * @return Retorna os dados da lixeira.
     */
    public JSONObject getRecycleBinData() {
        return recycleBinData;
    }

    /**
     * Método responsável por alterar os dados da lixeira.
     *
     * @param recycleBinData Refere-se aos novos dados da lixeira.
     */
    public void setRecycleBinData(final JSONObject recycleBinData) {
        this.recycleBinData = recycleBinData;
    }

}
