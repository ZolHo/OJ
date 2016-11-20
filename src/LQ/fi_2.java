package LQ;

/**
 * Created by hao on 16-11-20.
 */
public class fi_2 {
    static boolean vis[];
    static int get(int k) {
        for (int i = k; i < 10; i++) {
            if (!vis[i]) {
                vis[i] = true;
                return i;
            }
        }
        return 0;
    }
    public static void main(String args[]) {

    }
}
