package POJ;

import java.util.*;

/**
 * Created by hao on 16-11-22.
 */
public class POJ3126Fail {
    static int aPrimeList[] = { // 素数表
        1009, 1153, 1201, 1217, 1249, 9649, 9697, 9857,
        1297, 1361, 1409, 1489, 1553, 1601, 1697, 1777, 1873,
        1889, 2017, 2081, 2113, 2129, 2161, 2273, 2417, 2593,
        2609, 2657, 2689, 2753, 2801, 2833, 2897, 3041, 3089,
        3121, 3137, 3169, 3217, 3313, 3329, 3361, 3457, 3617,
        3697, 3761, 3793, 3889, 4001, 4049, 4129, 4177, 4241,
        4273, 4289, 4337, 4481, 4513, 4561, 4657, 4673, 4721,
        4801, 4817, 4993, 5009, 5153, 5233, 5281, 5297, 5393,
        5441, 5521, 5569, 5857, 5953, 6113, 6257, 6337, 6353,
        6449, 6481, 6529, 6577, 6673, 6689, 6737, 6833, 6961,
        6977, 7057, 7121, 7297, 7393, 7457, 7489, 7537, 7649,
        7681, 7793, 7841, 7873, 7937, 8017, 8081, 8161, 8209,
        8273, 8353, 8369, 8513, 8609, 8641, 8689, 8737, 8753,
        8849, 8929, 9041, 9137, 9281, 9377, 9473, 9521, 9601
    };
    static int start, over;
    static boolean vis[];
    static int cur[] = {1, -1, 10, -10, 100, -100, 1000, -1000};
    static int times;
    static boolean judge(int x) {
        if (x < 1000 || x > 9999 || vis[x]) {
            return false;
        }
        boolean isPre;
        isPre = false;
        for (int i = 0; i < 125; i++) {
            if (aPrimeList[i] == x) {
                isPre = true;
                break;
            }
        }
        return isPre;
    }
    static public class node {
        static int x;
        static int step;
        public node(int x) {
            this.x = x;
            this.step = 0;
        }
    }
    static void BFS() {
        Queue<node> queue = new LinkedList<node>();
        queue.add(new node(start));
        node now, next;
        while (!queue.isEmpty()) {
            now = queue.poll();
            if (now.x == over) {
                times = now.step;
                return;
            }
            for (int i = 0; i < 8; i++) {
                for (int j = 1; j < 10; j++) {
                    if (judge(now.x + cur[i] * j)) {
                        next = new node(now.x + cur[i] * j);
                        queue.add(next);
                        next.step = now.step +1;
                        vis[next.x] = true;
                    }
                }

            }
        }
    }
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int N;
        N = scanner.nextInt();
        while (N-- != 0) {
            vis = new boolean[10000];
            Arrays.fill(vis, false);
            times = 0;
            start = scanner.nextInt();
            over = scanner.nextInt();
            BFS();
            System.out.println(times);
        }
    }
}

