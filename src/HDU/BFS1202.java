package HDU;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by hao on 16-11-13.
 *
 */

public class BFS1202 {

    private static int n, m, t;
    private static int dir[][] = { { 0, 1} , { 0, -1 } , { 1, 0} , { -1, 0} };//遍历四个方向
    private static int map[][][];
    private static boolean vis[][][];
    private static int count = 0;

    private static void BFS(int x, int y, int z){
        Queue<node> queue = new LinkedList<>();
        node now, next, thisNode;
        thisNode = new node(x, y, z);
        queue.add(thisNode);
        vis[thisNode.x][thisNode.y][thisNode.z] = true;
        while (!queue.isEmpty()) {
            now = queue.peek();
            if (map[now.x][now.y][now.z] == (int)'P') {
                count = now.t;
                return;
            }
            for (int i = 0; i < 4; i++) {
                next = new node(now.x+dir[i][0], now.y+dir[i][1], now.z);
                next.t = now.t+1;
                if (judg(next.x, next.y, next.z) && !vis[next.x][next.y][next.z]) {
                    if (map[next.x][next.y][next.z] == (int)'#') {
                        vis[next.x][next.y][next.z] = true;
                        next.z = 1-next.z;
                        queue.add(next);
                        vis[next.x][next.y][next.z] = true;
                    } else {
                        queue.add(next);
                        vis[next.x][next.y][next.z] = true;
                    }
                }
            }
            queue.poll();
        }
        return;
    }

    private static class node {
        public int x, y, z;
        public int t;
        public node(int x, int y, int z) {
            this.x = x; this.y = y; this.z = z;
            t = 0;
        }
    }

    private static boolean judg(int x, int y, int z) {
        if (x>=0 && x<n && y>=0 && y<m && map[x][y][z] != (int)'*') {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int c = scanner.nextInt();
        while (c-- != 0) {
            count = 0;
            n = scanner.nextInt();
            m = scanner.nextInt();
            t = scanner.nextInt();
            scanner.nextLine();
            map = new int[n][m][2];
            vis = new boolean[n][m][2];
            for (int k = 0; k < 2; k++) {
                for (int i = 0; i < n; i++) {
                    String input = scanner.nextLine();
                    for (int j = 0; j < m; j++) {
                        map[i][j][k] = input.charAt(j);
                        vis[i][j][k] = false;
                    }
                }
                if (k == 0)
                scanner.nextLine();
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (map[i][j][0] == (int)'*' && map[i][j][1] == (int)'#') {
                        map[i][j][1] = (int)'*';
                    } else if (map[i][j][1] == (int)'*' && map[i][j][0] == (int)'#') {
                        map[i][j][0] = (int)'*';
                    } else if (map[i][j][0] == (int)'#' && map[i][j][1] == (int)'#') {
                        map[i][j][0] = (int)'*';
                        map[i][j][1] = (int)'*';
                    }
                }
            }
            BFS(0, 0, 0);
            if (count<=t && count!=0) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }
}
