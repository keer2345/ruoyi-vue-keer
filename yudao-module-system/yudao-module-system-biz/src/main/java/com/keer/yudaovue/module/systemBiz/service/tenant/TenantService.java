package com.keer.yudaovue.module.systemBiz.service.tenant;

import com.keer.yudaovue.module.systemBiz.dal.dataobject.tenant.TenantDO;

/**
 * 租户 Service 接口
 *
 * @author keer
 * @date 2024-04-16
 */
public interface TenantService {
  /**
   * 获得域名对应的租户
   *
   * @param website
   * @return
   */
  TenantDO getTenantByWebsite(String website);
}
