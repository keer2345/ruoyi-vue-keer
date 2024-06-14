package com.keer.yudaovue.framework.web.core.util;

import cn.hutool.core.util.ObjUtil;
import com.keer.yudaovue.framework.common.enums.UserTypeEnum;
import com.keer.yudaovue.framework.common.pojo.CommonResult;
import com.keer.yudaovue.framework.web.config.WebProperties;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 专属于 web 包的工具类
 *
 * @author keer
 * @date 2024-04-20
 */
@Slf4j(topic = ">>> WebFrameworkUtils")
public class WebFrameworkUtils {

  private static final String REQUEST_ATTRIBUTE_LOGIN_USER_ID = "login_user_id";

  private static final String REQUEST_ATTRIBUTE_LOGIN_USER_TYPE = "login_user_type";

  private static final String REQUEST_ATTRIBUTE_COMMON_RESULT = "common_result";

  public static final String HEADER_TENANT_ID = "tenant-id";

  /**
   * 终端的 Header
   *
   * @see cn.iocoder.yudao.framework.common.enums.TerminalEnum
   */
  public static final String HEADER_TERMINAL = "terminal";

  private static WebProperties properties;

  public WebFrameworkUtils(WebProperties webProperties) {
    WebFrameworkUtils.properties = webProperties;
  }

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

  /**
   * 获得当前用户的类型
   *
   * <p>注意：该方法仅限于 web 相关的 framework 组件使用！！！
   *
   * @param request 请求
   * @return 用户编号
   */
  public static Integer getLoginUserType(HttpServletRequest request) {
    if (request == null) {
      return null;
    }
    // 1. 优先，从 Attribute 中获取
    Integer userType = (Integer) request.getAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_TYPE);
    if (userType != null) {
      return userType;
    }
    // 2. 其次，基于 URL 前缀的约定
    if (request.getServletPath().startsWith(properties.getAdminApi().getPrefix())) {
      return UserTypeEnum.ADMIN.getValue();
    }
    if (request.getServletPath().startsWith(properties.getAppApi().getPrefix())) {
      return UserTypeEnum.MEMBER.getValue();
    }
    return null;
  }

  public static void setCommonResult(ServletRequest request, CommonResult<?> result) {
    request.setAttribute(REQUEST_ATTRIBUTE_COMMON_RESULT, result);
  }

  public static CommonResult<?> getCommonResult(ServletRequest request) {
    return (CommonResult<?>) request.getAttribute(REQUEST_ATTRIBUTE_COMMON_RESULT);
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
