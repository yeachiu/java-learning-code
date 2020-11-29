package cn.chiu.haveatry.javaCore.innerClass;

/**
 * Created by yeachiu on 2019/6/27.
 *
 * 内部类向上转型
 */
public class InnerClassUpcasting {

    private class Contents implements IContents {
        private int i = 11;
        public int value() {
            return i;
        }
    }
    protected class Destination implements IDestination {
        private String label;
        public Destination(String whereTo) {
            label = whereTo;
        }
        public String readLabel() {
            return label;
        }
    }

    public IDestination destination(String s) {
        return new Destination(s);
    }
    public IContents contents() {
        return new Contents();
    }

    public static void main(String[] args) {
        InnerClassUpcasting up = new InnerClassUpcasting();

        //不合法，Contents是private,Destination是protectd的，外部代码访问这些成员将受到限制
        //InnerClassUpcasting.Contents contents = up.new Contents();
        //InnerClassUpcasting.Destination destination = up.new Destination("Tahiti");

        // 使内部类(某个接口的实现)完全不可见并且不可用，仅得到指向基类或接口的引用，这样能够很方便地隐藏具体实现细节。
        IContents icontents = up.contents();
        IDestination idestination = up.destination("Wakanda");

        //ERROR:无法完成向下转型，说明该内部类被隐藏了
        //Contents ct = (Contents)icontents;

    }

}
