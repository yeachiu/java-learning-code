package cn.chiu.haveatry.dataStructures.string;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by yeachiu on 2019/7/2.
 *
 * 手写简单String类
 */
public class MyString {

    // 此类精髓
    private final char value[];

    public MyString() {
        this.value = new char[0];
    }

    public MyString(MyString str) {
        this.value = str.value;
    }

    public MyString(char[] value) {
        this.value = Arrays.copyOf(value,value.length);
    }

    /**
     * 字符串长度
     * @return
     */
    public int length() {
        return value.length;
    }

    /**
     * 是否为空串
     * @return
     */
    public boolean isEmpty() {
        return value.length == 0;
    }

    /**
     * 字符串拼接
     * @param S
     * @return
     */
    public MyString concat(MyString S) {
        int i = 0;
        int len1 = value.length;
        int len2 = S.value.length;
        if (len2 == 0)
            return this;
        char[] newStr = new char[len1 + len2];
        while (i < newStr.length) {
            if (i < len1)
                newStr[i] = value[i];
            else
                newStr[i] = S.value[i - len1];
        }
        return new MyString(newStr);
    }

    /**
     * 字符串替换
     * @param target
     * @param toReplace
     */
    public MyString replace(MyString target, MyString toReplace) {
        MyString newStr;


        return null;
    }

    /**
     * 返回第一个子串位置
     * @param S
     * @param pos
     * @return
     */
    public int index(MyString S, int pos) {
        int n, m, i;
        MyString sub;
        if (pos > 0) {
            n = this.length();
            m = S.length();
            i = pos;
            while (i <= n - m + 1) {
                sub = subString(i,m);   //取主串第i个位置，长度与S相等的子串
                if (sub.compareTo(S) != 0)    // 两串不相等
                    i++;
                else                            // 相等
                    return i;                   //返回位置
            }
        }
        return -1;      //无子串与S相等，返回-1
    }

    /**
     * 字符串截取
     * @param start
     * @param len
     * @return
     */
    public MyString subString(int start, int len) {
        char[] arr = new char[len];
        for (int i = start; i < len; i++) {
            arr[i-start] = value[i];
        }
        return new MyString(arr);
    }

    /**
     * 字符串比较
     * @param str
     * @return
     */
    public int compareTo(MyString str) {
        int len1 = value.length;
        int len2 = str.value.length;
        int lim = Math.min(len1, len2);
        char s1[] = value;
        char s2[] = str.value;

        int k = 0;
        char c1, c2;
        while (k < lim) {
            c1 = s1[k];
            c2 = s2[k];
            if (c1 != c2) {
                return c1 - c2;
            }
        }

        return len1 - len2;
    }

    public boolean contains(MyString S) {
        return index(S,0) > -1;
    }

    ////////////////////////// 匹配算法 ///////////////////////////////

    public int normalIndexOf(MyString S, int pos) {
        int i = pos;    // 主串当前位置下标
        int j = 0;  //子串当前位置下标
        int len1 = value.length;
        int len2 = S.value.length;
        while (i < len1 && j < len2) {
            if (value[i] == S.value[j]){
                i++;
                j++;
            }else{
                i = i - j + 1;  //退回上次匹配首位的下一位
                j = 0;  //j退回到子串首位
            }
        }
        if (j >= len2)
            return i - len2;
        else
            return -1;

    }

    /**
     * KMP模式匹配算法
     *
     * 原理：匹配总是从匹配字符串的第一位开始，如果子串中有与前面相等的字符，则可以省略这一部分的不必要判断步骤。
     * @param S
     * @param pos
     * @return
     */
    public int kmpIndexOf(MyString S, int pos) {
        int i = pos;
        int j = 0;
        int len1 = value.length;
        int len2 = S.value.length;
        long time1 = System.nanoTime();
        int next[] = getNext(S);
        long time2 = System.nanoTime();
        System.out.println("-------------" + time2 + " - " + time1 + " = " + (time2 - time1));
        while (i < len1 && j < len2) {
            if ( j == 0 || value[i] == S.value[j]) {    //两字母相等则继续
                ++i;
                ++j;
            }else
                j = next[j-1];
        }
        if (j >= len2)
            return i - len2;
        else
            return -1;
    }

    //如果在 j 处字符不匹配，那么由于前边所说的模式字符串 PMT 的性质，主字符串中 i 指针之前的 PMT[j −1] 位就一定与模式字符串的第 0 位至第 PMT[j−1] 位是相同的。
    private int[] getNext(MyString S) {
        int[] next = new int[S.length()];
        int j = 0;
        next[0] = 0;
        System.out.print("0 ");
        for (int i = 1; i < S.length(); i++) {
            while (j > 0 && S.value[i] != S.value[j] ) {    //  取判断字符(前缀字符)
                j = next[j - 1];
            }
            if (S.value[i] == S.value[j] )
                ++j;
            next[i] = j;
            System.out.print(next[i] + " ");
        }
        return next;
        /*
            next[j]数组存储的是长度为j+1已匹配字符串中的最大重复长度
         */
    }

    public static void main(String[] args) {
        MyString s = new MyString(new char[]{'a','b','a','b','a','a','a','b','a','b','a','a','a','b','a',});
        MyString t = new MyString(new char[]{'a','b','a','b','a','a','a','b','a'});
        long time1 = System.nanoTime();
        System.out.println(s.normalIndexOf(t,2));
        long time2 = System.nanoTime();
        System.out.println(s.kmpIndexOf(t, 2));
        long time3 = System.nanoTime();


        System.out.println(time2 + " - " + time1 + " = " + (time2 - time1));
        System.out.println(time3 + " - " + time2 + " = " + (time3 - time2));
    }

}
