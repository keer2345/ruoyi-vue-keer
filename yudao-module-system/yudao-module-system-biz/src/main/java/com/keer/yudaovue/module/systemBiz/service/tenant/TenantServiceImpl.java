package com.keer.yudaovue.module.systemBiz.service.tenant;

import com.keer.yudaovue.module.systemBiz.dal.dataobject.tenant.TenantDO;
import com.keer.yudaovue.module.systemBiz.dal.mysql.tenant.TenantMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * @author keer
 * @date 2024-04-16
 */
@Slf4j
@Service
@Validated
public class TenantServiceImpl implements TenantService {

  @Resource private TenantMapper tenantMapper;

  @Override
  public TenantDO getTenantByWebsite(String website) {
    return tenantMapper.selectByWebsite(website);
  }

  @Override
  public TenantDO getTenantByName(String name) {
    return tenantMapper.selectByName(name);
  }
}
