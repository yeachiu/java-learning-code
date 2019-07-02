package cn.chiu.haveatry.javaCore.innerClass.controller.impl;


import cn.chiu.haveatry.javaCore.innerClass.controller.Controller;
import cn.chiu.haveatry.javaCore.innerClass.controller.Event;

import java.util.List;

/**
 * Created by yeachiu on 2019/7/1.
 *
 * 温室的运作控制
 */
public class GreenhouseControls extends Controller {

    // 灯光控制 ------------------------------------->
    private boolean light = false;
    public class LightOn extends Event {

        public LightOn(long delayTime) {
            super(delayTime);
        }

        @Override
        public void action() {
            light = true;
        }

        public String toString() {
            return "Light is on";
        }
    }
    public class LightOff extends Event {

        public LightOff(long delayTime) {
            super(delayTime);
        }

        @Override
        public void action() {
            light = false;
        }

        public String toString() {
            return "Light is off";
        }
    }

    // 水控制 ------------------------------------->
    private boolean water = false;
    public class WaterOn extends Event {

        public WaterOn(long delayTime) {
            super(delayTime);
        }

        @Override
        public void action() {
            water = true;
        }

        public String toString() {
            return "Water is on";
        }
    }
    public class WaterOff extends Event {

        public WaterOff(long delayTime) {
            super(delayTime);
        }

        @Override
        public void action() {
            water = false;
        }

        public String toString() {
            return "Water is off";
        }
    }

    // 恒温器控制 ------------------------------------->
    private String thermostat = "Day";
    public class ThermostatNight extends Event {

        public ThermostatNight(long delayTime) {
            super(delayTime);
        }

        @Override
        public void action() {
            thermostat = "Night";
        }

        public String toString() {
            return "Thermostat on night setting";
        }
    }
    public class ThermostatDay extends Event {

        public ThermostatDay(long delayTime) {
            super(delayTime);
        }

        @Override
        public void action() {
            thermostat = "Day";
        }

        public String toString() {
            return "Thermostat on day setting";
        }
    }

    // 响铃控制 ------------------------------------->
    public class Bell extends Event {

        public Bell(long delayTime) {
            super(delayTime);
        }

        @Override
        public void action() {
            addEvent(new Bell(delayTime));
        }

        public String toString() {
            return "Bing!";
        }
    }

    // 系统重启控制 ------------------------------------->
    public class Restart extends Event {
        private Event[] eventlist;
        public Restart(long delayTime, Event[] eventlist) {
            super(delayTime);
            this.eventlist = eventlist;
            for (Event e : eventlist) {
                addEvent(e);
            }
        }

        @Override
        public void action() {
            for (Event e : eventlist) {
                e.start();
                addEvent(e);
            }
            start();
            addEvent(this);
        }

        public String toString() {
            return "Restarting system";
        }
    }

    // 温度控制 ------------------------------------->
    public static class Terminate extends Event {
        public Terminate(long delayTime) {
            super(delayTime);
        }

        @Override
        public void action() {
            System.exit(0);
        }

        public String toString() {
            return "Terminating";
        }
    }

    ///////////////////////////////////////////////////
    public static void main(String[] args) {
        GreenhouseControls gc = new GreenhouseControls();
        gc.addEvent(gc.new Bell(900));
        Event[] eventList = {
          gc.new ThermostatNight(0),
          gc.new LightOn(200),
          gc.new LightOff(400),
          gc.new WaterOn(600),
          gc.new WaterOff(800),
          gc.new ThermostatDay(1400)
        };
        gc.addEvent(gc.new Restart(2000, eventList));
        if (args.length == 1)
            gc.addEvent(
                new GreenhouseControls.Terminate(
                    new Integer(args[0])));
        gc.run();
    }
}
