package interfaces;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import static interfaces.Factory.socket;
import static interfaces.Factory.dataInputStream;
import static interfaces.Factory.dataOutputStream;
import java.net.Socket;
import static interfaces.Factory.dataDualStream;

/**
 * Interface responsável por fornecer as assinaturas de método para um objeto de
 * conexão para servidor.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public interface ServerConnection extends Connection<DataInputStream, DataOutputStream> {

    public FutureStream<SingleStream<? super DataInputStream>> inputStreamFuture() throws IOException;

    /**
     * Método responsável por construir a instância de uma conexão voltada para
     * servidores.
     *
     * @param port Refere-se a porta de acesso da conexão.
     * @return Retorna um objeto de conexão para servidores.
     */
    public static ServerConnection builder(final int port) {
        return new ServerConnection() {
            private ServerSocket serverSocket = null;

            /**
             * Método responsável por gerar instância de ServerSocket com porta
             * fornecida.
             *
             * @throws IOException Exceção lançada no caso de haver falha de
             * entrada/saída.
             */
            private ServerSocket getServerSocketInstance() throws IOException {
                if (serverSocket == null) {
                    serverSocket = new ServerSocket(port);
                }
                return serverSocket;
            }

            /**
             * Implementação de método responsável por construir fluxos de
             * entrada de dados para servidores.
             *
             * @param singleStream Refere-se ao dito fluxo de entrada de dados
             * para servidores.
             * @throws IOException Exceção lançada no caso de haver falha de
             * entrada/saída.
             */
            @Override
            public void inputStreamBuilder(final SingleStream<? super DataInputStream> singleStream) throws IOException {
                socket(getServerSocketInstance(), socketInstance -> {
                    dataInputStream(socketInstance, singleStream::accept);
                });
            }

            /**
             * Implementação de método responsável por construir fluxos de saída
             * de dados para servidores.
             *
             * @param singleStream Refere-se ao dito fluxo de saída de dados
             * para servidores.
             * @throws IOException Exceção lançada no caso de haver falha de
             * entrada/saída.
             */
            @Override
            public void outputStreamBuilder(final SingleStream<? super DataOutputStream> singleStream) throws IOException {
                socket(getServerSocketInstance(), socketInstance -> {
                    dataOutputStream(socketInstance, singleStream::accept);
                });
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
                socket(getServerSocketInstance(), socketInstance -> {
                    dataDualStream(socketInstance, dualStream::accept);
                });
            }

            @Override
            public FutureStream<SingleStream<? super DataInputStream>> inputStreamFuture() throws IOException {
                final Socket socket = getServerSocketInstance().accept();
                return (final SingleStream<? super DataInputStream> singleStream) -> {
                    Factory.Thread.makeFree(() -> {
                        try {
                            try {
                                dataInputStream(socket, singleStream::accept);
                            } finally {
                                socket.close();
                            }
                        } catch (final IOException ex) {
                            System.out.println("Erro");
                        }
                    }).start();
                };
            }

        };

    }

}
