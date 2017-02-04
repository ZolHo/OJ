package POJ;

import com.lancelot9.algorithm.DisjointSet;

import java.util.Scanner;

/**
 * Created by hao on 17-2-4.
 */

public class DisjointS1611 {
    public static DisjointSet set;
    public static int n;
    public static int m;
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            n = scanner.nextInt();
            m = scanner.nextInt();
            scanner.nextLine();
            if (n == 0 && m == 0) {
                break;
            }
            set = new DisjointSet(n);
            for (int i = 0; i < m; i ++) {
                int k = scanner.nextInt();
                int x = scanner.nextInt();
                for (int j = 0; j < k-1; j ++) {
                    set.merge(x, scanner.nextInt());
                }
                scanner.nextLine();
            }
            int count = 0;
            for (int i = 0; i < n; i++) {
                if (set.find(i) == set.find(0)) {
                    count ++;
                }
            }
            System.out.println(count);
        }
    }
}
