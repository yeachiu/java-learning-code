package cn.chiu.haveatry.javaCore.recursive;

/**
 * 递归和尾递归
 */
public class Recursive {

    /** 递归函数
     * @to-do 计算n的阶乘：n! = n * (n - 1)!
     * @param n
     * @return n!
     */
    int factorial(int n) {
        if(n == 1){     //递归边界
            return 1;   //递归返回段
        } else {
            return n * factorial(n-1);  //递归前进段
        }
    }


    /** 尾递归
     * @to-do 计算n的阶乘：n! = n * (n - 1)!
     * @param n
     * @param result
     * @return n!
     */
    int factorial(int n, int result) {
        if(n == 1){
            return result;
        } else {
            return factorial(n-1, n*result);
        }
    }
}
