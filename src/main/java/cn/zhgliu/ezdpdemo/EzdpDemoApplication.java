package cn.zhgliu.ezdpdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "cn.zhgliu.ezdpdemo")
@MapperScan("cn.zhgliu.ezdpdemo.*.mapper")
public class EzdpDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EzdpDemoApplication.class, args);
    }



}
