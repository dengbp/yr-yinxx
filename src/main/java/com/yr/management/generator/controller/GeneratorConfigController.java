package com.yr.management.generator.controller;

import com.yr.management.common.annotation.ControllerEndpoint;
import com.yr.management.common.controller.BaseController;
import com.yr.management.common.entity.LicenseResponse;
import com.yr.management.common.exception.ERPException;
import com.yr.management.generator.entity.GeneratorConfig;
import com.yr.management.generator.service.IGeneratorConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author dengbp
 */
@Slf4j
@RestController
@RequestMapping("generatorConfig")
public class GeneratorConfigController extends BaseController {

    @Autowired
    private IGeneratorConfigService generatorConfigService;

    @GetMapping
    @RequiresPermissions("generator:configure:view")
    public LicenseResponse getGeneratorConfig() {
        return new LicenseResponse().success().data(generatorConfigService.findGeneratorConfig());
    }

    @PostMapping("update")
    @RequiresPermissions("generator:configure:update")
    @ControllerEndpoint(operation = "修改GeneratorConfig", exceptionMessage = "修改GeneratorConfig失败")
    public LicenseResponse updateGeneratorConfig(@Valid GeneratorConfig generatorConfig) {
        if (StringUtils.isBlank(generatorConfig.getId()))
            throw new ERPException("配置id不能为空");
        this.generatorConfigService.updateGeneratorConfig(generatorConfig);
        return new LicenseResponse().success();
    }
}
