package com.yr.management;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Description 启动类
 * @return
 * @Author dengbp
 * @Date 17:29 2020-12-11
 **/
@EnableAsync
@SpringBootApplication(exclude={DruidDataSourceAutoConfigure.class})
@EnableTransactionManagement
@MapperScan("com.yr.management.**.mapper")
public class ManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementApplication.class, args);
    }

}
