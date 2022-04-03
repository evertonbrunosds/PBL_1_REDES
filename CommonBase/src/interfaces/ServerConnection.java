package interfaces;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import static interfaces.Factory.dataDualStreamBuilder;
import static interfaces.Factory.dataInputStreamBuilder;
import static interfaces.Factory.dataOutputStreamBuilder;
import static interfaces.Factory.socketBuilder;

/**
 * Interface responsável por fornecer as assinaturas de método para um objeto de
 * conexão para servidor.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public interface ServerConnection extends Connection<DataInputStream, DataOutputStream> {

    /**
     * Método responsável por possibilitar a excussão assíncrona de instruções
     * que envolvem um fluxo de entrada.
     *
     * @param iOException Refere-se as instruções de tratamento caso ocorram
     * erros na execução assíncrona.
     * @return Retorna um objeto de execução assíncrona.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public Future<SingleStream<? super DataInputStream>> inputStreamFuture(final Treatable<? super IOException> iOException) throws IOException;

    /**
     * Método responsável por possibilitar a excussão assíncrona de instruções
     * que envolvem um fluxo de saída.
     *
     * @param iOException Refere-se as instruções de tratamento caso ocorram
     * erros na execução assíncrona.
     * @return Retorna um objeto de execução assíncrona.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public Future<SingleStream<? super DataOutputStream>> outputStreamFuture(final Treatable<? super IOException> iOException) throws IOException;

    /**
     * Método responsável por possibilitar a excussão assíncrona de instruções
     * que envolvem um fluxo de entrada/saída.
     *
     * @param iOException Refere-se as instruções de tratamento caso ocorram
     * erros na execução assíncrona.
     * @return Retorna um objeto de execução assíncrona.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public Future<DualStream<? super DataInputStream, ? super DataOutputStream>> streamFuture(final Treatable<? super IOException> iOException) throws IOException;

    /**
     * Método responsável por possibilitar o fechamento do servidor.
     *
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public void close() throws IOException;

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
                socketBuilder(getServerSocketInstance(), socketInstance -> {
                    dataInputStreamBuilder(socketInstance, singleStream::accept);
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
                socketBuilder(getServerSocketInstance(), socketInstance -> {
                    dataOutputStreamBuilder(socketInstance, singleStream::accept);
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
                socketBuilder(getServerSocketInstance(), socketInstance -> {
                    dataDualStreamBuilder(socketInstance, dualStream::accept);
                });
            }

            /**
             * Implementação de método responsável por possibilitar a excussão
             * assíncrona de instruções que envolvem um fluxo de entrada.
             *
             * @param iOException Refere-se as instruções de tratamento caso
             * ocorram erros na execução assíncrona.
             * @return Retorna um objeto de execução assíncrona.
             * @throws IOException Exceção lançada no caso de haver falha de
             * entrada/saída.
             */
            @Override
            public Future<SingleStream<? super DataInputStream>> inputStreamFuture(final Treatable<? super IOException> iOException) throws IOException {
                final Socket socket = getServerSocketInstance().accept();
                return (final SingleStream<? super DataInputStream> singleStream) -> {
                    Factory.thread(() -> {
                        try {
                            try {
                                dataInputStreamBuilder(socket, singleStream::accept);
                            } finally {
                                socket.close();
                            }
                        } catch (final IOException ex) {
                            iOException.toTreate(ex);
                        }
                    }).start();
                };
            }

            /**
             * Implementação de método responsável por possibilitar a excussão
             * assíncrona de instruções que envolvem um fluxo de saída.
             *
             * @param iOException Refere-se as instruções de tratamento caso
             * ocorram erros na execução assíncrona.
             * @return Retorna um objeto de execução assíncrona.
             * @throws IOException Exceção lançada no caso de haver falha de
             * entrada/saída.
             */
            @Override
            public Future<SingleStream<? super DataOutputStream>> outputStreamFuture(Treatable<? super IOException> iOException) throws IOException {
                final Socket socket = getServerSocketInstance().accept();
                return (final SingleStream<? super DataOutputStream> singleStream) -> {
                    Factory.thread(() -> {
                        try {
                            try {
                                dataOutputStreamBuilder(socket, singleStream::accept);
                            } finally {
                                socket.close();
                            }
                        } catch (final IOException ex) {
                            iOException.toTreate(ex);
                        }
                    }).start();
                };
            }

            /**
             * Implementação de método responsável por possibilitar a excussão
             * assíncrona de instruções que envolvem um fluxo de entrada/saída.
             *
             * @param iOException Refere-se as instruções de tratamento caso
             * ocorram erros na execução assíncrona.
             * @return Retorna um objeto de execução assíncrona.
             * @throws IOException Exceção lançada no caso de haver falha de
             * entrada/saída.
             */
            @Override
            public Future<DualStream<? super DataInputStream, ? super DataOutputStream>> streamFuture(Treatable<? super IOException> iOException) throws IOException {
                final Socket socket = getServerSocketInstance().accept();
                return (final DualStream<? super DataInputStream, ? super DataOutputStream> dualStream) -> {
                    Factory.thread(() -> {
                        try {
                            try {
                                dataDualStreamBuilder(socket, dualStream::accept);
                            } finally {
                                socket.close();
                            }
                        } catch (final IOException ex) {
                            iOException.toTreate(ex);
                        }
                    }).start();
                };
            }

            /**
             * Implementação de método responsável por possibilitar o fechamento
             * do servidor.
             *
             * @throws IOException Exceção lançada no caso de haver falha de
             * entrada/saída.
             */
            @Override
            public void close() throws IOException {
                if (serverSocket != null) {
                    if (!serverSocket.isClosed()) {
                        serverSocket.close();
                    }
                    serverSocket = null;
                }
            }

        };

    }

}
