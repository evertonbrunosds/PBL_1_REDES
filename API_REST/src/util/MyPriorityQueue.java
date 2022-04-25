package util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.function.Consumer;
import uefs.ComumBase.classes.AVLTree;

/**
 * Classe responsável por comportar-se como fila de prioridades.
 *
 * @author Everton Bruno Silva dos Santos.
 * @param <E> Refere-se ao tipo de dado a ser considerado como prioridade.
 */
public class MyPriorityQueue<E> implements Iterable<E> {

    /**
     * Refere-se a estrutura interna de ordenação.
     */
    private final AVLTree<E, String> aVLTree;

    /**
     * Construtor responsável por instanciar a fila de prioridades.
     *
     * @param comparator Refere-se ao comparador de prioridades usado pela
     * estrutura interna de ordenação.
     */
    public MyPriorityQueue(final Comparator<E> comparator) {
        aVLTree = new AVLTree<>(comparator::compare);
        aVLTree.setReverseIterations(true);
    }

    /**
     * Método responsável por indicar se a fila está vazia.
     * @return Retorna indicativo de que a fila está vazia.
     */
    public boolean isEmpty() {
        return aVLTree.isEmpty();
    }

    /**
     * Método responsável por esvaziar a lista de prioridades.
     */
    public void clear() {
        aVLTree.clear();
    }

    /**
     * Método responsável por adicionar elementos a lista de prioridades.
     * @param element Refere-se ao novo elemento.
     */
    public void add(final E element) {
        aVLTree.put(element, null);
    }

    /**
     * Método responsável por fazer com que as iterações sejam revertidas.
     * @param reverse 
     */
    public void setReverseIterations(final boolean reverse) {
        aVLTree.setReverseIterations(reverse);
    }

    /**
     * Método responsável por retornar um iterador de elementos.
     * @return Retorna um iterador de elementos.
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private final Iterator<Entry<E, String>> iterator = aVLTree.iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public E next() {
                return iterator.next().getKey();
            }
        };
    }

    /**
     * Método responsável por percorrer por todos os elementos.
     * @param action Refere-se aos ditos elementos.
     */
    @Override
    public void forEach(final Consumer<? super E> action) {
        aVLTree.forEach(entry -> action.accept(entry.getKey()));
    }

}
