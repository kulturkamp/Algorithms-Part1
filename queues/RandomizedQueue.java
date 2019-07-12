


import java.util.Iterator;
import java.util.Random;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] rq;



    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        rq = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }
    private void resize(int cap) {
        Item[] copy = (Item[]) new Object[cap];
        for (int i = 0; i < size; i++)
            copy[i] = rq[i];
        rq = copy;

    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size == rq.length) resize(2*rq.length);
        rq[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) throw new java.util.NoSuchElementException();
        int r = (int)Math.random()*(size + 1);
        Item item = rq[r];
        rq[r] = rq[size - 1];
        rq[--size] = null;
        if (size > 0 && size == rq.length/4) resize(rq.length/2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) throw new java.util.NoSuchElementException();
        int r = (int)(Math.random() *size);
        return rq[r];

    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i = size;
        private final int[] order;

        public RandomizedQueueIterator()
        {
            order = new int[i];
            for (int j = 0; j < i; ++j) {
                order[j] = j;
            }
            shuffleArray(order);
        }

        public boolean hasNext() {
            return i > 0;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return rq[order[--i]];
        }
    }
    private static void shuffleArray(int[] array)
    {
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            if (index != i)
            {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        rq.enqueue("a");
        rq.enqueue("b");
        rq.enqueue("c");
        rq.enqueue("d");
        rq.enqueue("e");
        System.out.println(rq.size());
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println("-----");
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.size());
        System.out.println(rq.sample());
        System.out.println("-----");
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.size());
        System.out.println(rq.isEmpty());

    }

}
