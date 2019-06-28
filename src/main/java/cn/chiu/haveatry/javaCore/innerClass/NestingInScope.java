package cn.chiu.haveatry.javaCore.innerClass;

/**
 * Created by yeachiu on 2019/6/28.
 *
 * 在任意作用域内嵌入一个内部类
 */
public class NestingInScope {

    private void internalTracking(boolean bool) {
        if (bool) {
            class TrackingSlip {
                private String id;
                TrackingSlip(String s) {
                    this.id = s;
                }
                String getSlip() {
                    return  id;
                }
            }
            TrackingSlip ts = new TrackingSlip("slip");
            String s = ts.getSlip();
        }
        //ERROR:作用域外无法使用该内部类
        //TrackingSlip ts = new TrackingSlip("slip");
    }
    public void track() {
        internalTracking(true);
    }

    public static void main(String[] args) {
        NestingInScope ns = new NestingInScope();
        ns.track();
    }
}
