import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node<Item> first;    // beginning of queue
    private Node<Item> last;     // end of queue
    private int n;               // number of elements on queue

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        n = 0;
        first = null;
        last = null;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldlast.next = last;
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int r = StdRandom.uniform(n);
        Node<Item> item = first;
        Node<Item> itemBefore = null;
        for (int i = 1; i < r; i++) {
            itemBefore = item;
            item = item.next;
        }
        if (item != first && item != last) itemBefore.next = itemBefore.next.next;
        else if (item == first) first = item.next;
        else last = itemBefore;
        n--;
        if (n == 0) {
            first = null;
            last = null;
        }
        return item.item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int r = StdRandom.uniform(n);
        Node<Item> item = first;
        for (int i = 1; i <= r; i++) {
            item = item.next;
        }

        return item.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // for testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();
        for (int i = 0; i < 500; i++) {
            randomizedQueue.enqueue(i);
            randomizedQueue.enqueue(i + 100);
            System.out.println(randomizedQueue.dequeue());
        }

    }
}