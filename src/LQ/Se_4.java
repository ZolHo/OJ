package LQ;

/**
 * 跑不出。。。
 * Created by hao on 16-12-3.
 */
public class Se_4 {
    public static long move(int n){
        if(n==1){
            return 1;
        }else{
            long temp1=move(n-1);
            long temp2=move(n-1);
            return temp1+temp2+1;
        }
    }
    public static void main(String args[])
    {
        System.out.println(move(64));
    }
}
