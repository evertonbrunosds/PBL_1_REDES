package interfaces;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
     * Método responsável por construir fluxos de entrada e saída de dados.
     *
     * @param multiStream Refere-se ao dito fluxo de entrada e saída de dados.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public void streamBuilder(
            final MultiStream<? super I, ? super O> multiStream) throws IOException;

    /**
     * Método responsável por construir fluxos de entrada de dados.
     *
     * @param stream Refere-se ao dito fluxo de entrada de dados.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public void inputStreamBuilder(final Stream<? super I> stream) throws IOException;

    /**
     * Método responsável por construir fluxos de saída de dados.
     *
     * @param stream Refere-se ao dito fluxo de saída de dados.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public void outputStreamBuilder(final Stream<? super O> stream) throws IOException;

    /**
     * Método responsável por construir a instância de uma conexão voltada para
     * clientes.
     *
     * @param ip Refere-se ao IP de acesso da conexão.
     * @param port Refere-se a porta de acesso da conexão.
     * @return Retorna um objeto de conexão para clientes.
     */
    public static Connection<DataInputStream, DataOutputStream> builder(final String ip, final int port) {
        return new Connection<DataInputStream, DataOutputStream>() {

            /**
             * Implementação de método responsável por construir fluxos de
             * entrada de dados para clientes.
             *
             * @param stream Refere-se ao dito fluxo de entrada de dados para
             * clientes.
             * @throws IOException Exceção lançada no caso de haver falha de
             * entrada/saída.
             */
            @Override
            public void inputStreamBuilder(final Stream<? super DataInputStream> stream) throws IOException {
                try (final Socket socket = new Socket(ip, port)) {
                    try (final DataInputStream input = new DataInputStream(socket.getInputStream())) {
                        stream.accept(input);
                    }
                }
            }

            /**
             * Implementação de método responsável por construir fluxos de saída
             * de dados para clientes.
             *
             * @param stream Refere-se ao dito fluxo de saída de dados para
             * clientes.
             * @throws IOException Exceção lançada no caso de haver falha de
             * entrada/saída.
             */
            @Override
            public void outputStreamBuilder(final Stream<? super DataOutputStream> stream) throws IOException {
                try (final Socket socket = new Socket(ip, port)) {
                    try (final DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
                        stream.accept(output);
                    }
                }
            }

            /**
             * Implementação de método responsável por construir fluxos de
             * entrada e saída de dados para clientes.
             *
             * @param multiStream Refere-se ao dito fluxo de entrada e saída de
             * dados.
             * @throws IOException Exceção lançada no caso de haver falha de
             * entrada/saída.
             */
            @Override
            public void streamBuilder(
                    final MultiStream<? super DataInputStream, ? super DataOutputStream> multiStream
            ) throws IOException {
                try (final Socket socket = new Socket(ip, port)) {
                    try (final DataInputStream input = new DataInputStream(socket.getInputStream())) {
                        try (final DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
                            multiStream.accept(input, output);
                        }
                    }
                }
            }
        };
    }

    /**
     * Método responsável por construir a instância de uma conexão voltada para
     * servidores.
     *
     * @param port Refere-se a porta de acesso da conexão.
     * @return Retorna um objeto de conexão para servidores.
     */
    public static Connection<DataInputStream, DataOutputStream> builder(final int port) {
        return new Connection<DataInputStream, DataOutputStream>() {

            /**
             * Implementação de método responsável por construir fluxos de
             * entrada de dados para servidores.
             *
             * @param stream Refere-se ao dito fluxo de entrada de dados para
             * servidores.
             * @throws IOException Exceção lançada no caso de haver falha de
             * entrada/saída.
             */
            @Override
            public void inputStreamBuilder(final Stream<? super DataInputStream> stream) throws IOException {
                try (final ServerSocket serverSocket = new ServerSocket(port)) {
                    try (final Socket socket = serverSocket.accept()) {
                        try (final DataInputStream input = new DataInputStream(socket.getInputStream())) {
                            stream.accept(input);
                        }
                    }
                }
            }

            /**
             * Implementação de método responsável por construir fluxos de saída
             * de dados para servidores.
             *
             * @param stream Refere-se ao dito fluxo de saída de dados para
             * servidores.
             * @throws IOException Exceção lançada no caso de haver falha de
             * entrada/saída.
             */
            @Override
            public void outputStreamBuilder(final Stream<? super DataOutputStream> stream) throws IOException {
                try (final ServerSocket serverSocket = new ServerSocket(port)) {
                    try (final Socket socket = serverSocket.accept()) {
                        try (final DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
                            stream.accept(output);
                        }
                    }
                }
            }

            /**
             * Implementação de método responsável por construir fluxos de
             * entrada e saída de dados para servidores.
             *
             * @param multiStream Refere-se ao dito fluxo de entrada e saída de
             * dados.
             * @throws IOException Exceção lançada no caso de haver falha de
             * entrada/saída.
             */
            @Override
            public void streamBuilder(
                    final MultiStream<? super DataInputStream, ? super DataOutputStream> multiStream
            ) throws IOException {
                try (final ServerSocket serverSocket = new ServerSocket(port)) {
                    try (final Socket socket = serverSocket.accept()) {
                        try (final DataInputStream input = new DataInputStream(socket.getInputStream())) {
                            try (final DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
                                multiStream.accept(input, output);
                            }
                        }
                    }
                }
            }
        };
    }

    /**
     * Interface funcional responsável por possibilitar o acesso aos fluxos de
     * entrada e saída de forma dinâmica.
     *
     * @author Everton Bruno Silva dos Santos.
     * @version 1.0
     * @param <T> Refere-se ao tipo de fluxo.
     */
    @FunctionalInterface
    public interface Stream<T> {

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
    public interface MultiStream<I, O> {

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
