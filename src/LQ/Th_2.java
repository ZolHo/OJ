package LQ;

/**
 * Created by hao on 16-12-6.
 */
public class Th_2 {
    public static void main(String[] args) {
        int a[] = new int[12];
        for(int i=0; i<12; i++){
            a[i] = i+1;
        }
        int r[] = new int[6];
        find(a, 0, r);
    }
    static void find(int[] a,  int k, int[] r){
        if(k >= 12){
            return;
        }
        for(int i=k; i<a.length; i++){
            int temp = a[k];
            a[k] = a[i];
            a[i] = temp;
            if(a[0] == 1 && a[1] == 8 && a[11]==3){
                r[0] = a[0] + a[2] + a[5] + a[7];
                r[1] = a[7] + a[8] + a[9] + a[10];
                r[2] = a[10] + a[6] + a[3] + a[0];
                r[3] = a[1] + a[5] + a[8] + a[11];
                r[4] = a[11] + a[9] + a[6] + a[4];
                r[5] = a[4] + a[3] + a[2] + a[1];
                if(check(r)){
                    System.out.println(a[5]);
                    return;
                }
            }
            find(a, k+1, r);
            temp = a[k];
            a[k] = a[i];
            a[i] = temp;
        }
    }
    static boolean check(int[] r){ //判断是否
        boolean flag = true;
        for(int j=0; j<5; j++){
            if(r[j+1] != r[j]){
                flag = false;
                break;
            }
        }
        return flag;
    }
}