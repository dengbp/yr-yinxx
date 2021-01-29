package com.yr.management.common.runner;

import com.yr.management.common.properties.ErpProperties;
import com.yr.management.monitor.service.IRedisService;
import com.yr.management.system.entity.User;
import com.yr.management.system.manager.UserManager;
import com.yr.management.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.List;

/**
 * @author dengbp
 */
@Slf4j
@Component
public class LicenseStartedUpRunner implements ApplicationRunner {

    @Autowired
    private ConfigurableApplicationContext context;
    @Autowired
    private ErpProperties licenseProperties;
    @Autowired
    private IRedisService redisService;
    @Autowired
    private IUserService userService;
    @Autowired
    private UserManager userManager;

    @Value("${server.port:8080}")
    private String port;
    @Value("${server.servlet.context-path:}")
    private String contextPath;
    @Value("${spring.profiles.active}")
    private String active;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        boolean testRedis = false;
        try {
            // 测试 Redis连接是否正常
            redisService.exists("erp_test");
            log.info("缓存初始化 ······");
            log.info("缓存用户数据 ······");
            List<User> list = this.userService.list();
            for (User user : list) {
                userManager.loadUserRedisCache(user);
            }
            testRedis = true;
        } catch (Exception e) {
            log.error("erp-manager启动失败，{}", e.getMessage());
            e.printStackTrace();
            log.error("Redis连接异常，请检查Redis连接配置并确保Redis服务已启动");
            context.close();
        }
        if (testRedis) {
            InetAddress address = InetAddress.getLocalHost();
            String url = String.format("http://%s:%s", address.getHostAddress(), port);
            String loginUrl = licenseProperties.getShiro().getLoginUrl();
            if (StringUtils.isNotBlank(contextPath)){
                url += contextPath;
            }
            if (StringUtils.isNotBlank(loginUrl)) {
                url += loginUrl;
            }
            log.info("erp-manager 系统启动完毕，地址：{}", url);
            boolean auto = licenseProperties.isAutoOpenBrowser();
            String[] autoEnv = licenseProperties.getAutoOpenBrowserEnv();
            if (auto && ArrayUtils.contains(autoEnv, active)) {
                String os = System.getProperty("os.name");
                if (StringUtils.containsIgnoreCase(os, "windows")) {
                    Runtime.getRuntime().exec("cmd  /c  start " + url);
                }
            }
        }
    }
}
