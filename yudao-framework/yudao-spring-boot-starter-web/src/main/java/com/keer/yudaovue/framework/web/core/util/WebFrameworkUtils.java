package com.keer.yudaovue.framework.web.core.util;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.http.server.HttpServerRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 专属于 web 包的工具类
 *
 * @author keer
 * @date 2024-04-20
 */
public class WebFrameworkUtils {

  private static final String REQUEST_ATTRIBUTE_LOGIN_USER_ID = "login_user_id";

  public static Long getLoginUserId() {
    HttpServletRequest request = getRequest();
    return getLoginUserId(request);
  }

  public static Long getLoginUserId(HttpServletRequest request) {
    if (ObjUtil.isNull(request)) {
      return null;
    }
    return (Long) request.getAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_ID);
  }

  private static HttpServletRequest getRequest() {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    if (!(requestAttributes instanceof ServletRequestAttributes)) {
      return null;
    }
    ServletRequestAttributes servletRequestAttributes =
        (ServletRequestAttributes) requestAttributes;
    return servletRequestAttributes.getRequest();
  }
}
