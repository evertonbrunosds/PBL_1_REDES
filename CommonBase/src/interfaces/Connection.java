package interfaces;

import java.io.IOException;

/**
 * Interface responsável por fornecer as assinaturas de método para um objeto de
 * conexão.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 * @param <I> Refere-se ao objeto tipo de entrada.
 * @param <O> Refere-se ao objeto tipo de saída.
 */
public interface Connection<I, O> {

    /**
     * Método responsável por testar a conexão.
     *
     * @throws IOException Exceção lançada no caso de haver falha de
     */
    public void test() throws IOException;

    /**
     * Método responsável por construir fluxos de entrada e saída de dados.
     *
     * @param dualStream Refere-se ao dito fluxo de entrada e saída de dados.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public void streamBuilder(final DualStream<? super I, ? super O> dualStream) throws IOException;

    /**
     * Método responsável por construir fluxos de entrada de dados.
     *
     * @param singleStream Refere-se ao dito fluxo de entrada de dados.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public void inputStreamBuilder(final SingleStream<? super I> singleStream) throws IOException;

    /**
     * Método responsável por construir fluxos de saída de dados.
     *
     * @param singleStream Refere-se ao dito fluxo de saída de dados.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public void outputStreamBuilder(final SingleStream<? super O> singleStream) throws IOException;

    /**
     * Interface funcional responsável por possibilitar o acesso aos fluxos de
     * entrada e saída de forma dinâmica.
     *
     * @author Everton Bruno Silva dos Santos.
     * @version 1.0
     * @param <T> Refere-se ao tipo de fluxo.
     */
    @FunctionalInterface
    public interface SingleStream<T> {

        /**
         * Método responsável por receber fluxos de entrada e saída.
         *
         * @param t Refere-se ai tipo de fluxo de entrada e saída.
         * @throws IOException Exceção lançada no caso de haver falha de
         * entrada/saída.
         */
        public void accept(final T t) throws IOException;

    }

    /**
     * Interface funcional responsável por possibilitar o acesso aos fluxos de
     * entrada e saída de forma simultânea.
     *
     * @param <I> Refere-se ao tipo de fluxo de entrada.
     * @param <O> Refere-se ao tipo de fluxo de saída.
     */
    @FunctionalInterface
    public interface DualStream<I, O> {

        /**
         * Método responsável por receber fluxos de entrada e saída de forma
         * simultânea.
         *
         * @param i Refere-se ai tipo de fluxo de entrada.
         * @param o Refere-se ai tipo de fluxo de saída.
         * @throws IOException Exceção lançada no caso de haver falha de
         * entrada/saída.
         */
        public void accept(final I i, final O o) throws IOException;
    }
}
