package cn.chiu.haveatry.mydemo.others;

import java.util.HashMap;
import java.util.Map;

public class CircularDependency {
    Map<String, JavaBean> completed = new HashMap<>();
    Map<String, JavaBean> semiFinished = new HashMap<>();
    Map<String, BeanDefine> toCreate = new HashMap<>();

    public void create() {
        init();
        while (!toCreate.isEmpty()) {
            toCreate.keySet().stream().findFirst().ifPresent(beanName -> {
                doCreate(beanName);
                toCreate.remove(beanName);
            });

        }
        System.out.println(completed.keySet());
        System.out.println(semiFinished.keySet());
    }

    private void doCreate(String beanName) {
        JavaBean bean = null;
        BeanDefine beanDefine = toCreate.get(beanName);
        String autowire = beanDefine.autowire;
        if (completed.containsKey(beanName)) {
            return;
        }
        if (semiFinished.containsKey(beanName)) {
            bean = semiFinished.get(beanName);
        } else {
            bean = new JavaBean(beanName, null);
            semiFinished.put(beanName, bean);
        }
        if (autowire == null) {
            canRemoveSemiFinished(semiFinished.get(beanName));
            completed.put(beanName, bean);
            return;
        }
        if (!completed.containsKey(autowire) && !semiFinished.containsKey(autowire)) {
            doCreate(autowire);
        }
        if (completed.containsKey(autowire)) {
            bean.autowire = completed.get(autowire);
        } else if (semiFinished.containsKey(autowire)){
            bean.autowire = semiFinished.get(autowire);
        }
        canRemoveSemiFinished(semiFinished.get(beanName));
        completed.put(beanName, bean);
    }

    private void canRemoveSemiFinished(JavaBean bean) {
        String autowire = bean.name;
        if (completed.containsKey(autowire)) {
            semiFinished.remove(autowire);
        }
    }


    private void init() {
        toCreate.put("A", new BeanDefine("A", "B"));
        toCreate.put("B", new BeanDefine("B", "C"));
        toCreate.put("C", new BeanDefine("C", "A"));
    }
    static class JavaBean {
        String name;
        JavaBean autowire;

        public JavaBean(String name, JavaBean autowire) {
            this.name = name;
            this.autowire = autowire;
        }
    }

    static class BeanDefine {
        String name;
        String autowire;

        public BeanDefine(String name, String autowire) {
            this.name = name;
            this.autowire = autowire;
        }
    }

    public static void main(String[] args) {
        new CircularDependency().create();
    }
}
