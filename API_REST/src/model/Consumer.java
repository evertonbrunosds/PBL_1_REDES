package model;

import java.io.IOException;

/**
 * Interface responsável por fornecer os métodos de um consumidor.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public interface Consumer {

    /**
     * Método responsável por buscar dados do consumidor.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    void get() throws IOException;

    /**
     * Método responsável por criar dados do consumidor.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    void post() throws IOException;

    /**
     * Método responsável por atualizar dados do consumidor.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    void put() throws IOException;

    /**
     * Método responsável por apagar dados do consumidor.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    void delete() throws IOException;

}
