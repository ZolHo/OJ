package POJ;

import java.util.Scanner;

/**
 * Created by hao on 16-11-17.
 */
public class Search1321 {

    static int map[][];
    static boolean vis[][];
    static int count;
    static int  n, k;

    static void DFS(int x, int m) {
        if (n - x < k - m) {
            return;
        }
        if (m >= k) {
            count ++;
            return;
        }
        if (x >= n) {
            return;
        }
        for (int i = 0; i < n; i++) {
            if (judge(x, i) && isOk(x, i)) {
                vis[x][i] = true;
                DFS(x+1, m+1);
                vis[x][i] = false;
            }
        }
        DFS(x+1, m);
    }

    static boolean judge(int x, int y) {
        if (x >= 0 && x < n && y>=0 && y < n && !vis[x][y]  && map[x][y] == 0) {
            return true;
        }
        return false;
    }

    static boolean isOk(int x, int y) {
        for (int i = x, j = 0; j < n; j++) {
            if (vis[i][j]) {
                return false;
            }
        }
        for (int j = y, i = 0; i < n; i++) {
            if (vis[i][j]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            count = 0;
            String get;
            n = scanner.nextInt();
            k = scanner.nextInt();
            scanner.nextLine();
            if (n == -1 && k == -1) {
                break;
            }
            map = new int[n][n];
            vis = new boolean[n][n];
            for (int i = 0; i < n; i++) {
                get = scanner.nextLine();
                for (int j = 0; j < n; j++) {
                    if (get.charAt(j) == '.') {
                        map[i][j] = 1;
                    } else {
                        map[i][j] = 0;
                    }
                    vis[i][j] = false;
                }
            }

            DFS(0, 0);
            System.out.println(count);
        }
    }
}
