package com.yr.management.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.management.common.entity.QueryRequestPage;
import com.yr.management.common.utils.SortUtil;
import com.yr.management.system.entity.Role;
import com.yr.management.system.entity.RoleMenu;
import com.yr.management.system.manager.UserManager;
import com.yr.management.system.mapper.RoleMapper;
import com.yr.management.system.mapper.RoleMenuMapper;
import com.yr.management.system.service.IRoleMenuService;
import com.yr.management.system.service.IRoleService;
import com.yr.management.system.service.IUserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author dengbp
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleMenuService roleMenuService;
    @Autowired
    private UserManager userManager;

    @Override
    public IPage<Role> findRoles(Role role, QueryRequestPage request) {
        try {
            LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();

            if (StringUtils.isNotBlank(role.getRoleName())) {
                queryWrapper.eq(Role::getRoleName, role.getRoleName());
            }
            if (StringUtils.isNotBlank(role.getCreateTimeFrom()) && StringUtils.isNotBlank(role.getCreateTimeTo())) {
                queryWrapper
                        .ge(Role::getCreateTime, role.getCreateTimeFrom())
                        .le(Role::getCreateTime, role.getCreateTimeTo());
            }
            Page<Role> page = new Page<>();
            SortUtil.handlePageSort(request, page, true);
            return this.page(page,queryWrapper);
        } catch (Exception e) {
            log.error("获取角色信息失败", e);
            return null;
        }
    }

    @Override
    public List<Role> findUserRole(String userName) {
        return baseMapper.findUserRole(userName);
    }

    @Override
    public Role findByName(String roleName) {
        return baseMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getRoleName, roleName));
    }

    @Override
    public void createRole(Role role) {
        role.setCreateTime(new Date());
        this.save(role);

        String[] menuIds = role.getMenuId().split(StringPool.COMMA);
        setRoleMenus(role, menuIds);
    }

    @Override
    public void deleteRoles(String[] roleIds) throws Exception {
        // 查找这些角色关联了那些用户
        List<String> userIds = this.userRoleService.findUserIdsByRoleId(roleIds);

        List<String> list = Arrays.asList(roleIds);

        baseMapper.deleteBatchIds(list);

        this.roleMenuService.deleteRoleMenusByRoleId(roleIds);
        this.userRoleService.deleteUserRolesByRoleId(roleIds);

        // 重新将这些用户的角色和权限缓存到 Redis中
        this.userManager.loadUserPermissionRoleRedisCache(userIds);

    }

    @Override
    public void updateRole(Role role) throws Exception {
        // 查找这些角色关联了那些用户
        String[] roleId = {String.valueOf(role.getRoleId())};
        List<String> userIds = this.userRoleService.findUserIdsByRoleId(roleId);

        role.setModifyTime(new Date());
        baseMapper.updateById(role);

        roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, role.getRoleId()));

        String[] menuIds = role.getMenuId().split(StringPool.COMMA);
        setRoleMenus(role, menuIds);

        // 重新将这些用户的角色和权限缓存到 Redis中
        this.userManager.loadUserPermissionRoleRedisCache(userIds);
    }

    private void setRoleMenus(Role role, String[] menuIds) {
        Arrays.stream(menuIds).forEach(menuId -> {
            RoleMenu rm = new RoleMenu();
            rm.setMenuId(Long.valueOf(menuId));
            rm.setRoleId(role.getRoleId());
            this.roleMenuMapper.insert(rm);
        });
    }
}
