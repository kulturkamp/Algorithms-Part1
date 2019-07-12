

import java.util.Scanner;

public class Permutation {
    private static Scanner s;
    public static void main(String[] args)    {

        RandomizedQueue<String> strs = new RandomizedQueue<String>();
        while (!s.hasNext()) {
            strs.enqueue(s.nextLine());
        }

        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) {
           System.out.println(strs.dequeue());
        }
    }
}
