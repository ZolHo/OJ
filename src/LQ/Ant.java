package LQ;

import java.util.Scanner;

/**
 * Created by hao on 17-3-19.
 */
public class Ant {
    public static final int[][] U_R_D_L = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public static boolean[][] map;
    public static int antHead;
    public static int k;

    public static void deal(int x, int y, int step) {
        if (step == k) {
            System.out.println(x + " " + y);
            return;
        }
        //掉头,true表示黑,false表示白
        if (map[x][y]) {
            antHead = (antHead + 1) % 4;
        } else {
            antHead = (antHead + 3) % 4;
        }
        map[x][y] = !map[x][y];
        deal(x + U_R_D_L[antHead][0], y + U_R_D_L[antHead][1], step + 1);
    }

    public static void main(String s[]) {
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        scanner.nextLine();
        map = new boolean[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (scanner.nextInt() == 1) {
                    map[i][j] = true;
                } else {
                    map[i][j] = false;
                }
            }
            scanner.nextLine();
        }
        int firstX = scanner.nextInt();
        int firstY = scanner.nextInt();
        String head = scanner.next();
        switch (head) {
            case "U": antHead = 0; break;
            case "R": antHead = 1; break;
            case "D": antHead = 2; break;
            case "L": antHead = 3; break;
        }
        k = scanner.nextInt();
        deal(firstX, firstY, 0);
    }
}
//5 6
//0 0 0 0 0 0
//0 0 0 0 0 0
//0 0 1 0 0 0
//0 0 0 0 0 0
//0 0 0 0 0 0
//2 3 L 5