package HDU;

import com.lancelot9.algorithm.DisjointSet;

import java.util.Scanner;

/**
 * Created by hao on 17-2-4.
 */
public class DisjointS1213 {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        DisjointSet set;
        int C = scanner.nextInt();
        while (C-- != 0) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            scanner.nextLine();
            set = new DisjointSet(n);
            for(int i = 0; i < m; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                scanner.nextLine();
                set.merge(x-1, y-1);
            }
            int count = 0;
            for (int i = 0; i < n; i++) {
                if (i == set.find(i)) {
                    count++;
                }
            }
            System.out.println(count);
            scanner.nextLine();
        }
    }
}
