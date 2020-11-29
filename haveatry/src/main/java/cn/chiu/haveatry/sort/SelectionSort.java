package cn.chiu.haveatry.sort;

/**
 * Created by yeachiu on 2019/6/18.
 */
public class SelectionSort {

    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++){
            int min = i;
            for (int j = i + 1; j < N; j++){
                if (less(a[j],a[i])) {
                    min = j;
                }
                exch(a,i,min);
            }
        }

    }

    private static boolean less(Comparable x, Comparable y) {
        return x.compareTo(y) < 0 ? true : false;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        temp = null;
    }
}
