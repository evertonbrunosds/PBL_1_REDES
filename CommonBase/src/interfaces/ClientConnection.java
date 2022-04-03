package interfaces;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import static interfaces.Factory.dataDualStreamBuilder;
import static interfaces.Factory.dataInputStreamBuilder;
import static interfaces.Factory.dataOutputStreamBuilder;
import static interfaces.Factory.socketBuilder;

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
             * @param singleStream Refere-se ao dito fluxo de entrada de dados
             * para clientes.
             * @throws IOException Exceção lançada no caso de haver falha de
             * entrada/saída.
             */
            @Override
            public void inputStreamBuilder(final SingleStream<? super DataInputStream> singleStream) throws IOException {
                socketBuilder(ip, port, socketInstance -> dataInputStreamBuilder(socketInstance, singleStream::accept));
            }

            /**
             * Implementação de método responsável por construir fluxos de saída
             * de dados para clientes.
             *
             * @param singleStream Refere-se ao dito fluxo de saída de dados
             * para clientes.
             * @throws IOException Exceção lançada no caso de haver falha de
             * entrada/saída.
             */
            @Override
            public void outputStreamBuilder(final SingleStream<? super DataOutputStream> singleStream) throws IOException {
                socketBuilder(ip, port, socketInstance -> dataOutputStreamBuilder(socketInstance, singleStream::accept));
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
                socketBuilder(ip, port, socketInstance -> dataDualStreamBuilder(socketInstance, dualStream::accept));
            }

        };
    }

}
