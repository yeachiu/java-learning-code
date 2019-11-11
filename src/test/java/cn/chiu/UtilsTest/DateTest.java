package cn.chiu.UtilsTest;

import io.jsonwebtoken.Clock;
import io.jsonwebtoken.impl.DefaultClock;

import java.util.Date;

/**
 * 日期相关测试
 */
public class DateTest {

    private static Long expiration = Long.valueOf(604800);
    private  static Clock clock = DefaultClock.INSTANCE;

    public static void main(String[] args) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);
    }

    private static Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }
}
