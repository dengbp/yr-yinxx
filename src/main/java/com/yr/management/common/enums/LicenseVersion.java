package com.yr.management.common.enums;

/**
 * @author dengbp
 * @ClassName LicenseVersion
 * @Description 证书版本对应不同的软件版本支持，如：现在是控制菜单权限和项目数，后面可能还控制用户量等
 * @date 2019-11-21 11:17
 */
public enum LicenseVersion {

    SNAPSHOT("0.1"),
    VERSION2("1.0");

    private String code;

    LicenseVersion(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }}
