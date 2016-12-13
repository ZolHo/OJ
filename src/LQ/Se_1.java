package LQ;

/**
 * 以总共的金币为基础，判断能否满足条件
 * Created by hao on 16-12-3.
 */
public class Se_1 {
    public static void main(String args[]) {
        boolean flag = false;
        for (int i = 1000; true; i++) {
            if (flag) {
                break;
            }
            int n = 0;
            int count = i;
            while (n++ <= 5) {
                if (n == 6 && count > 1000) {
                    System.out.println( i );
                    flag = true;
                }
                if ((count+1) % 5 != 0) {
                    break;
                } else {
                    count = (count+1) / 5 * 4;
                }
            }
        }
    }
}
