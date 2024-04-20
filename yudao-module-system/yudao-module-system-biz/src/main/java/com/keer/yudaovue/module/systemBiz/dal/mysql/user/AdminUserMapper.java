package com.keer.yudaovue.module.systemBiz.dal.mysql.user;

import com.keer.yudaovue.framework.mybatis.core.mapper.BaseMapperX;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.user.AdminUserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author keer
 * @date 2024-04-20
 */
@Mapper
public interface AdminUserMapper extends BaseMapperX<AdminUserDO> {
  default AdminUserDO selectByUsername(String username) {
    return selectOne(AdminUserDO::getUsername, username);
  }
}
