package com.keer.yudaovue.framework.tenant.core.db;

import com.keer.yudaovue.framework.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 拓展多租户的 BaseDO 基类
 *
 * @author keer
 * @date 2024-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantBaseDO extends BaseDO {
  /** 多租户编号 */
  private Long tenantId;
}
