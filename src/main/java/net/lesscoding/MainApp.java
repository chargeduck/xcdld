package net.lesscoding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author eleven
 * @date 2023年9月27日12:12:28
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@MapperScan("net.lesscoding.mapper")
public class MainApp {
    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }
}
