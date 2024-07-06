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
  /**
   * 获得角色列表
   *
   * @param roleIds
   * @return
   */
  List<RoleDO> getRoleList(Collection<Long> ids);
}
