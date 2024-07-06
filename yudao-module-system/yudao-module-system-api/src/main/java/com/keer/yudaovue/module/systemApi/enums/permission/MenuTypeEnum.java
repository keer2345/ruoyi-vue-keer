package com.keer.yudaovue.module.systemApi.enums.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型枚举类
 *
 * @author keer
 * @date 2024-07-06
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum {
  DIR(1), // 目录
  MENU(2), // 菜单
  BUTTON(3) // 按钮
;

  /** 类型 */
  private final Integer type;
}
