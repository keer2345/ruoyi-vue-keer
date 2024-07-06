package com.keer.yudaovue.module.systemBiz.dal.mysql.permission;

import com.keer.yudaovue.framework.mybatis.core.mapper.BaseMapperX;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.permission.UserRoleDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author keer
 * @date 2024-07-06
 */
@Mapper
public interface UserRoleMapper extends BaseMapperX<UserRoleDO> {
  default List<UserRoleDO> selectListByUserId(Long userId) {
    return selectList(UserRoleDO::getUserId, userId);
  }
}
