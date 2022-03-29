package interfaces;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Interface responsável por fornecer as assinaturas de método para um objeto de
 * conexão para servidor.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public interface ServerConnection extends Connection<DataInputStream, DataOutputStream> {

    /**
     * Método responsável por construir a instância de uma conexão voltada para
     * servidores.
     *
     * @param port Refere-se a porta de acesso da conexão.
     * @return Retorna um objeto de conexão para servidores.
     */
    public static ServerConnection builder(final int port) {
        return new ServerConnection() {

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
            public void inputStreamBuilder(final SingleStream<? super DataInputStream> singleStream) throws IOException {
                try (final ServerSocket serverSocket = new ServerSocket(port)) {
                    try (final Socket socket = serverSocket.accept()) {
                        try (final DataInputStream input = new DataInputStream(socket.getInputStream())) {
                            singleStream.accept(input);
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
            public void outputStreamBuilder(final SingleStream<? super DataOutputStream> singleStream) throws IOException {
                try (final ServerSocket serverSocket = new ServerSocket(port)) {
                    try (final Socket socket = serverSocket.accept()) {
                        try (final DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
                            singleStream.accept(output);
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
                    final DualStream<? super DataInputStream, ? super DataOutputStream> dualStream
            ) throws IOException {
                try (final ServerSocket serverSocket = new ServerSocket(port)) {
                    try (final Socket socket = serverSocket.accept()) {
                        try (final DataInputStream input = new DataInputStream(socket.getInputStream())) {
                            try (final DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
                                dualStream.accept(input, output);
                            }
                        }
                    }
                }
            }

            /**
             * Implementação de Método responsável por testar a conexão.
             *
             * @throws IOException Exceção lançada no caso de haver falha de
             * entrada/saída.
             */
            @Override
            public void test() throws IOException {
                final ServerSocket serverSocket = new ServerSocket(port);
                serverSocket.close();
            }
        };
    }

}
