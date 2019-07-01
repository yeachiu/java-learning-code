package cn.chiu.haveatry;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Hello world!
 *
 */
@Slf4j
@SpringBootApplication
@EnableConfigurationProperties
public class HaveatryApplication {

    public static void main(String[] args) {
        SpringApplication.run(HaveatryApplication.class, args);
        log.info("Hello World !!!");
    }
}
