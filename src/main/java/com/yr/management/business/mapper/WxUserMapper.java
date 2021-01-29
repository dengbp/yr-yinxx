package com.yr.management.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yr.management.business.entity.WxUser;

import java.util.List;

/**
 * @author baiyang
 * @version 1.0
 * @date 2020/11/29 2:30 下午
 */
public interface WxUserMapper extends BaseMapper<WxUser> {
   WxUser queryByOpenId(String openId);
}
