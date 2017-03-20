package LQ;

import java.util.Scanner;

/**
 * Created by hao on 17-3-20.
 */
public class FenTangGUo {
    public static int peopleSum;
    public static int ans = 0;
    public static int[] map;

    public static boolean isOK() {
        for (int i = 1; i < peopleSum; i ++) {
            if (map[i] != map[i-1]) {
                return false;
            }
        }
        return true;
    }

    public static void add() {
        for (int i = 0; i < peopleSum; i++) {
            if (map[i]%2==1) {
                map[i] ++;
                ans++;
            }
        }
    }

    public static void deal() {
        while(!isOK()) {
            int one = map[0] /= 2;
            for (int i = 0; i < peopleSum - 1; i++) {
                map[i] += map[i + 1] /= 2;
            }
            map[peopleSum - 1] += one;
            add();
        }
        System.out.println(ans);
        return;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        peopleSum = scanner.nextInt();
        map = new int[peopleSum];
        scanner.nextLine();
        for (int i = 0; i < peopleSum; i ++) {
            map[i] = scanner.nextInt();
        }
        deal();
    }
}
