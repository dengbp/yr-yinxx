package com.yr.management.business.login.entity;

import com.yr.management.business.entity.WxUser;
import lombok.Builder;
import lombok.Data;

/**
 * @author baiyang
 * @version 1.0
 * @date 2020/12/1 7:29 下午
 */
@Data
@Builder
public class LoginVo {
    private WxUser wxUser;
    private UserRelation userRelation;
    private String sessionKey;
}
