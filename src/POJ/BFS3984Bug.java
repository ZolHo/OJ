package POJ;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.*;

/**
 * Created by hao on 16-11-21.
 */
public class BFS3984Bug {
    static int map[][];
    static int cur[][] = {{0,1},{0,-1},{1,0},{-1,0}};
    static boolean vis[][];
    static class node {
        static int x, y, times;
        static String step;
        public node(int x, int y, String step) {
            this.x = x;
            this.y = y;
            this.step = new String(step) + x + y;
            times = 0;
        }
    }
    static boolean judge(int x, int y) {
        if (x >= 0 && x < 5 && y >= 0 && y < 5 && !vis[x][y] && map[x][y] != 1) {
            return true;
        }
        return false;
    }
    static node BFS() {
        Queue<node> queue = new LinkedList<node>();
        node fir = new node(0, 0, "00");
        queue.add(fir);
        node now, next;
        vis[0][0] = true;
        while(!queue.isEmpty()) {
            now = queue.peek();
            if (map[now.x][now.y] == 2) {
                return now;
            }
            for (int i = 0; i < 4; i++) {
                if (judge(now.x+cur[i][0], now.y+cur[i][1])) {
                    next = new node(now.x+cur[i][0], now.y+cur[i][1], now.step);
                    next.times = now.times + 1;
                    queue.add(next);
                    vis[next.x][next.y] = true;
                }
            }
            queue.poll();
        }
        return new node(0, 0, "");
    }
    public static void main(String args[]) {
        map = new int[5][5];
        vis = new boolean[5][5];
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                map[i][j] = scanner.nextInt();
                vis[i][j] = false;
            }
            scanner.nextLine();
        }
        map[4][4] = 2;
        node ans = BFS();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (vis[i][j])
                System.out.print(1);
                else System.out.print(0);
            }
            System.out.println();
        }
        System.out.println(ans.times);
        for (int i = 0; i < ans.times; i += 2) {
            System.out.println("("+ans.step.charAt(i) + ", " + ans.step.charAt(i+1) + ")");
        }
    }
}
