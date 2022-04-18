package util;

import java.util.concurrent.Semaphore;

/**
 * Classe responsável por comportar-se como um gerador de ID.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class IdGenerator {

    /**
     * Refere-se ao semárforo utilizado na geração de números de identificação
     * únicos.
     */
    private final Semaphore semaphore;
    /**
     * Refere-se ao número de identificação atual.
     */
    private int currentId;

    /**
     * Construtor responsável por instanciar um gerador de ID.
     */
    public IdGenerator() {
        semaphore = new Semaphore(1);
        currentId = 0;
    }

    /**
     * Método responsável por obiter um número de identificação em inteiro.
     *
     * @return Retorna um número de identificação em inteiro.
     * @throws InterruptedException Exceção lançada para o caso da geração de ID
     * não ter sido exclusiva.
     */
    public int getIntegerId() throws InterruptedException {
        semaphore.acquire();
        final int currentValue = currentId;
        currentId++;
        semaphore.release();
        return currentValue;
    }

    /**
     * Método responsável por obiter um número de identificação em string.
     *
     * @return Retorna um número de identificação em inteiro.
     * @throws InterruptedException Exceção lançada para o caso da geração de ID
     * não ter sido exclusiva.
     */
    public String getStringId() throws InterruptedException {
        return Integer.toString(getIntegerId());
    }

}
