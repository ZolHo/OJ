package LQ;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by hao on 16-12-11.
 */
public class Fo_2 {
    static int cur[][] ={{ 1, 0},{ -1, 0},{ 0, 1},{ 0, -1}};
    static boolean vis[][];
    static int get[];
    static int count;
    /**
     * C(A,B) ,即从A个数中取B个数的组合,deep表示当前已经取出的个数
     */
    static void combination(int deep,int pre, int A, int B) {
        if (deep >= B) {
//            count++;
//            for (int i = 0; i < 5; i++) {
//                System.out.print(get[i] + " ");
//            }
//            System.out.println();

            setVis();
            //DFS(get[0]/4, get[0]%4);
            BFS(get[0]);
            if (isOk()) {
                count++;
            }

            return;
        }
        for (int i = pre+1; i < A; i++) {
            get[deep] = i;
            combination(deep+1, i, A, B);
        }
    }

    /**
     * 
     */
    static boolean isOk() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (false == vis[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * 深度优先遍历get[0]所在的联通图,并将所有访问过的标记true
     */
    static void DFS(int x, int y) {
        vis[x][y] = true;
        for (int i = 0; i < 4; i++) {
            int nextX = x+cur[i][0];
            int nextY = y+cur[i][1];
            if (isLegal(nextX, nextY)) {
                DFS(nextX, nextY);
            }
        }
    }
    /**
     * 广度优先遍历get[0]所在的连通图,并将所有访问到的进行标记
     */
    static void BFS(int pos) {
        Queue<Integer> queue = new LinkedList<>();
        int now, next;
        queue.add(pos);
        vis[pos/4][pos%4] = true;
        while(!queue.isEmpty()) {
            int nowX, nowY, nextX, nextY;
            now = queue.poll(); //取得并弹出列队顶端的值
                //在需要搜索到某一状态的题目里面,一般在此处判断now是不是要求的状态,是则进行操作
            nowX = now/4;
            nowY = now%4;
            for (int i = 0; i < 4; i++) {
                nextX = nowX+cur[i][0];
                nextY = nowY+cur[i][1];
                next = nextX*4 + nextY;
                if (isLegal(nextX, nextY)) {
                    vis[nextX][nextY] = true;
                    queue.add(next);
                }
            }
        }
    }
    /**
     * 判断这个点是否合法
     */
    static boolean isLegal(int x, int y) {
        if (x >= 0 && x < 3 && y >= 0 && y < 4 && false == vis[x][y]) {
            return true;
        }
        return false;
    }
    /**
     * 根据组合好的数字标记地图
     */
    static void setVis() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                vis[i][j] = true;
            }
        }
        for (int i = 0; i < 5; i++) {
            vis[ get[i]/4 ][ get[i]%4 ] = false;
        }
    }

    public static void main(String args[]) {
        vis = new boolean[3][4];
        get = new int[5];
        count = 0;
        combination(0, -1, 12, 5);
        System.out.print(count);
    }
}
