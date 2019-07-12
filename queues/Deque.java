

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private node first, last;

    private class node {
        Item val;
        node next;
        node prev;
    }

    // construct an empty deque
    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;

    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        node oldfirst = first;
        first = new node();
        first.val = item;
        first.next = oldfirst;
        first.prev = null;
        if (isEmpty()) last = first;
        else oldfirst.prev = first;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        node oldlast = last;
        last = new node();
        last.val = item;
        last.next = null;
        last.prev = oldlast;
        if (isEmpty()) first = last;
        else oldlast.next = last;
        size++;

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = first.val;
        first = first.next;
        size--;
        if (isEmpty()) last = first;
        else first.prev = null;

        return item;

    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = last.val;
        last = last.prev;
        size--;
        if (isEmpty()) first = last;
        else last.next = null;

        return item;

    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private node curr = first;

        public boolean hasNext() {
            return curr != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = curr.val;
            curr = curr.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        Deque<String> dq = new Deque<>();
        dq.addFirst("a");
        dq.addLast("b");
        System.out.println(dq.size());
        System.out.println(dq.first.val);
        System.out.println(dq.last.val);
        System.out.println(dq.removeLast());
        System.out.println(dq.last.val);
        System.out.println(dq.first.val);
        System.out.println(dq.removeFirst());
        System.out.println(dq.size());
        System.out.println(dq.isEmpty());

    }
}
