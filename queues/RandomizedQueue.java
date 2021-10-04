/* *****************************************************************************
 *  Name:           Lucas Kamakura
 *  Date:           2021/09/30
 *  Description:    Generic Randomized Queue
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] randArray;
    private int arrayLength;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        randArray = (Item[]) new Object[1];
        arrayLength = 1;
        size = 0;
    }

    // resizing function
    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < size; i++) {
            temp[i] = randArray[i];
        }
        randArray = temp;
        arrayLength = max;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Enqueue argument cannot be null");
        }
        if (size == randArray.length) {
            resize(2 * randArray.length);
        }

        randArray[size] = item;

        if (size > 0) {
            int randIndex = StdRandom.uniform(size + 1);
            Item tempItem = randArray[randIndex];
            randArray[randIndex] = randArray[size];
            randArray[size] = tempItem;
        }

        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot deque when the queue is empty");
        }
        if (size > 0 && size <= randArray.length / 4) {
            resize(randArray.length / 2);
        }

        Item dequeItem = randArray[size - 1];
        randArray[size - 1] = null;
        size--;

        return dequeItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot call sample when the queue is empty");
        }
        return randArray[StdRandom.uniform(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return (i < size);
        }

        public Item next() {
            if (i >= size) {
                throw new NoSuchElementException("Iterator is exhausted");
            }
            return randArray[i++];
        }

        public void remove() {
            throw new UnsupportedOperationException(
                    "This implementation of the Deque does not support removing during iteration");
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        /*
        int[] testArray = new int[5];

        RandomizedQueue<Integer> queue;
        for (int queueTrial = 0; queueTrial < 100000; queueTrial++) {
            queue = new RandomizedQueue<Integer>();
            for (int i = 0; i < 5; i++) {
                queue.enqueue(i);
            }
            int i = 0;
            for (int item : queue) {
                testArray[i] += item;
                i++;
            }
        }

        for (int i = 0; i < 5; i++) {
            StdOut.println("Index " + i + " = " + testArray[i]);
        }

        queue = new RandomizedQueue<Integer>();
        queue.enqueue(4);
        StdOut.println(queue.size());
        StdOut.println(queue.arrayLength);
        queue.enqueue(4);
        StdOut.println(queue.size());
        StdOut.println(queue.arrayLength);
        queue.enqueue(0);
        StdOut.println(queue.size());
        StdOut.println(queue.arrayLength);
        queue.enqueue(0);
        StdOut.println(queue.size());
        StdOut.println(queue.arrayLength);
        queue.dequeue();
        */

    }

}
