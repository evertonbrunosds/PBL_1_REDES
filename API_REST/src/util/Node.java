package util;

public class Node<E> {

    private final E element;
    private Node<E> before;
    private Node<E> after;

    public Node(final E element) {
        this.element = element;
        this.before = null;
        this.after = null;
    }

    public E getElement() {
        return element;
    }

    public Node<E> getBefore() {
        return before;
    }

    public Node<E> getAfter() {
        return after;
    }

    public void setBefore(final Node<E> before) {
        this.before = before;
        if (before != null) {
            before.after = this;
        }
    }

    public void setAfter(final Node<E> after) {
        this.after = after;
        if (after != null) {
            after.before = this;
        }
    }

}
