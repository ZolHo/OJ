package POJ;

import java.util.Scanner;

/**
 * Flip Game
 * Created by hao on 16-11-7.
 */
public class Enum1753Fail {

    Scanner scanner = new Scanner(System.in);
    boolean boardCast[] = new boolean[16];
    String line;
    boolean over = false;
    int vis[] = new int[16];

   public static void main(String args[]) {
       Enum1753Fail instance = new Enum1753Fail();
       instance.boardGet();
       instance.doIt(0,0);
       instance.putOut();
       instance.scanner.close();
   }

   //输出结果
   private void putOut() {
       if (over) {
           int times = 0;
           for (int i=0; i<16; i++) {
               if(vis[i]%2==1) times++;
               System.out.print(vis[i]%2);
               if ((i+1)%4==0) System.out.println();
           }
           System.out.println(times);
       } else {
           System.out.println("Impossible");
       }
   }

   //取得键盘输入..好麻烦的感觉
   private void boardGet() {
       for (int i=0; i<4; i++) {
           line = scanner.nextLine();
           for (int j=0; j<4; j++) {
               if(line.charAt(j)=='w') {
                   boardCast[i*4+j] = true;
               } else {
                   boardCast[i*4+j] = false;
               }
               vis[i*4+j] = 0;
           }
       }
   }

   //判断是否完成
   private boolean isOver() {
       for (int i=0; i<4; i++) {
           for (int j=0; j<4; j++) {
               if (boardCast[i*4+j]!=boardCast[0]) {
                   return false;
               }
           }
       }
       return true;
   }

   //翻转
   private void flip(int x, int y) {
       boardCast[x*4+y] = !boardCast[x*4+y];
       if (x-1>=0) boardCast[x*4+y-4] = !boardCast[x*4+y-4];
       if (x+1<4) boardCast[x*4+y+4] = !boardCast[x*4+y+4];
       if (y-1>=0) boardCast[x*4+y-1] = !boardCast[x*4+y-1];
       if (y+1<4) boardCast[x*4+y+1] = !boardCast[x*4+y+1];
   }

   //递归遍历
   public void doIt(int x, int y) {
       if (isOver()) {
           over = true;
           return;
       }

       flip(x,y);
       vis[x*4+y]++;

       for (int num = x*4+y+1; num<16; num++) {
           doIt(num/4,num%4);
       }

       if (isOver()) {
           over = true;
           return;
       }

       flip(x,y);
       vis[x*4+y]++;

       for (int num = x*4+y+1; num<16; num++) {
           doIt(num/4,num%4);
       }
   }
}
