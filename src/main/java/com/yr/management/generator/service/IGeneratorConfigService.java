package com.yr.management.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yr.management.generator.entity.GeneratorConfig;

/**
 * @author dengbp
 */
public interface IGeneratorConfigService extends IService<GeneratorConfig> {

    /**
     * 查询
     *
     * @return GeneratorConfig
     */
    GeneratorConfig findGeneratorConfig();

    /**
     * 修改
     *
     * @param generatorConfig generatorConfig
     */
    void updateGeneratorConfig(GeneratorConfig generatorConfig);

}
