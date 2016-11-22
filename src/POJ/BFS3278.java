package POJ;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by hao on 16-11-20.
 * You can only walk one step or have a double
 * and go to position K from N.
 */
public class BFS3278 {
    static int N;
    static int K;
    static int times;
    static boolean map[];
    static class node {
        public int pos;
        public int step;
        public node(int N,int step) {
            this.pos = N;
            this.step = step;
        }
    }
    static boolean judge(int pos) {
        if (pos >= 0 && pos <= 100000 && !map[pos]) {
            return true;
        }
        return false;
    }
    static void BFS(node fir) {
        Queue<node> queue = new LinkedList<node>();
        node next, now;
        queue.add(fir);
        map[fir.pos] = true;
        while (!queue.isEmpty()) {
            now = queue.poll();
            if (now.pos == K) {
                times = now.step;
                return;
            }
            next = new node(now.pos+1, now.step+1);
            if (judge(next.pos)) {
                queue.add(next);
                map[next.pos] = true;
            }
            next = new node(now.pos-1, now.step+1);
            if (judge(next.pos)) {
                queue.add(next);
                map[next.pos] = true;
            }
            if (now.pos > K) continue;
            next = new node(now.pos*2, now.step+1);
            if (judge(next.pos)) {
                queue.add(next);
                map[next.pos] = true;
            }
        }
    }
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        times = 0;
        N = scanner.nextInt();
        K = scanner.nextInt();
        map = new boolean[100001];
        Arrays.fill(map, false);
        node fir = new node(N, 0);
        BFS(fir);
        System.out.println(times);
    }
}
