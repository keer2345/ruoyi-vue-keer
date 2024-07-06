package com.keer.yudaovue.module.systemBiz.service.permission;

import com.keer.yudaovue.module.systemBiz.dal.dataobject.permission.RoleDO;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 角色 Service 接口
 *
 * @author keer
 * @date 2024-07-06
 */
public interface RoleService {

  RoleDO getRoleFromCache(Long id) ;
  /**
   * 获得角色列表
   *
   * @param roleIds
   * @return
   */
  List<RoleDO> getRoleList(Collection<Long> ids);

  /**
   * 判断角色编号数组中，是否有管理员
   *
   * @param ids
   * @return
   */
  boolean hasAnySuperAdmin(Collection<Long> ids);
}
