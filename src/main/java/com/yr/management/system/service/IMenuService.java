package com.yr.management.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yr.management.system.entity.Menu;

import java.util.List;
import java.util.Map;

/**
 * @author dengbp
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findUserPermissions(String username);

    List<Menu> findUserMenus(String username);

    Map<String, Object> findMenus(Menu menu);

    List<Menu> findMenuList(Menu menu);

    void createMenu(Menu menu);

    void updateMenu(Menu menu) throws Exception;

    /**
     * 递归删除菜单/按钮
     *
     * @param menuIds menuIds
     */
    void deleteMeuns(String[] menuIds) throws Exception;
}
