package com.yr.management.business.login.service;

import com.yr.management.business.login.entity.UserRelation;

/**
 * @author baiyang
 * @version 1.0
 * @date 2020/11/29 2:37 下午
 */
public interface UserRelationService {
    void insert(UserRelation userRelation);

    UserRelation selectByWxOpenId(String id,Integer type);
}
