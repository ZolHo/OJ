package POJ;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by hao on 16-11-24.
 * Dijkstra算法
 */
public class Main {
    static final int INF = 10000;
    static int dis[];
    static int g[][];
    static int N;
    static boolean v[];

    static void dijkstra() {
        for (int i=1; i<=N; i++) {
            int mark = -1;
            int minDis = INF;
            for (int j = 1; j <= N; j++) {
                if (!v[j] && dis[j] < minDis) {
                    mark = j;
                    minDis = dis[j];
                }
            }
            v[mark] = true;
            for (int j = 1; j <= N; j++) {
                if (!v[j]) {
                    dis[j] = Math.min(dis[j], dis[mark] + g[mark][j]);
                }
            }
        }
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        N = scanner.nextInt();
        dis = new int[N+1];
        g = new int[N+1][N+1];
        v = new boolean[N+1];
        Arrays.fill(dis, INF);
        Arrays.fill(v, false);
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= N; j++) {
                g[i][j] = INF;
            }
        }
        scanner.nextLine();
        dis[1] = 0;
        for (int i=0; i<T; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int a = scanner.nextInt();
            if (g[x][y] > a) {
                g[x][y] = a;
            }
            scanner.nextLine();
        }
        dijkstra();
        System.out.println(dis[N]);
    }
}
