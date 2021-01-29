package com.yr.management.common.enums;

/**
 * @author dengbp
 * @ClassName SysParamType
 * @Description TODO
 * @date 2019-12-14 11:06
 */
public enum SysParamType {

    PROJECTCODETYPE(1000,"项目编码"),
    PLANSLEVEL(1001,"项目套餐类型"),
    LICENSEVERSION(1002,"license版本"),
    PLANSFEATURE(1003,"套餐特性"),
    PROCEDURE(1004,"系统环境");



    SysParamType(Integer code, String des) {
        this.code = code;
        this.des = des;
    }

    private final Integer code;
    private final String des;

    public static SysParamType getByCode(Integer code){
        if(code == null){
            return null;
        }
        for(SysParamType e : SysParamType.values()){
            if(e.getCode().equals(code)){
                return e;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }
}
