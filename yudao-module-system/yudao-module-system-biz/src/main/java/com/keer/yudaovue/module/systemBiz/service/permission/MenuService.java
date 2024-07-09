package com.keer.yudaovue.module.systemBiz.service.permission;

import com.keer.yudaovue.module.systemBiz.dal.dataobject.permission.MenuDO;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 菜单 Service 接口
 *
 * @author keer
 * @date 2024-07-06
 */
public interface MenuService {
  /**
   * 获得菜单数组
   *
   * @param ids
   * @return
   */
  List<MenuDO> getMenuList(Collection<Long> ids);

  List<MenuDO> getMenuList();
}
