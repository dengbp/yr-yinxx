package com.yr.management.business.login.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yr.management.business.config.WxConfig;
import com.yr.management.business.login.entity.LoginVo;
import com.yr.management.common.entity.ERPResponse;
import com.yr.management.business.entity.WxUser;
import com.yr.management.business.login.entity.LoginDto;
import com.yr.management.business.login.entity.UserRelation;
import com.yr.management.business.login.service.UserRelationService;
import com.yr.management.business.service.WxUserService;
import com.yr.management.common.utils.HttpClientUtil;
import com.yr.management.common.utils.SeqUtil;
import com.yr.management.common.utils.WeChatGetUserInfoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author baiyang
 * @version 1.0
 * @date 2020/11/29 2:21 下午
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    private final WxUserService wxUserService;
    private final UserRelationService userRelationService;
    @Autowired
    private WxConfig wxConfig;

    public LoginController(WxUserService wxUserService, UserRelationService userRelationService) {
        this.wxUserService = wxUserService;
        this.userRelationService = userRelationService;
    }


    @PostMapping("/wx")
    public ERPResponse login(@RequestBody LoginDto loginDto) {
        if (!StringUtils.isNotBlank(loginDto.getCode())) {
            return new ERPResponse().fail().message("未获取到用户凭证code");
        }
        String apiUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + wxConfig.getAppId() + "&secret=" + wxConfig.getSecret() + "&js_code=" + loginDto.getCode() + "&grant_type=authorization_code";
        String responseBody = HttpClientUtil.doGet(apiUrl);
        JSONObject jsonObject = JSON.parseObject(responseBody);
        System.out.println(jsonObject.toJSONString());
        WxUser wxUser = new WxUser();
        UserRelation userRelation = new UserRelation();
        if (StringUtils.isNotBlank(jsonObject.getString("openid"))
                && StringUtils.isNotBlank(jsonObject.getString("session_key"))) {
            JSONObject userInfoJson= WeChatGetUserInfoUtil.getUserInfo(loginDto.getEncryptedData(),jsonObject.getString("session_key"),loginDto.getIv());
            assert userInfoJson != null;
            wxUser.getWxUser(userInfoJson);
            WxUser wxUserFromData = wxUserService.queryByOpenId(wxUser.getWxOpenId());
            if (wxUserFromData == null) {
                wxUser.setWxUserId(SeqUtil.nextId());
                wxUser.setStatus(1);
                wxUser.setAuthorityId(1);
                wxUser.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
                wxUserService.insert(wxUser);
                userRelation.setUserId(SeqUtil.nextId());
                userRelation.setType(1);
                userRelation.setWxOpenId(wxUser.getWxOpenId());
                userRelationService.insert(userRelation);
            } else {
                wxUser = wxUserService.queryByOpenId(wxUser.getWxOpenId());
                userRelation = userRelationService.selectByWxOpenId(wxUser.getWxOpenId(), 1);
            }
        }
        return new ERPResponse().success().data(LoginVo.builder().wxUser(wxUser).sessionKey(jsonObject.getString("session_key")).userRelation(userRelation).build());
    }

    @PostMapping("/phone")
    public ERPResponse getPhone(@RequestBody LoginDto loginDto){
        JSONObject userInfoJson= WeChatGetUserInfoUtil.getUserInfo(loginDto.getEncryptedData(),loginDto.getSessionKey(),loginDto.getIv());
        assert userInfoJson != null;
        WxUser wxUserFromData = wxUserService.queryByOpenId(loginDto.getWxOpenId());
        System.out.println(userInfoJson.toJSONString());
        wxUserFromData.setPhoneNumber(userInfoJson.getString("purePhoneNumber"));
        wxUserService.updateByWxUserId(wxUserFromData);
        return new ERPResponse().success().data(wxUserFromData);
    }

}
