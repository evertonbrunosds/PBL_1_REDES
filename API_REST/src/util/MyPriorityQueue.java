package util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.function.Consumer;
import uefs.ComumBase.classes.AVLTree;

public class MyPriorityQueue<E> implements Iterable<E> {

    private final AVLTree<E,String> aVLTree;

    public MyPriorityQueue(final Comparator<E> comparator) {
        aVLTree = new AVLTree<>(comparator::compare);
        aVLTree.setReverseIterations(true);
    }

    public boolean isEmpty() {
        return aVLTree.isEmpty();
    }
    
    public void clear() {
        aVLTree.clear();
    }

    public void add(final E element) {
        aVLTree.put(element, null);
    }
    
    public void setReverseIterations(final boolean reverse) {
        aVLTree.setReverseIterations(reverse);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private final Iterator<Entry<E,String>> iterator = aVLTree.iterator();

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

    @Override
    public void forEach(final Consumer<? super E> action) {
        aVLTree.forEach(entry -> action.accept(entry.getKey()));
    }

}
