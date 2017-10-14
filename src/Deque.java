import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Item[] d;       // deque elements
    private int n;          // number of elements on queue
    private int head;      // index of first available element of deque
    private int tail;       // index of last available element of deque

    /**
     * Initializes an empty queue.
     */
    public Deque() {
        d = (Item[]) new Object[2];
        n = 0;
        head = 0;
        tail = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        // double size of array if necessary and recopy to front of array
        if (n == d.length) resize(2 * d.length);   // double size of array if necessary
        d[head--] = item;                        // add item
        if (head == -1) head = d.length - 1;          // wrap-around
        n++;
        if (n == 1) tail = head;
    }

    /**
     * Adds the item to end this queue.
     *
     * @param item the item to add
     */
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        // double size of array if necessary and recopy to front of array
        if (n == d.length) resize(2 * d.length);   // double size of array if necessary
        d[tail++] = item;                        // add item
        if (tail == d.length) tail = 0;          // wrap-around
        n++;
        if (n == 1) head = tail;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        head++;
        if (head == d.length) head = 0;          // wrap-around
        Item item = d[head];
        d[head] = null;                            // to avoid loitering
        n--;
        // shrink size of array if necessary
        if (n > 0 && n == d.length / 4) resize(d.length / 2);
        if (n == 1) tail = head;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        tail--;
        if (tail == -1) tail = d.length - 1;           // wrap-around
        Item item = d[tail];
        d[tail] = null;                            // to avoid loitering
        n--;
        // shrink size of array if necessary
        if (n > 0 && n == d.length / 4) resize(d.length / 2);
        if (n == 1) head = tail;
        return item;
    }

    /**
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     *
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = d[(i + head + 1) % d.length];
            i++;
            return item;
        }
    }

    // for testing
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();

        deque.addFirst("ZJVNWPBVLP");
        deque.addLast("ZJATEYRDDL");
        deque.removeLast(); //      ==> ZJATEYRDDL
        deque.addFirst("DNNWTJNHSK");
        deque.removeLast(); //     ==> ZJVNWPBVLP
        deque.addFirst("XGVKLMHLKO");
        deque.addFirst("GPRGSBTZCO");
        deque.removeLast(); //     ==> DNNWTJNHSK
        deque.removeLast(); //     ==> XGVKLMHLKO
        deque.removeFirst(); //     ==> GPRGSBTZCO
        deque.addLast("OCCOBGOYTC");
        deque.addLast("CQQRXDLDCG");
    }

    // resize the underlying array
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = d[(head + i + 1) % d.length];
        }
        d = temp;
        head = d.length - 1;
        tail = n;
    }
}
