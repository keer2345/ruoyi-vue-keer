package com.keer.yudaovue.module.systemBiz.service.tenant;

import com.keer.yudaovue.module.systemBiz.dal.dataobject.tenant.TenantDO;
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
  @Override
  public TenantDO getTenantByWebsite(String website) {

     return TenantDO.builder().id(123L).name("yudao-keer2").build();
  }
}
