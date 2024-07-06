package com.keer.yudaovue.module.systemBiz.service.permission;

import com.keer.yudaovue.module.systemBiz.dal.dataobject.permission.MenuDO;
import com.keer.yudaovue.module.systemBiz.dal.mysql.permission.MenuMapper;
import jakarta.annotation.Resource;

import java.util.Collection;
import java.util.List;

/**
 * 菜单 Service 实现
 *
 * @author keer
 * @date 2024-07-06
 */
public class MenuServiceImpl implements MenuService {
  @Resource private MenuMapper menuMapper;

  @Override
  public List<MenuDO> getMenuList(Collection<Long> ids) {
    return menuMapper.selectBatchIds(ids);
  }
}
