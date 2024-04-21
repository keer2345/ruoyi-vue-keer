package com.keer.yudaovue.framework.common.util.servlet;

import cn.hutool.extra.servlet.JakartaServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

/**
 * 客户端工具类
 *
 * @author keer
 * @date 2024-04-07
 */
public class ServletUtils {
  /**
   * 获得请求
   *
   * @return HttpServletRequest
   */
  public static HttpServletRequest getRequest() {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    if (!(requestAttributes instanceof ServletRequestAttributes)) {
      return null;
    }
    return ((ServletRequestAttributes) requestAttributes).getRequest();
  }

  public static String getUserAgent() {
    HttpServletRequest request = getRequest();
    if (request == null) {
      return null;
    }
    return getUserAgent(request);
  }

  /**
   * 请求
   *
   * @param request
   * @return
   */
  public static String getUserAgent(HttpServletRequest request) {
    String ua = request.getHeader("User-Agent");
    return ua != null ? ua : "";
  }

  public static String getClientIP() {
    HttpServletRequest request = getRequest();
    if (request == null) {
      return null;
    }
    return JakartaServletUtil.getClientIP(request);
  }

  public static String getClientIP(HttpServletRequest request) {
    return JakartaServletUtil.getClientIP(request);
  }

  public static Map<String, String> getParamMap(HttpServletRequest request) {
    return JakartaServletUtil.getParamMap(request);
  }

  public static String getBody(HttpServletRequest request) {
    return JakartaServletUtil.getBody(request);
  }
}
