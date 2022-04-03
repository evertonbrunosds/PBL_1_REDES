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
     * @param singleStream Refere-se ao transmissor do Socket.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public static void socket(final ServerSocket serverSocket, final SingleStream<Socket> singleStream) throws IOException {
        try (final Socket socket = serverSocket.accept()) {
            singleStream.accept(socket);
        }
    }

    /**
     * Método responsável por gerar instância de Socket com IP e porta
     * fornecida.
     *
     * @param ip Refere-se ao IP fornecido.
     * @param port Refere-se a porta fornecida.
     * @param singleStream Refere-se ao transmissor do Socket.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public static void socket(final String ip, final int port, final SingleStream<Socket> singleStream) throws IOException {
        try (final Socket socket = new Socket(ip, port)) {
            singleStream.accept(socket);
        }
    }

    /**
     * Método responsável por gerar instância de DataInputStream com Socket
     * fornecido.
     *
     * @param socket Refere-se ao Socket fornecido.
     * @param singleStream Refere-se ao transmissor do DataInputStream.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public static void dataInputStream(final Socket socket, final SingleStream<DataInputStream> singleStream) throws IOException {
        try (final DataInputStream input = new DataInputStream(socket.getInputStream())) {
            singleStream.accept(input);
        }
    }

    /**
     * Método responsável por gerar instância de DataOutputStream com Socket
     * fornecido.
     *
     * @param socket Refere-se ao Socket fornecido.
     * @param singleStream Refere-se ao transmissor do DataOutputStream.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public static void dataOutputStream(final Socket socket, final SingleStream<DataOutputStream> singleStream) throws IOException {
        try (final DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            singleStream.accept(output);
        }
    }

    /**
     * Método responsável por gerar instância de DataInputStream e
     * DataOutputStream com Socket fornecido.
     *
     * @param socket Refere-se ao Socket fornecido.
     * @param singleStream Refere-se ao transmissor do DataInputStream e
     * DataOutputStream.
     * @throws IOException Exceção lançada no caso de haver falha de
     * entrada/saída.
     */
    public static void dataDualStream(final Socket socket, final DualStream<DataInputStream, DataOutputStream> singleStream) throws IOException {
        try (
                final DataInputStream input = new DataInputStream(socket.getInputStream());
                final DataOutputStream output = new DataOutputStream(socket.getOutputStream());) {
            singleStream.accept(input, output);
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

}
