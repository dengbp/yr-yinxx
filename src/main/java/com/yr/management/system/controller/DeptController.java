package com.yr.management.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import com.yr.management.common.annotation.Log;
import com.yr.management.common.controller.BaseController;
import com.yr.management.common.entity.QueryRequestPage;
import com.yr.management.common.exception.ERPException;
import com.yr.management.system.entity.Dept;
import com.yr.management.system.service.IDeptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("dept")
public class DeptController extends BaseController {

    private String message;

    @Autowired
    private IDeptService deptService;

    @GetMapping
    public Map<String, Object> deptList(QueryRequestPage request, Dept dept) {
        return this.deptService.findDepts(request, dept);
    }

    @Log("新增部门")
    @PostMapping
    @RequiresPermissions("dept:add")
    public void addDept(@Valid Dept dept) throws ERPException {
        try {
            this.deptService.createDept(dept);
        } catch (Exception e) {
            message = "新增部门失败";
            log.error(message, e);
            throw new ERPException(message);
        }
    }

    @Log("删除部门")
    @DeleteMapping("/{deptIds}")
    @RequiresPermissions("dept:delete")
    public void deleteDepts(@NotBlank(message = "{required}") @PathVariable String deptIds) throws ERPException {
        try {
            String[] ids = deptIds.split(StringPool.COMMA);
            this.deptService.deleteDepts(ids);
        } catch (Exception e) {
            message = "删除部门失败";
            log.error(message, e);
            throw new ERPException(message);
        }
    }

    @Log("修改部门")
    @PutMapping
    @RequiresPermissions("dept:update")
    public void updateDept(@Valid Dept dept) throws ERPException {
        try {
            this.deptService.updateDept(dept);
        } catch (Exception e) {
            message = "修改部门失败";
            log.error(message, e);
            throw new ERPException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("dept:export")
    public void export(Dept dept, QueryRequestPage request, HttpServletResponse response) throws ERPException {
        try {
            List<Dept> depts = this.deptService.findDepts(dept, request);
            ExcelKit.$Export(Dept.class, response).downXlsx(depts, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new ERPException(message);
        }
    }
}
