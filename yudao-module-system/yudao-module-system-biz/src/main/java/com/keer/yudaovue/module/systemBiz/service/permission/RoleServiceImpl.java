package com.keer.yudaovue.module.systemBiz.service.permission;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.keer.yudaovue.module.systemApi.enums.permission.RoleCodeEnum;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.permission.RoleDO;
import com.keer.yudaovue.module.systemBiz.dal.mysql.permission.RoleMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 角色 Service 实现类
 *
 * @author keer
 * @date 2024-07-06
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
  @Resource private RoleMapper roleMapper;

  @Override
  // todo
  public RoleDO getRoleFromCache(Long id) {
    return roleMapper.selectById(id);
  }

  @Override
  public List<RoleDO> getRoleList(Collection<Long> ids) {
    if (CollectionUtil.isEmpty(ids)) {
      return Collections.emptyList();
    }
    return roleMapper.selectBatchIds(ids);
  }

  @Override
  public boolean hasAnySuperAdmin(Collection<Long> ids) {
    if (CollUtil.isEmpty(ids)) {
      return false;
    }
    RoleServiceImpl self = getSelf();
    return ids.stream()
        .anyMatch(
            id -> {
              RoleDO role = self.getRoleFromCache(id);
              return role != null && RoleCodeEnum.isSuperAdmin(role.getCode());
            });
  }

  /**
   * 获得自身的代理对象，解决 AOP 生效问题
   *
   * @return 自己
   */
  private RoleServiceImpl getSelf() {
    return SpringUtil.getBean(getClass());
  }
}
