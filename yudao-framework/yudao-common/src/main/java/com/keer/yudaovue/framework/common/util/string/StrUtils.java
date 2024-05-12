package com.keer.yudaovue.framework.common.util.string;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Collection;
import java.util.List;

/**
 * 字符串工具类
 *
 * @author keer
 * @date 2024-05-12
 */
public class StrUtils {
  /**
   * 给定字符串是否以任何一个字符串开始
   *
   * <p>给定字符串和数组为空都返回 false
   *
   * @param str
   * @param prefixes
   * @return
   */
  public static boolean startWithAny(String str, Collection<String> prefixes) {
    if (StrUtil.isEmpty(str) || ArrayUtil.isEmpty(prefixes)) {
      return false;
    }

    for (CharSequence suffix : prefixes) {
      if (StrUtil.startWith(str, suffix, false)) {
        return true;
      }
    }
    return false;
  }
}
