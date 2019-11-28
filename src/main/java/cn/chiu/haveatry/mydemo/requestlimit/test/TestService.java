package cn.chiu.haveatry.mydemo.requestlimit.test;


import cn.chiu.haveatry.mydemo.requestlimit.demo_singleconfig.RequestLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jlqiu
 * @since 2019/11/21 9:56
 */
@Service
public class TestService{

    @Autowired
    private RequestLimitService requestLimitService;

    private static int count = 0;


    public void testRequest() {
//        LimitConfig limitConfig = new LimitConfig("test",5,10,5);
        requestLimitService.beforeProcess("test");
        System.out.println("调用了API");
    }

}
