package com.keer.yudaovue.module.systemBiz.dal.mysql.permission;

import com.keer.yudaovue.framework.mybatis.core.mapper.BaseMapperX;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.permission.RoleMenuDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author keer
 * @date 2024-07-06
 */
@Mapper
public interface RoleMenuMapper extends BaseMapperX<RoleMenuDO> {
  default List<RoleMenuDO> selectListByRoleId(Collection<Long> roleIds) {
    return selectList(RoleMenuDO::getRoleId, roleIds);
  }
}
