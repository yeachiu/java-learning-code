package cn.chiu.haveatry.javaCore.innerClass;

/**
 * Created by qWX649512 on 2019/6/28.
 *
 * 嵌套类(静态内部类)
 */
public class NestedClass {

    private static final int deed = 51;
    public int doSomething() {
        final double d = Math.random();
        final int i = (int)(d*deed);
        return i;
    }

    private class ParcelContents implements IContents {
        private int i = 37;
        public int value() {
            return i + doSomething();
        }
        // ERROR:普通内部类的字段和方法不能有static数据和static字段，也不能包含嵌套类。
        //public static void f() {}
    }
    
    // ParcelDestination不需要外围类的任何对象
    protected static class ParcelDestination implements IDestination {
        private String label;
        private ParcelDestination(String s) {
            this.label = s;
        }
        public String readLabel() {
            return label;
        }
        //嵌套类可以包含其他静态元素和静态字段
        public static void f() {}
        static int x = 7;
        static class AnotherLevel {
            public static void f() {}
            static int x = 3;
        }
    }

    public IContents contents() {
        return new ParcelContents();
    }

    public static IDestination destination(String s) {
        return new ParcelDestination(s);
    }

    public static void main(String[] args) {
        NestedClass nc = new NestedClass();
        IContents c = nc.contents();
        IDestination d = destination("Cool");
    }
}
