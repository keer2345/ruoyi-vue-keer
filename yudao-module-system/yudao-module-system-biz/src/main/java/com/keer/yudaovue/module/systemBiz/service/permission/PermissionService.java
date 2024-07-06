package com.keer.yudaovue.module.systemBiz.service.permission;

import java.util.Collection;
import java.util.Set;

/**
 * 权限 Service 接口
 *
 * <p>提供用户-角色、角色-菜单、角色-部门的关联权限处理
 *
 * @author keer
 * @date 2024-07-05
 */
public interface PermissionService {

  /**
   * 获得用户拥有的角色编号集合
   *
   * @param id
   * @return
   */
  Set<Long> getUserRoleIdListByUserId(Long id);

  /**
   * 获得角色们拥有的菜单编号集合
   *
   * @param longs
   * @return
   */
  Set<Long> getRoleMenuListByRoleId(Collection<Long> roleIds);
}
