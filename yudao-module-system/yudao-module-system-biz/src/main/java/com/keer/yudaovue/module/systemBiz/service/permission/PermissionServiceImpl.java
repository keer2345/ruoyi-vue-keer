package com.keer.yudaovue.module.systemBiz.service.permission;

import cn.hutool.core.collection.CollUtil;
import com.keer.yudaovue.framework.common.util.ollection.CollectionUtils;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.permission.UserRoleDO;
import com.keer.yudaovue.module.systemBiz.dal.mysql.permission.UserRoleMapper;
import jakarta.annotation.Resource;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static com.keer.yudaovue.framework.common.util.ollection.CollectionUtils.convertSet;

/**
 * 权限 Service 实现类
 *
 * @author keer
 * @date 2024-07-05
 */
public class PermissionServiceImpl implements PermissionService {
  @Resource private UserRoleMapper userRoleMapper;

  @Resource private RoleService roleService;

  @Override
  public Set<Long> getUserRoleIdListByUserId(Long userId) {
    return convertSet(userRoleMapper.selectListByUserId(userId), UserRoleDO::getRoleId);
  }

  @Override
  public Set<Long> getRoleMenuListByRoleId(Collection<Long> roleIds) {
    if(CollUtil.isEmpty(roleIds)){
      return Collections.emptySet();
    }

    // 如果是管理员的情况下，获取全部菜单编号
    if(roleService.hasAnySuperAdmin(roleIds)){

    }

    return Set.of();
  }
}
