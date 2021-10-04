/* *****************************************************************************
 *  Name:           Lucas Kamakura
 *  Date:           2021/09/29
 *  Description:    Deque implementation using a doubly linked list
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Implementing this as a doubly linked list
public class Deque<Item> implements Iterable<Item> {

    private int length;
    private Node first;
    private Node last;

    private class Node {
        Item item = null;
        Node next = null;
        Node prev = null;
    }

    // construct an empty deque
    public Deque() {
        length = 0;
        first = null;
        last = null;
    }

    // Show first item
    private void showFirst() {
        if (first != null) StdOut.println("First: " + first.item);
        else {
            StdOut.println("First is null");
        }

    }

    // Show last item
    private void showLast() {
        if (last != null) StdOut.println("Last: " + last.item);
        else {
            StdOut.println("Last is null");
        }

    }

    // is the deque empty?
    public boolean isEmpty() {
        if (length == 0) {
            return true;
        }
        return false;
    }

    // return the number of items on the deque
    public int size() {
        return length;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null) {
            throw new IllegalArgumentException("Cannot addFirst with null argument");
        }

        Node newNode = new Node();  // Create new node
        Node oldFirst = first;      // Create pointer for old first
        newNode.item = item;        // Store item in new node
        newNode.next = oldFirst;    // Point new node to old first

        if (oldFirst != null) {
            oldFirst.prev = newNode;    // Point old first to new Node
        }

        first = newNode;            // Make new node head of the list

        if (length == 0) {
            last = first;
        }

        length++;                   // Increase length by 1
    }

    // add the item to the back
    public void addLast(Item item) {

        if (item == null) {
            throw new IllegalArgumentException("Cannot addLast with null argument");
        }

        if (length == 0) {
            addFirst(item);
            return;
        }

        Node newNode = new Node();
        Node oldLast = last;
        newNode.item = item;
        newNode.prev = oldLast;
        oldLast.next = newNode;
        last = newNode;
        length++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (length == 0) {
            throw new NoSuchElementException("Cannot removeFirst when the queue is empty");
        }
        if (length == 1) {
            Item itemToReturn = first.item;
            first.item = null;
            last.item = null;
            last = null;
            first = null;
            length--;
            return itemToReturn;
        }

        Node oldFirst = first;
        first = first.next;
        first.prev = null;
        oldFirst.next = null;
        length--;

        return oldFirst.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (length == 0) {
            throw new NoSuchElementException("Cannot removeLast when the queue is empty");
        }
        if (length == 1) {
            return removeFirst();
        }

        Node oldLast = last;
        last = last.prev;
        last.next = null;
        oldLast.prev = null;
        length--;

        return oldLast.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException(
                    "This implementation of the Deque does not support removing during iteration");
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There is no next node");
            }

            Item item = current.item;
            current = current.next;
            return item;
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> testDeque = new Deque<Integer>();

        // Testing adding first when length == 0
        StdOut.println("--- Adding first when length == 0 ---");
        testDeque.addFirst(1);
        testDeque.showFirst();
        testDeque.showLast();
        testDeque.removeFirst();
        StdOut.println("---");

        // Testing adding last when length == 0
        StdOut.println("--- Adding last when length == 0 ---");
        testDeque.addLast(1);
        testDeque.showFirst();
        testDeque.showLast();
        testDeque.removeFirst();
        StdOut.println("---");

        // Testing adding first and removing first
        StdOut.println("--- Adding first and removing last ---");
        testDeque.addFirst(1);
        testDeque.showFirst();
        testDeque.showLast();
        testDeque.removeFirst();
        testDeque.showFirst();
        testDeque.showLast();
        StdOut.println("---");

        // Testing adding last and removing last
        StdOut.println("--- Adding last and removing last ---");
        testDeque.addLast(1);
        testDeque.showFirst();
        testDeque.showLast();
        testDeque.removeLast();
        testDeque.showFirst();
        testDeque.showLast();
        StdOut.println("---");

        // Testing adding first and removing last;
        StdOut.println("--- Adding first and removing last ---");
        testDeque.addFirst(1);
        testDeque.showFirst();
        testDeque.showLast();
        testDeque.removeLast();
        testDeque.showFirst();
        testDeque.showLast();
        StdOut.println("---");

        // Testing adding last and removing first;
        StdOut.println("--- Adding last and removing first ---");
        testDeque.addLast(1);
        testDeque.showFirst();
        testDeque.showLast();
        testDeque.removeFirst();
        testDeque.showFirst();
        testDeque.showLast();
        StdOut.println("---");

        // Testing length
        StdOut.println("--- Testing length ---");
        StdOut.println("Adding first 1000");
        for (int i = 0; i < 1000; i++) {
            testDeque.addFirst(i);
        }
        testDeque.showFirst();
        testDeque.showLast();
        StdOut.println(testDeque.size());

        StdOut.println("Removing first 500");
        for (int i = 0; i < 500; i++) {
            testDeque.removeFirst();
        }
        testDeque.showFirst();
        testDeque.showLast();
        StdOut.println(testDeque.size());

        StdOut.println("Removing remaining 500");
        for (int i = 0; i < 500; i++) {
            testDeque.removeFirst();
        }
        testDeque.showFirst();
        testDeque.showLast();
        StdOut.println(testDeque.size());

        StdOut.println("Adding last 1000");
        for (int i = 0; i < 1000; i++) {
            testDeque.addLast(i);
        }
        testDeque.showFirst();
        testDeque.showLast();
        StdOut.println(testDeque.size());

        StdOut.println("Removing last 500");
        for (int i = 0; i < 500; i++) {
            testDeque.removeLast();
        }
        testDeque.showFirst();
        testDeque.showLast();
        StdOut.println(testDeque.size());

        StdOut.println("Removing last 500");
        for (int i = 0; i < 500; i++) {
            testDeque.removeLast();
        }
        testDeque.showFirst();
        testDeque.showLast();
        StdOut.println(testDeque.size());

        // Testing isEmpty;
        StdOut.println("--- Testing isEmpty ---");
        testDeque.showFirst();
        testDeque.showLast();
        StdOut.println("Is the deque empty? " + testDeque.isEmpty());
        testDeque.addLast(1);
        testDeque.showFirst();
        testDeque.showLast();
        StdOut.println("Is the deque empty? " + testDeque.isEmpty());
        testDeque.removeFirst();
        testDeque.showFirst();
        testDeque.showLast();
        StdOut.println("Is the deque empty? " + testDeque.isEmpty());
        StdOut.println("---");

        // Randomized Add and Remove
        StdOut.println("--- Randomized Add and Remove ---");
        for (int i = 0; i < 1000; i++) {
            int addOrRemove = StdRandom.uniform(4);
            if (addOrRemove == 0) {
                int num = StdRandom.uniform(10000);
                StdOut.println("Adding First: " + num);
                testDeque.addFirst(num);
            }
            else if (addOrRemove == 1) {
                int num = StdRandom.uniform(10000);
                StdOut.println("Adding Last: " + num);
                testDeque.addFirst(num);
            }
            else if (addOrRemove == 2 && !testDeque.isEmpty()) {
                StdOut.println("Removing first");
                testDeque.removeFirst();
            }
            else if (addOrRemove == 3 && !testDeque.isEmpty()) {
                StdOut.println("Removing last");
                testDeque.removeLast();
            }
        }

        for (int item : testDeque) {
            StdOut.print(item + " ");
        }
        StdOut.println();
        StdOut.println(testDeque.size());
        testDeque.showFirst();
        testDeque.showLast();

        while (!testDeque.isEmpty()) {
            int firstOrLast = StdRandom.uniform(2);
            if (firstOrLast == 0) {
                StdOut.println("Removing first");
                testDeque.removeFirst();
            }
            else {
                StdOut.println("Removing last");
                testDeque.removeLast();
            }
        }

        StdOut.println(testDeque.size());
        testDeque.showFirst();
        testDeque.showLast();

    }
}
