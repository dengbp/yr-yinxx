package com.yr.management.generator.controller;//package com.segi.license.generator.controller;
//
//import com.segi.license.common.annotation.ControllerEndpoint;
//import com.segi.license.common.controller.BaseController;
//import com.segi.license.common.entity.LicenseResponse;
//import com.segi.license.common.entity.QueryRequest;
//import com.segi.license.common.exception.FebsException;
//import com.segi.license.common.utils.FebsUtil;
//import com.segi.license.common.utils.FileUtil;
//import com.segi.license.generator.entity.Column;
//import com.segi.license.generator.entity.GeneratorConfig;
//import com.segi.license.generator.entity.GeneratorConstant;
//import com.segi.license.generator.helper.GeneratorHelper;
//import com.segi.license.generator.service.IGeneratorConfigService;
//import com.segi.license.generator.service.IGeneratorService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.RegExUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletResponse;
//import javax.validation.constraints.NotBlank;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author dengbp
// */
//@Slf4j
//@RestController
//@RequestMapping("generator")
//public class GeneratorController extends BaseController {
//
//    private static final String SUFFIX = "_code.zip";
//
//    @Autowired
//    private IGeneratorService generatorService;
//    @Autowired
//    private IGeneratorConfigService generatorConfigService;
//    @Autowired
//    private GeneratorHelper generatorHelper;
//
//    @GetMapping("tables/info")
//    @RequiresPermissions("generator:view")
//    public LicenseResponse tablesInfo(String tableName, QueryRequest request) {
//        Map<String, Object> dataTable = getDataTable(generatorService.getTables(tableName, request, GeneratorConstant.DATABASE_TYPE, GeneratorConstant.DATABASE_NAME));
//        return new LicenseResponse().success().data(dataTable);
//    }
//
//    @GetMapping
//    @RequiresPermissions("generator:generate")
//    @ControllerEndpoint(exceptionMessage = "代码生成失败")
//    public void generate(@NotBlank(message = "{required}") String name, String remark, HttpServletResponse response) throws Exception {
//        GeneratorConfig generatorConfig = generatorConfigService.findGeneratorConfig();
//        if (generatorConfig == null) {
//            throw new FebsException("代码生成配置为空");
//        }
//
//        String className = name;
//        if (GeneratorConfig.TRIM_YES.equals(generatorConfig.getIsTrim())) {
//            className = RegExUtils.replaceFirst(name, generatorConfig.getTrimValue(), StringUtils.EMPTY);
//        }
//
//        generatorConfig.setTableName(name);
//        generatorConfig.setClassName(FebsUtil.underscoreToCamel(className));
//        generatorConfig.setTableComment(remark);
//        // 生成代码到临时目录
//        List<Column> columns = generatorService.getColumns(GeneratorConstant.DATABASE_TYPE, GeneratorConstant.DATABASE_NAME, name);
//        generatorHelper.generateEntityFile(columns, generatorConfig);
//        generatorHelper.generateMapperFile(columns, generatorConfig);
//        generatorHelper.generateMapperXmlFile(columns, generatorConfig);
//        generatorHelper.generateServiceFile(columns, generatorConfig);
//        generatorHelper.generateServiceImplFile(columns, generatorConfig);
//        generatorHelper.generateControllerFile(columns, generatorConfig);
//        // 打包
//        String zipFile = System.currentTimeMillis() + SUFFIX;
//        FileUtil.compress(GeneratorConstant.TEMP_PATH + "src", zipFile);
//        // 下载
//        FileUtil.download(zipFile, name + SUFFIX, true, response);
//        // 删除临时目录
//        FileUtil.delete(GeneratorConstant.TEMP_PATH);
//    }
//}
