package POJ;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by hao on 16-11-22.
 */
public class DFS1426 {
    static BigInteger n;
    static boolean flag;
    static void DFS(BigInteger now, int step) {
        if (step >= 19 || flag) {
            return;
        }
        if (now.remainder(n) == BigInteger.valueOf(0)) {
            System.out.println(now);
            flag = true;
            return;
        }
        BigInteger next = now.multiply(BigInteger.TEN);
        DFS(next, step+1);
        DFS(next.add(BigInteger.ONE), step+1);
        return;
    }
//    static void BFS() {
//        Queue<BigInteger> queue = new LinkedList<BigInteger>();
//        queue.add(BigInteger.ONE);
//        while (!queue.isEmpty()) {
//            BigInteger now = queue.poll();
//            if (now.remainder(n) == BigInteger.ZERO) {
//                System.out.println(now);
//                return;
//            }
//            queue.add(now.multiply(BigInteger.TEN));
//            queue.add(now.multiply(BigInteger.TEN).add(BigInteger.ONE));
//        }
//    }
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String temp = "";
            temp += scanner.nextInt();
            n = new BigInteger(temp);
            if (n == BigInteger.valueOf(0)) {
                break;
            }
            flag = false;
            DFS(BigInteger.valueOf(1), 0);
//            BFS();
        }
    }
}
