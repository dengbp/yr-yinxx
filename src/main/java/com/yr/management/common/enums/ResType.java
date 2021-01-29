package com.yr.management.common.enums;

/**
 * @author dengbp
 * @ClassName SysParamType
 * @Description 菜单类型：1:菜单 2:功能权限 0目录
 * @date 2019-12-14 11:06
 */
public enum ResType {

    MENU(1,"菜单"),
    BUTTON(2,"功能按钮"),
    CATALOG(0,"目录");



    ResType(Integer code, String des) {
        this.code = code;
        this.des = des;
    }

    private final Integer code;
    private final String des;

    public static ResType getByCode(Integer code){
        if(code == null){
            return null;
        }
        for(ResType e : ResType.values()){
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
