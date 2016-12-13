package LQ;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by hao on 16-12-11.
 */
public class Fo_1 {
    static final int LENGTH = 4;
    static final int HEIGHT = 3;
    static int cur[][] ={{ 1, 0},{ -1, 0},{ 0, 1},{ 0, -1}};
    static boolean vis[][];
    static int times;
    static int step;
    /**
     * 输入一个点的坐标，如果该点坐标越界或已被访问，则无法被添加
     * @param x 需要判断的点的x坐标
     * @param y 需要判断的点的y坐标
     * @return 如果该点能够添加，则返回true，否则返回false
     */
    static boolean isLegal(int x, int y) {
        if (x >= 0 && x < HEIGHT
                && y >= 0 && y < LENGTH
                && vis[x][y] == false) {
            return true;
        } else {
            return false;
        }
    }
    static void addPoint(int x, int y) {

    }
    static void BFS(int x, int y) {

    }
    public static void main(String args[]) {
        times = 0;
        vis = new boolean[HEIGHT][LENGTH];
        Arrays.fill(vis, false);
        for (int i = 0; i < 12; i++) {
            BFS(i/4, i%4);
            vis[i/4][i%4] = true;
        }
    }
}
