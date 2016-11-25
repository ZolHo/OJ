package POJ;

import java.util.Scanner;

/**
 * Created by hao on 16-11-22.
 * 就是输入01矩阵全变0,思路是只遍历第一排的可能,后面的全靠
 * 第一排推导出来,最后检测最后一排是不是都是0结束
 */
public class Enum3279WA {
    static int M, N;
    static int map[][];
    static int copy[][];
    static boolean ans[][];
    static int cur[][] = {{1, 0},{-1, 0},{0, 1},{0, -1}};
    static boolean flag;

    static boolean judge(int x, int y) {
        if (x >= 0 && x < M && y >= 0 && y < N) {
            return true;
        }
        return false;
    }
    static boolean isOk() {
        for (int i = 0; i < N; i++) {
            if (copy[M-1][i] == 1) {
                return false;
            }
        }
        return true;
    }

    static void change(int x, int y) {
        ans[x][y] = !ans[x][y];
        map[x][y] = 1 - map[x][y];
        for (int i = 0; i < 4; i++) {
            if (judge(x+cur[i][0], y+cur[i][1])) {
                map[x+cur[i][0]][y+cur[i][1]] = 1 - map[x+cur[i][0]][y+cur[i][1]];
            }
        }
    }
    static void changeCopy(int x, int y) {
        ans[x][y] = !ans[x][y];
        copy[x][y] = 1 - copy[x][y];
        for (int i = 0; i < 4; i++) {
            if (judge(x+cur[i][0], y+cur[i][1])) {
                copy[x+cur[i][0]][y+cur[i][1]] = 1 - copy[x+cur[i][0]][y+cur[i][1]];
            }
        }
    }
    static void doElse() {
        for (int i = 1; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (copy[i-1][j] == 1) {
                    changeCopy(i, j);
                }
            }
        }
    }

    static void seach(int deep) {
        if (flag) {
            return;
        }
        if (deep >= N) {
            copy = map.clone();
            doElse();
            if (isOk()) {
                flag = true;
                for (int i = 0; i < M; i++) {
                    for (int j = 0; j < N; j++) {
                        if (ans[i][j] == false) {
                            System.out.print(0);
                        }else {
                            System.out.print(1);
                        }
                        if (j != N-1) {
                            System.out.print(" ");
                        }
                    }
                    System.out.println();
                }
            }
            copy = map.clone();
            doElse();
            return;
        }
        seach(deep+1);
        change(0, deep);
        seach(deep+1);
        change(0, deep);
    }
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        M = scanner.nextInt();
        N = scanner.nextInt();
        scanner.nextLine();
        flag = false;
        map = new int[M][N];
        ans = new boolean[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = scanner.nextInt();
                ans[i][j] = false;
            }
            scanner.nextLine();
        }
        seach(0);
        if (!flag) {
            System.out.println("IMPOSSIBLE");
        }
    }
}
