package cn.chiu.haveatry.mydemo.requestlimit.test;


import cn.chiu.haveatry.HaveatryApplication;
import cn.chiu.haveatry.javaCore.concurrency.demo.ExecuteTaskFrameWork;
import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author jlqiu
 * @since 2019/11/20 20:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HaveatryApplication.class)
//@Transactional
//@Rollback(true)
public class RequestLimitAspectTest {

    @Autowired
    ExecuteTaskFrameWork executeTaskFrameWork;
    @Autowired
    TestService testService;


    @Test
    public void multThreadRun() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            executeTaskFrameWork.execute(new Task(String.valueOf(i)));
//            testService.testRequest();

        }
        Thread.sleep(200000);
    }

}