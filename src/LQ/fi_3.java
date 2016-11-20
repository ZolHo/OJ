package LQ;

import java.util.Scanner;

/**
 * Created by hao on 16-11-20.
 * 先输出除0最小,然后全部从小到大输出
 */
public class fi_3 {
    static int[] num;
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int n = 0;
        num = new int[10];
        for (int i = 0; i < 10; i++) {
            num[i] = scanner.nextInt();
        }
        for (int i = 1; i < 10; i++) {
            if (num[i] != 0) {
                System.out.print(i);
                num[i] --;
                break;
            }
        }
        for (int i = 0; i < 10; i++) {
            while (num[i] > 0) {
                System.out.print(i);
                num[i] --;
            }
        }
        System.out.println();
    }
}
