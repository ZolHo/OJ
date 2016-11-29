package LQ;

import java.util.Arrays;

/**
 * Created by hao on 16-11-27.
 */
public class fi_4 {
    static boolean vis[] = new boolean[10];
    static int times;
    static int num[] = new int[8];
    static void C(int deep) {
        if (deep >= 8) {
            if (num[0] == 0 || num[4] == 0) {
                return;
            }
            int a = num[0] * 1000 + num[1] * 100 + num[2] * 10 + num[3];
            int b = num[4] * 1000 + num[5] * 100 + num[6] * 10 + num[1];
            int c = num[4] * 10000 + num[5] * 1000 + num[2] * 100 + num[1] * 10 +num[7];
            if (a+b == c) {
                times ++;
                System.out.println(a + "+" + b + "=" + c);
            }
            return;
        }
        for (int i = 0; i < 10; i++) {
            if (!vis[i]) {
                vis[i] = true;
                num[deep] = i;
                C(deep + 1);
                vis[i] = false;
            }
        }
    }

    public static  void main(String args[]) {
        times = 0;
        Arrays.fill(vis, false);
        C(0);
        System.out.println(times);
    }
}
