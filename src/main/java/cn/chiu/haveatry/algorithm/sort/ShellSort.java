package cn.chiu.haveatry.algorithm.sort;

/**
 * Created by yeachiu on 2019/6/18.
 *
 * 希尔排序 3x+1增量序列
 */
public class ShellSort {

    public static void sort(Comparable[] a) {

        int N = a.length;

        int h = 1;
        // 小于N/3的最大增量
        while (h < N/3) h = 3*h + 1;

        while (h >= 1) {
            for (int i = h; i < N; i ++){
                for (int j = i; j >= h && less(a[j], a[j-h]); j-=h)
                    exch(a, j, j-h);
            }
            h = h/3;
        }
    }

    private static boolean less(Comparable x, Comparable y) {
        return x.compareTo(y) < 0 ? true : false;
    }

    private static void exch(Comparable[] a, int i, int j) {
        print(a);
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        temp = null;
        print(a);
    }

    public static void main(String[] args) {
        Comparable[] arr = {2,45,7,32,12,3,1};
        sort(arr);
        print(arr);
    }

    private static void print(Comparable[] arr) {
        for (int i = 0; i < arr.length; i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }
}
/**
 * 希尔排序: 把记录按照下标的一定增量分组，对每组使用直接插入排序算法排序；随着增量逐渐减少，每组包含的关键词越来越多，
 * 当增量减至1时，整个文件恰好被分成一组，到此算法终止。
 *
 * 希尔排序是一种改进式的插入排序算法，它采用跳跃式分组的策略，这种策略使得整个数组在初始阶段达到从宏观上看基本有序，小的基本在前，大的基本在后。然后缩小增量，到增量为1时，其实多数情况下只需微调即可，不会涉及过多的数据移动。
 */
