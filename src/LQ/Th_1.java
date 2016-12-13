package LQ;

/**
 * Created by hao on 16-12-6.
 */
public class Th_1 {
    static int times;

    static void BFS(int jiu, int dian, int hua) {
        if (dian < 0 || hua < 1 || jiu < 0) { //出口
            return;
        }
        if (dian == 0 && hua == 1 && jiu == 1) { //达成条件
            times ++ ;
            return;
        }
        if (dian > 0) { //还能遇见店
            BFS(jiu * 2, dian - 1, hua );
        }
        if (hua > 0) { //还能遇见花
            BFS(jiu - 1, dian, hua -1);
        }
    }

    public static void main(String args[]) {
        times = 0;
        BFS(2, 5, 10);
        System.out.println(times);
    }
}
