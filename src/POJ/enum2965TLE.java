package POJ;

import java.util.Scanner;

/**
 * Created by hao on 16-11-15.
 */
public class enum2965TLE {

    static boolean [][] map = new boolean[4][4];
    static boolean[][] vis = new boolean[4][4];

    static void change(int x, int y) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == x || j == y) {
                    map[i][j] = !map[i][j];
                }
            }
        }

        vis[x][y] = !vis[x][y];
    }

    //全排列:C16|count
    static boolean doIt(int xy, int count) {
        if (count <= 0 || xy >= 16) {
            return false;
        }
        int x = xy%4;
        int y = xy/4;
        //为了记录路线,采用了那么蠢的方法..应该能优化
        change(x, y);
        boolean f = doIt(xy+1, --count);
        if (isOk()) return true;
        change(x, y);
        boolean s = doIt(xy+1, ++count);
        if (isOk()) return true;
        return false;
    }

    static boolean isOk() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!map[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 4; i++) {
            String temp = new String(scanner.nextLine());
            for (int j = 0; j < 4; j++) {
                if (temp.charAt(j) == '-') {
                    map[i][j] = true;
                } else {
                    map[i][j] = false;
                }
                vis[i][j] = false;
            }
        }

        for (int i = 0; i < 16; i++) {
            if (doIt(0, i+1)) {
                System.out.println(i+1);
                break;
            }
        }
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                if (vis[i-1][j-1]) {
                    System.out.println(i + " " + j);
                }
            }
        }
    }
}
