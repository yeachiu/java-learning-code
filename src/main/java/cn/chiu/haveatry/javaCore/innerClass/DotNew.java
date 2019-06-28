package cn.chiu.haveatry.javaCore.innerClass;

/**
 * Created by yeachiu on 2019/6/27.
 *
 * 使用 .new 生成内部类对象
 */
public class DotNew {
    class Contents {
        private int i = 11;
        public int value() {
            return i;
        }
    }
    class Destination {
        private String label;
        public Destination(String whereTo) {
            label = whereTo;
        }
        public String readLabel() {
            return label;
        }
    }

    public static void main(String[] args) {
        DotNew dn = new DotNew();
        DotNew.Contents contents = dn.new Contents();
        DotNew.Destination destination = dn.new Destination("Tahiti");
    }
}
