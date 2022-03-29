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
 * @author Everton Bruno Silva dos Santos - 1911746
 * @version 1.0
 * @param <I> Refere-se ao objeto tipo de entrada.
 * @param <O> Refere-se ao objeto tipo de saída.
 */
public interface Connection<I, O> {

    /**
     * Método responsável por construir fluxos de entrada e saída de dados.
     *
     * @param inputStream Refere-se ao dito fluxo de entrada de dados.
     * @param outputStream Refere-se ao dito fluxo de saída de dados.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public void streamBiulder(
            final Stream<? super I> inputStream,
            final Stream<? super O> outputStream
    ) throws IOException;

    /**
     * Método responsável por construir fluxos de entrada de dados.
     *
     * @param stream Refere-se ao dito fluxo de entrada de dados.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public void inputStreamBiulder(final Stream<? super I> stream) throws IOException;

    /**
     * Método responsável por construir fluxos de saída de dados.
     *
     * @param stream Refere-se ao dito fluxo de saída de dados.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public void outputStreamBiulder(final Stream<? super O> stream) throws IOException;

    /**
     * Método responsável por construir a instância de uma conexão voltada para
     * clientes.
     *
     * @param ip Refere-se ao IP de acesso da conexão.
     * @param port Refere-se a porta de acesso da conexão.
     * @return Retorna um objeto de conexão para clientes.
     */
    public static Connection<DataInputStream, DataOutputStream> biulder(final String ip, final int port) {
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
            public void inputStreamBiulder(Stream<? super DataInputStream> stream) throws IOException {
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
            public void outputStreamBiulder(Stream<? super DataOutputStream> stream) throws IOException {
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
             * @param inputStream Refere-se ao dito fluxo de entrada de dados
             * para clientes.
             * @param outputStream Refere-se ao dito fluxo de saída de dados
             * para clientes.
             * @throws IOException Exceção lançada no caso de haver falha de
             * entrada/saída.
             */
            @Override
            public void streamBiulder(
                    Stream<? super DataInputStream> inputStream,
                    Stream<? super DataOutputStream> outputStream
            ) throws IOException {
                try (final Socket socket = new Socket(ip, port)) {
                    try (final DataInputStream input = new DataInputStream(socket.getInputStream())) {
                        try (final DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
                            inputStream.accept(input);
                            outputStream.accept(output);
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
    public static Connection<DataInputStream, DataOutputStream> biulder(final int port) {
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
            public void inputStreamBiulder(Stream<? super DataInputStream> stream) throws IOException {
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
            public void outputStreamBiulder(Stream<? super DataOutputStream> stream) throws IOException {
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
             * @param inputStream Refere-se ao dito fluxo de entrada de dados
             * para servidores.
             * @param outputStream Refere-se ao dito fluxo de saída de dados
             * para servidores.
             * @throws IOException Exceção lançada no caso de haver falha de
             * entrada/saída.
             */
            @Override
            public void streamBiulder(
                    Stream<? super DataInputStream> inputStream,
                    Stream<? super DataOutputStream> outputStream
            ) throws IOException {
                try (final ServerSocket serverSocket = new ServerSocket(port)) {
                    try (final Socket socket = serverSocket.accept()) {
                        try (final DataInputStream input = new DataInputStream(socket.getInputStream())) {
                            try (final DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
                                inputStream.accept(input);
                                outputStream.accept(output);
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
     * @author Everton Bruno Silva dos Santos - 19111746
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
}
