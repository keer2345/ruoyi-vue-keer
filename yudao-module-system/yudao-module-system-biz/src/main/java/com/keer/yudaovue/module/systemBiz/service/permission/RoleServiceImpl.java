package com.keer.yudaovue.module.systemBiz.service.permission;

import cn.hutool.core.collection.CollectionUtil;
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
  public List<RoleDO> getRoleList(Collection<Long> ids) {
    if (CollectionUtil.isEmpty(ids)) {
      return Collections.emptyList();
    }
    return roleMapper.selectBatchIds(ids);
  }
}
