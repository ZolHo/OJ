package CODEVS;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static class Y{
        public int k;
        public String a;

    }
    static Y[] y;
    public static void main(String[] args) {
        y = new Y[10010];
        Scanner cin = new Scanner(System.in);
        int totalNum = cin.nextInt();
        for(int i=0;i<=2*totalNum+1;i++){
            y[i] = new Y();
        }
        for(int i=1;i<=totalNum;i++){
            y[i].a="Hao";
        }
        for(int i=totalNum;i<=2*totalNum;i++){
        	y[i].a="Huai";
        }
        for(int i=1;i<=totalNum;i++)
            if(yuesefu(totalNum, i))
                break;

    }

    public static boolean yuesefu(int totalNum, int countNum) {
        // 初始化人数
        List<Integer> start = new ArrayList<Integer>();
        for (int i = 1; i <= 2*totalNum; i++) {
            start.add(y[i].k);
        }
        //从第K个开始计数
        int k = 1;
        while (start.size() >0) {
            k = k + countNum;
            //第m人的索引位置
            k = k % (start.size()) - 1;
            // 判断是否到队尾

            if (k < 0) {
                if(y[start.size()-1].a.equals("Huai"))return false;
                System.out.println(start.get(start.size()-1));
                start.remove(start.size() - 1);
                k = 0;
            } else {
                if(y[start.size()-1].a.equals("Huai"))return false;
                System.out.println(start.get(k));
                start.remove(k);
            }
        }
        return true;
    }
}
