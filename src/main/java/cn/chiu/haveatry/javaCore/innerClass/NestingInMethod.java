package cn.chiu.haveatry.javaCore.innerClass;

/**
 * Created by yeachiu on 2019/6/28.
 *
 * 在方法内部定义内部类
 */
public class NestingInMethod {
    public IDestination destination(String s) {
        class Destination implements IDestination {
            private String label;
            public Destination(String whereTo) {
                label = whereTo;
            }
            public String readLabel() {
                return label;
            }
        }
        return new Destination(s);
    }

    public IContents contents() {
        class Contents implements IContents {
            private int i = 11;
            public int value() {
                return i;
            }
        }
        return new Contents();
    }

    public static void main(String[] args) {
        NestingInMethod ic = new NestingInMethod();
        IContents icontents = ic.contents();
        System.out.println(icontents.value());
        IDestination idestination = ic.destination("Wakanda");
        System.out.println(idestination.readLabel());
    }

}
