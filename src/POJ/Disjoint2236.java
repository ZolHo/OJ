package POJ;

import com.lancelot9.algorithm.DisjointSet;

import java.util.Scanner;

/**
 * Created by hao on 17-2-3.
 */


public class Disjoint2236 {
    static int N;
    static int d;
    static Node[] node;
    static class Node{
        public int x, y;
        public boolean isFix;
        public Node(int x, int y) {
            this.x = x;
            this.y = y;
            this.isFix = false;
        }
    }

    public static boolean dir(Node a, Node b) {
        int x = a.x - b.x;
        int y = a.y - b.y;
        if (x*x+y*y > d*d) {
            return false;
        } else {
            return true;
        }
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        N = scanner.nextInt();
        d = scanner.nextInt();
        node = new Node[N];
        scanner.nextLine();
        DisjointSet set = new DisjointSet(N);
        for (int i = 0; i < N; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            scanner.nextLine();
            node[i] = new Node(x, y);
        }
        String o;
        while (scanner.hasNext()) {
            int x, y;
            o = scanner.next();
            if (o.equals("O")) {
                x = scanner.nextInt() - 1;
                node[x].isFix = true;
                for (int i = 0; i < N; i ++) {
                    if (i == x) {
                        continue;
                    }
                    if (node[i].isFix && dir(node[x],node[i])) {
                        set.merge(i,x);
                    }
                }
            } else {
                x = scanner.nextInt() - 1;
                y = scanner.nextInt() - 1;
                if (node[x].isFix && node[y].isFix && set.find(x)==set.find(y)) {
                    System.out.println("SUCCESS");
                } else {
                    System.out.println("FAIL");
                }
            }
            scanner.nextLine();
        }
    }
}
