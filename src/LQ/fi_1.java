package LQ;

import java.util.Scanner;

/**
 * Created by hao on 16-11-22.
 * 三层遍历
 */
public class fi_1 {
    static void toDo(int x) {
        for (int i = 0; true; i++) {
            //每层循环的退出条件是已有的大于x
            if (i*i > x) {
                break;
            }
            for (int j = 0; true; j++) {
                if (i*i + j*j > x) {
                    break;
                }
                for (int k = 0; true; k++) {
                    int y = x - i*i - j*j - k*k;
                    if (y < 0) {
                        break;
                    }

                    int l = (int) Math.sqrt(y);
                    if (l*l != y) {
                        continue;
                    } else {
                        System.out.println(i + " " + j + " " + k + " " + l);
                        return;
                    }
                }
            }
        }
    }
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        while (n-- != 0) {
            int x = scanner.nextInt();
            toDo(x);
        }
    }
}
