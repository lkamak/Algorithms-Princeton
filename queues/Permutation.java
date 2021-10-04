/* *****************************************************************************
 *  Name:           Lucas Kamakura
 *  Date:           2021/09/30
 *  Description:    Permutation client that utilizes RandomizedQueue
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> permutationArray = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty()) {
            permutationArray.enqueue(StdIn.readString());
            if (permutationArray.size() > k) {
                permutationArray.dequeue();
            }
        }

        for (String s : permutationArray) {
            StdOut.println(s);
        }

    }
}
