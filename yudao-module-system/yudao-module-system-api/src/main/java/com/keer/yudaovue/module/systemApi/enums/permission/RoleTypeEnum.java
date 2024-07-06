package com.keer.yudaovue.module.systemApi.enums.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author keer
 * @date 2024-07-06
 */
@Getter
@AllArgsConstructor
public enum RoleTypeEnum {

  /** 内置角色 */
  SYSTEM(1),
  /** 自定义角色 */
  CUSTOM(2);

  private final Integer type;
}
