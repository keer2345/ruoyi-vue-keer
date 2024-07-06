package com.keer.yudaovue.module.systemBiz.service.permission;

import com.keer.yudaovue.framework.common.util.ollection.CollectionUtils;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.permission.UserRoleDO;
import com.keer.yudaovue.module.systemBiz.dal.mysql.permission.UserRoleMapper;
import jakarta.annotation.Resource;

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

  @Override
  public Set<Long> getUserRoleIdListByUserId(Long userId) {
    return convertSet(userRoleMapper.selectListByUserId(userId), UserRoleDO::getRoleId);
  }
}
