package cn.chiu.haveatry.javaCore;

/**
 * @author by Raza
 * @classname TestEqual
 * @description TODO
 * @date 2020/11/23 0023 17:21
 */
public class TestEqual {
    public static int a = 1;

    public static void main(String[] args) {
        int a = 10;
        a++;
        TestEqual.a++;
        TestEqual test = new TestEqual();
        System.out.println(a + "," + test.a);
        String hello = "hello";
        String str = "he" + new String("llo");
        System.out.println(-12 % -5);
    }
}
