package com.keer.yudaovue.framework.common.util.servlet;

import cn.hutool.extra.servlet.JakartaServletUtil;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 客户端工具类
 *
 * @author keer
 * @date 2024-04-07
 */
public class ServletUtils {
  public static String getClientIP(HttpServletRequest request) {
    return JakartaServletUtil.getClientIP(request);
  }
}
