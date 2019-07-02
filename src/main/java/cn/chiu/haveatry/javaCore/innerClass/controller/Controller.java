package cn.chiu.haveatry.javaCore.innerClass.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeachiu on 2019/7/1.
 *
 * 用来管理并触发时间的实际控制框架
 */
public class Controller {
    private List<Event> eventList = new ArrayList<>();
    public void addEvent(Event c) {
        eventList.add(c);
    }
    //执行就绪的Event对象
    public void run() {
        while (eventList.size() > 0) {
            for (Event e : new ArrayList<Event>(eventList)) {
                if (e.ready()){
                    System.out.println(e);
                    e.action();
                    eventList.remove(e);
                }
            }
        }
    }
}
