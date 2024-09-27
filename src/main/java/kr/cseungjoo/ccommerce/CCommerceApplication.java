package kr.cseungjoo.ccommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class CCommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CCommerceApplication.class, args);
    }

}
