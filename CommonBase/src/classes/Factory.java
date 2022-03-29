package classes;

import interfaces.Worker;
import java.util.concurrent.Semaphore;

/**
 * Classe responsável por construir instâncias de objetos.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 * @since 1.0
 */
public interface Factory {

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
