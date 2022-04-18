package util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Consumer;

public class MyPriorityQueue<E> implements Iterable<E> {

    private final Comparator<E> comparator;
    private Node<E> root;

    public MyPriorityQueue(final Comparator<E> comparator) {
        this.comparator = comparator;
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void add(final E element) {
        final Node<E> newNode = new Node<>(element);
        if (isEmpty()) {
            root = newNode;
        } else {
            add(newNode);
        }
    }

    private void add(final Node<E> newNode) {
        for (Node<E> tmpNode = root; tmpNode != null; tmpNode = tmpNode.getAfter()) {
            final int result = comparator.compare(newNode.getElement(), tmpNode.getElement());
            if (result > 0) { //O NÓ NOVO TEM O ELEMENTO MAIOR QUE O DO NÓ ATUAL
                if (tmpNode.equals(root)) {
                    root = newNode;
                } else {
                    newNode.setBefore(tmpNode.getBefore());
                }
                newNode.setAfter(tmpNode);
                break;
            } else if (result < 0) {//O NÓ ATUAL TEM O ELEMENTO MAIOR QUE O DO NÓ NOVO
                newNode.setAfter(tmpNode.getAfter());
                newNode.setBefore(tmpNode);
                break;
            } else {//O NÓ NOVO E ATUAL TÊM ELEMENTOS DE TAMANHOS IGUAIS
                newNode.setAfter(tmpNode.getAfter());
                newNode.setBefore(tmpNode);
                break;
            }
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> tmpNode = root;

            @Override
            public boolean hasNext() {
                return tmpNode != null;
            }

            @Override
            public E next() {
                final E element = tmpNode.getElement();
                tmpNode = tmpNode.getAfter();
                return element;
            }
        };
    }

    @Override
    public void forEach(final Consumer<? super E> action) {
        for (Node<E> tmpNode = root; tmpNode != null; tmpNode = tmpNode.getAfter()) {
            action.accept(tmpNode.getElement());
        }
    }

}
