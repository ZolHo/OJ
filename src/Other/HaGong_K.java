package Other;

import java.util.Scanner;

/**
 * Created by hao on 16-11-26.
 */
public class HaGong_K {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int T;
        T = scanner.nextInt();
        scanner.nextLine();
        while (T-- != 0) {
            String s = scanner.nextLine();
            String ans = "";
            for (int i = 0; i < s.length(); i++) {
                if (Character.isDigit(s.charAt(i))) {
                    String temp = "";
                    for (int j = 0; j < Integer.valueOf(s.charAt(i)) - '0'; j++) {
                        temp += ans.charAt(ans.length()-1);
                    }
                    ans += temp;
                } else {
                    ans += s.charAt(i);
                }
            }
            System.out.println(ans);
        }
    }
}
