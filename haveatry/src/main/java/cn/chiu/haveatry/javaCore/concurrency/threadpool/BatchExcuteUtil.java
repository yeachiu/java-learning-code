package cn.chiu.haveatry.javaCore.concurrency.threadpool;


import java.util.ArrayList;

/**
 * @author jlqiu
 * @since 2019/11/11 17:35
 */
public class BatchExcuteUtil {

    static final int DEFAULT_INITIAL_CAPACITY = 500;

    private static Object[] array = new Object[DEFAULT_INITIAL_CAPACITY];

    private int currentSize = 0;

    public synchronized void putAndConsume(Object obj) {
        if(currentSize < array.length) {
            array[currentSize+1] = obj;
        }else if(currentSize == array.length){
            //TODO: consume datas
            flush();
        }

    }

    public void finishAndConsume() {
        //TODO: consume datas
    }
    public synchronized void flush(){
        ArrayList<Object> newlist = new ArrayList<>();
        // array => newlist
        // new thread
        // execute new list
        // array empty
    }


}
