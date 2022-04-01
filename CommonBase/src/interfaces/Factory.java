package interfaces;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

/**
 * Classe responsável por construir instâncias de objetos.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public interface Factory {

    /**
     * Método responsável por gerar instância de Socket com ServerSocket
     * fornecido.
     *
     * @param serverSocket Refere-se ao ServerSocket fornecido.
     * @param transmissor Refere-se ao transmissor do Socket.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public static void socket(final ServerSocket serverSocket, final SingleTransmissor<Socket> transmissor) throws IOException {
        try (final Socket socket = serverSocket.accept()) {
            transmissor.accept(socket);
        }
    }

    /**
     * Método responsável por gerar instância de Socket com IP e porta
     * fornecida.
     *
     * @param ip Refere-se ao IP fornecido.
     * @param port Refere-se a porta fornecida.
     * @param transmissor Refere-se ao transmissor do Socket.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public static void socket(final String ip, final int port, final SingleTransmissor<Socket> transmissor) throws IOException {
        try (final Socket socket = new Socket(ip, port)) {
            transmissor.accept(socket);
        }
    }

    /**
     * Método responsável por gerar instância de DataInputStream com Socket
     * fornecido.
     *
     * @param socket Refere-se ao Socket fornecido.
     * @param transmissor Refere-se ao transmissor do DataInputStream.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public static void dataInputStream(final Socket socket, final SingleTransmissor<DataInputStream> transmissor) throws IOException {
        try (final DataInputStream input = new DataInputStream(socket.getInputStream())) {
            transmissor.accept(input);
        }
    }

    /**
     * Método responsável por gerar instância de DataOutputStream com Socket
     * fornecido.
     *
     * @param socket Refere-se ao Socket fornecido.
     * @param transmissor Refere-se ao transmissor do DataOutputStream.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public static void dataOutputStream(final Socket socket, final SingleTransmissor<DataOutputStream> transmissor) throws IOException {
        try (final DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            transmissor.accept(output);
        }
    }

    /**
     * Método responsável por gerar instância de DataInputStream e
     * DataOutputStream com Socket fornecido.
     *
     * @param socket Refere-se ao Socket fornecido.
     * @param transmissor Refere-se ao transmissor do DataInputStream e
     * DataOutputStream.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public static void dataDualStream(final Socket socket, final DualTransmissor<DataInputStream, DataOutputStream> transmissor) throws IOException {
        try (
                final DataInputStream input = new DataInputStream(socket.getInputStream());
                final DataOutputStream output = new DataOutputStream(socket.getOutputStream());) {
            transmissor.accept(input, output);
        }
    }

    /**
     * Classe responsável por fornecer métodos de instanciamento de Threads.
     *
     * @author Everton Bruno Silva dos Santos.
     * @version 1.0
     * @since 1.0
     */
    public static final class Thread {

        /**
         * Refere-se a instância singular de semáforo de thread.
         */
        public static final Semaphore SEMAPHORE = new Semaphore(1);

        /**
         * Método responsável por gerar instância de thread sem uso de
         * semáforos.
         *
         * @param worker Refere-se ao trabalhador que desempenhará dado
         * trabalho.
         * @return Retorna instância de thread sem uso de semáforos.
         */
        public static java.lang.Thread makeFree(final Worker worker) {
            return new java.lang.Thread() {
                @Override
                public void run() {
                    worker.work();
                }
            };
        }

        /**
         * Método responsável por gerar instância de thread com uso de semáforo
         * singular compartilhado por todas as demais threads geradas por esse
         * método.
         *
         * @param worker Refere-se ao trabalhador que desempenhará dado
         * trabalho.
         * @return Retorna instância de thread com uso de semáforo singular
         * compartilhado por todas as demais threads geradas por esse método.
         */
        public static java.lang.Thread makeSafe(final Worker worker) {
            return new java.lang.Thread() {
                @Override
                public void run() {
                    try {
                        SEMAPHORE.acquire();
                        worker.work();
                    } catch (final InterruptedException ex) {
                        makeSafe(worker).start();
                    } finally {
                        SEMAPHORE.release();
                    }
                }
            };
        }

        /**
         * Método responsável por gerar instância de thread com uso de múltiplos
         * semáforos.
         *
         * @param worker Refere-se ao trabalhador que desempenhará dado
         * trabalho.
         * @param semaphores Refere-se aos múltiplos semáforos.
         * @return Retorna instância de thread com uso de múltiplos semáforos.
         */
        public static java.lang.Thread makeSafe(final Worker worker, final Iterable<Semaphore> semaphores) {
            return new java.lang.Thread() {
                @Override
                public void run() {
                    try {
                        for (final Semaphore semaphore : semaphores) {
                            semaphore.acquire();
                        }
                        worker.work();
                    } catch (final InterruptedException ex) {
                        makeSafe(worker).start();
                    } finally {
                        for (final Semaphore semaphore : semaphores) {
                            semaphore.release();
                        }
                    }
                }
            };
        }

        /**
         * Método responsável por gerar instância de thread com uso de múltiplos
         * semáforos.
         *
         * @param worker Refere-se ao trabalhador que desempenhará dado
         * trabalho.
         * @param semaphores Refere-se aos múltiplos semáforos.
         * @return Retorna instância de thread com uso de múltiplos semáforos.
         */
        public static java.lang.Thread makeSafeThread(final Worker worker, final Semaphore[] semaphores) {
            return new java.lang.Thread() {
                @Override
                public void run() {
                    try {
                        for (final Semaphore semaphore : semaphores) {
                            semaphore.acquire();
                        }
                        worker.work();
                    } catch (final InterruptedException ex) {
                        makeSafe(worker).start();
                    } finally {
                        for (final Semaphore semaphore : semaphores) {
                            semaphore.release();
                        }
                    }
                }
            };
        }
    }

    /**
     * Interface funcional responsável por possibilitar o acesso a dados
     * transmitidos.
     *
     * @author Everton Bruno Silva dos Santos.
     * @version 1.0
     * @param <T> Refere-se ao tipo de fluxo.
     */
    @FunctionalInterface
    public interface SingleTransmissor<T> {

        /**
         * Método responsável por receber dados transmitidos.
         *
         * @param t Refere-se ao tipo de dado transmitido.
         * @throws IOException Exceção lançada no caso de haver falha de
         * entrada/saída.
         */
        public void accept(final T t) throws IOException;

    }

    /**
     * Interface funcional responsável por possibilitar o acesso a dados
     * transmitidos.
     *
     * @author Everton Bruno Silva dos Santos.
     * @version 1.0
     * @param <T1> Refere-se ao primeiro tipo de transferência.
     * @param <T2> Refere-se ao segundo tipo de transferência.
     */
    @FunctionalInterface
    public interface DualTransmissor<T1, T2> {

        /**
         * Método responsável por receber dados transmitidos.
         *
         * @param t1 Refere-se ao primeiro tipo de dados transmitido.
         * @param t2 Refere-se ao segundo tipo de dados transmitido.
         * @throws IOException Exceção lançada no caso de haver falha de
         * entrada/saída.
         */
        public void accept(final T1 t1, final T2 t2) throws IOException;

    }

}
