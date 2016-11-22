package POJ;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by hao on 16-11-19.
 * 三维迷宫
 */
public class BFS2251 {
    static int L, R, C;
    static int map[][][];
    static boolean vis[][][];
    static int cur[][] = {{1,0,0},{-1,0,0},{0,1,0},{0,-1,0},{0,0,1},{0,0,-1}};
    static int time;
    static int sx, sy, sz;

    static class node {
        public int x, y, z;
        public int step;
        public node(int x, int y, int z) {
            this.x = x; this.y = y; this.z = z;
            step = 0;
        }
    }

    static void BFS() {
        Queue<node> queue = new LinkedList<node>();
        node now, next, first;
        first = new node(sx, sy, sz);
        queue.add(first);
        vis[sx][sy][sz] = true;
        while (!queue.isEmpty()) {
            now = queue.peek();
            if (map[now.x][now.y][now.z] == 1) {
                time = now.step;
                return;
            }
            for (int i = 0; i < 6; i++) {
                next = new node(now.x+cur[i][0], now.y+cur[i][1], now.z+cur[i][2]);
                next.step = now.step + 1;
                if (judge(next.x, next.y, next.z) && !vis[next.x][next.y][next.z]) {
                    queue.add(next);
                    vis[next.x][next.y][next.z] = true;
                }
            }
            queue.poll();
        }
        return;
    }

    static boolean judge(int x, int y, int z) {
        if (x >= 0 && x < L && y >= 0 && y < R && z >= 0 && z < C && map[x][y][z] != -1) {
            return true;
        }
        return false;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            time = 0;
            L = scanner.nextInt();
            R = scanner.nextInt();
            C = scanner.nextInt();
            scanner.nextLine();
            if (L == 0 && R == 0 && C == 0) {
                break;
            }
            String get;
            map = new int[L][R][C];
            vis = new boolean[L][R][C];
            for (int i = 0; i < L; i++) {
                for (int j = 0; j < R; j++) {
                    get = scanner.nextLine();
                    for (int k = 0; k < C; k++) {
                        if (get.charAt(k) == '#') {
                            map[i][j][k] = -1;
                        } else if (get.charAt(k) == 'E') {
                            map[i][j][k] = 1;
                        } else if (get.charAt(k) == 'S') {
                            sx = i; sy = j; sz = k;
                            map[i][j][k] = 0;
                        } else {
                            map[i][j][k] = 0;
                        }
                        vis[i][j][k] = false;
                    }
                }
                scanner.nextLine();
            }
            BFS();
            if (time > 0) {
                System.out.println("Escaped in "+ time +" minute(s).");
            } else {
                System.out.println("Trapped!");
            }
        }
    }
}
