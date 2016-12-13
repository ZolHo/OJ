package HDU;

import java.util.Scanner;
import java.util.Vector;

/**
 * Created by hao on 16-12-7.
 */
public class Water1029 {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        Vector<Integer> vector = new Vector<>();
        while (scanner.hasNext()) {
            int num = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < num; i++) {
                vector.add(scanner.nextInt());
            }
            System.out.println(vector.get(num/2));
            vector.clear();
            scanner.nextLine();
        }
    }
}
