package interfaces;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import static interfaces.Factory.socket;
import static interfaces.Factory.dataInputStream;
import static interfaces.Factory.dataOutputStream;
import static interfaces.Factory.dataDualStream;

/**
 * Interface responsável por fornecer as assinaturas de método para um objeto de
 * conexão para clientes.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public interface ClientConnection extends Connection<DataInputStream, DataOutputStream> {

    /**
     * Método responsável por construir a instância de uma conexão voltada para
     * clientes.
     *
     * @param ip Refere-se ao IP de acesso da conexão.
     * @param port Refere-se a porta de acesso da conexão.
     * @return Retorna um objeto de conexão para clientes.
     */
    public static ClientConnection builder(final String ip, final int port) {
        return new ClientConnection() {

            /**
             * Implementação de método responsável por construir fluxos de
             * entrada de dados para clientes.
             *
             * @param singleStream Refere-se ao dito fluxo de entrada de dados para
             * clientes.
             * @throws IOException Exceção lançada no caso de haver falha de
             * entrada/saída.
             */
            @Override
            public void inputStreamBuilder(final SingleStream<? super DataInputStream> singleStream) throws IOException {
                socket(ip, port, socketInstance -> dataInputStream(socketInstance, singleStream::accept));
            }

            /**
             * Implementação de método responsável por construir fluxos de saída
             * de dados para clientes.
             *
             * @param singleStream Refere-se ao dito fluxo de saída de dados para
             * clientes.
             * @throws IOException Exceção lançada no caso de haver falha de
             * entrada/saída.
             */
            @Override
            public void outputStreamBuilder(final SingleStream<? super DataOutputStream> singleStream) throws IOException {
                socket(ip, port, socketInstance -> dataOutputStream(socketInstance, singleStream::accept));
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
            public void streamBuilder(final DualStream<? super DataInputStream, ? super DataOutputStream> dualStream) throws IOException {
                socket(ip, port, socketInstance -> dataDualStream(socketInstance, dualStream::accept));
            }

            /**
             * Implementação de Método responsável por testar a conexão.
             *
             * @throws IOException Exceção lançada no caso de haver falha de
             * entrada/saída.
             */
            @Override
            public void test() throws IOException {
                final Socket socket = new Socket(ip, port);
                socket.close();
            }
        };
    }

}
