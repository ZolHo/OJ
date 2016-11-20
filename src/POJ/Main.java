package POJ;

import java.util.Scanner;

/**
 * Created by hao on 16-11-19.
 */
public class Main {
    static int L, R, C;
    static int map[][][];
    static boolean vis[][][];
    static int cur[][] = {{1,0,0},{-1,0,0},{0,1,0},{0,-1,0},{0,0,1},{0,0,-1}};
    static int time;
    static int sx, sy, sz;

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
                        if (get.charAt(C) == '#') {
                            map[i][j][k] = -1;
                        } else if (get.charAt(C) == 'E') {
                            map[i][j][k] = 1;
                        } else if (get.charAt(C) == 'S') {
                            sx = i; sy = j; sz = k;
                        } else {
                            map[i][j][k] = 0;
                        }
                    }
                }
            }
        }
    }
}
