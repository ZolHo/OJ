package CODEVS;

import java.util.Scanner;

/**
 * Created by hao on 16-11-12.
 */
public class TX1098 {
    Scanner scanner = new Scanner(System.in);
    int N;
    int A[];
    int ave = 0;
    int count = 0;

    public static void main(String arg[]) {
        TX1098 TX1098 = new TX1098();
        TX1098.inIt();
        TX1098.doIt(0, TX1098.N);
        System.out.println(TX1098.count);
    }

    private void inIt() {
        N = scanner.nextInt();
        A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = scanner.nextInt();
        }
        ave = getSum(0,N)/N;
    }

    private void doIt(int l, int r) {
        if (l >= r-1) {
            return;
        }
        int maxMark = getMax( l, r);
        A[maxMark] = ave;
        if (maxMark != l && (ave * (maxMark-l)) != (getSum(l, maxMark-1))) {
            count++;
            A[maxMark-1] = ave * (maxMark-l) - getSum(l, maxMark-1);
        }
        if (maxMark != r-1 && (ave * (r-maxMark-1)) != (getSum(maxMark+2, r))) {
            count++;
            A[maxMark+1] = ave * (r-maxMark-1) - getSum(maxMark+2, r);
        }
        doIt(l, maxMark);
        doIt(maxMark+1, r);
    }

    private int getSum(int l, int r){
        int sum = 0;
        for (int i = l; i < r; i++) {
            sum += A[i];
        }
        return sum;
    }

    private int getMax(int l, int r) {
        int max = A[l];
        int mark = l;
        for (int i = l; i < r; i++) {
            if (A[i] > max) {
                max = A[i];
                mark = i;
            }
        }
        return mark;
    }
}
