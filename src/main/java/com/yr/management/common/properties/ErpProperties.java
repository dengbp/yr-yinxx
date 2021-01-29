package com.yr.management.common.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author dengbp
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"file:${CONF_DIR}/yinxx.properties"})
@ConfigurationProperties(prefix = "erp")
public class ErpProperties {

    private ShiroProperties shiro = new ShiroProperties();
    private boolean autoOpenBrowser = true;
    private String[] autoOpenBrowserEnv = {};
    private SwaggerProperties swagger = new SwaggerProperties();
    private InterfaceProperties interfaceProperties = new InterfaceProperties();
    private MysqlProperties mysqlProperties = new MysqlProperties();
    private boolean openAopLog = true;
    private int maxBatchInsertNum = 1000;
    private String certificate_path;
    private String url_base;
}
