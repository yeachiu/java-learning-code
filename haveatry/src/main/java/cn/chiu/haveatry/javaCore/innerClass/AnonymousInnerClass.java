package cn.chiu.haveatry.javaCore.innerClass;


/**
 * Created by yeachiu on 2019/6/28.
 *
 * 匿名内部类
 */
public class AnonymousInnerClass {

    public IContents contents() {
        // 通过new表达式返回的引用被自动向上转型为对IContents的引用
        return new IContents() {    //创建一个继承自IContents的匿名类的对象
            private int i = 11;
            public int value() {
                return i;
            }
        };  //这种情况下此处需要一个分号(这个分号标记的是表达式的结束，只不过这个表达式正巧包含了匿名内部类)
    }

    //基类构造器需要参数的情况
    public Wrapping wrapping(int x) {
        return new Wrapping(x) {
            public int value() {
                return super.value() + 1;
            }
        };
    }


    // 如果定义一个匿名内部类，并且希望它使用一个在其外部定义的对象，那么编译器会要求其参数引用使final的
    public IDestination destination(final String dest) {
        return new IDestination() {
            // 在匿名类中定义字段时，还能够对其执行初始化操作
            private String label = dest;
            public String readLabel() {
                return label;
            }
        };
    }

    public static void main(String[] args) {
        AnonymousInnerClass ai = new AnonymousInnerClass();
        Wrapping w = ai.wrapping(37);
        System.out.println(w.value());

        IDestination d = ai.destination("Tahiti");
        System.out.println(d.readLabel());
    }
}
    /* 上述匿名内部类语法是下述形式的简化形式 */
//    class Contents implements IContents {
//        private int i = 11;
//        public int value() {
//            return i;
//        }
//    }
//    public IContents contents() {
//        return new Contents();
//    }

class Wrapping {
    private int i;
    public Wrapping(int i) {
        this.i = i;
    }
    public int value() {
        return i;
    }
}
