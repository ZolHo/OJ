package LQ;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

/**
 * Created by hao on 16-11-20.
 * Just Do It
 * 从个位开始遍历
 */
public class fi_2 {
    static boolean vis[];
    //3*3存放三个数的
    static int ans[][];
    static int times;
    //deep是位数,add是进位标志
    static void doIt(int deep, int add){
        //三位数都符合要求后输出
        if (deep >= 3) {
            times ++;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(ans[i][j]);
                }
                if (i == 0) {
                    System.out.print(" + ");
                } else if (i == 1) {
                    System.out.print(" = ");
                }
            }
            System.out.println();
            return;
        }

        for (int i = 1; i < 10; i++) {
            if (!vis[i]) {
                vis[i] = true;
                ans[0][2-deep] = i;
                for (int j = 1; j < 10; j++) {
                    int thr = i+j+add;
                    if (!vis[j] && !vis[thr%10] && thr%10 != 0 && thr%10 != j) {
                        if (deep == 2 && thr >= 10) {
                            continue;
                        }
                        vis[j] = true;
                        vis[thr%10] = true;
                        ans[1][2-deep] = j;
                        ans[2][2-deep] = thr%10;
                        doIt(deep + 1, thr/10);
                        vis[j] = false;
                        vis[thr%10] = false;
                    }
                }
                vis[i] = false;
            }
        }
    }

    public static void main(String args[]) {
        times = 0;
        ans = new int[3][3];
        vis = new boolean[10];
        for (int i = 0; i < 10; i++) {
            vis[i] = false;
        }
        doIt(0, 0);
        System.out.println(times);
    }
}
