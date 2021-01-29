package com.yr.management.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.management.business.entity.WxUser;
import com.yr.management.business.mapper.WxUserMapper;
import com.yr.management.business.service.WxUserService;
import org.springframework.stereotype.Service;

/**
 * @author baiyang
 * @version 1.0
 * @date 2020/11/29 2:29 下午
 */
@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper,WxUser> implements WxUserService {



    @Override
    public WxUser queryByOpenId(String openId) {
        return this.baseMapper.queryByOpenId(openId);
    }

    @Override
    public void insert(WxUser wxUser) {
        this.baseMapper.insert(wxUser);
    }

    @Override
    public void updateByWxUserId(WxUser wxUserFromData) {
        this.baseMapper.updateById(wxUserFromData);
    }
}
