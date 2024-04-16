package com.keer.yudaovue.module.systemBiz.dal.mysql.tenant;

import com.keer.yudaovue.framework.mybatis.core.mapper.BaseMapperX;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.tenant.TenantDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 租户 Mapper
 *
 * @author keer
 * @date 2024-04-16
 */
@Mapper
public interface TenantMapper extends BaseMapperX<TenantDO> {
  default TenantDO selectByWebsite(String website) {
    return selectOne(TenantDO::getWebsite, website);
  }
}
