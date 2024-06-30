package com.keer.yudaovue.framework.security.core.util;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.keer.yudaovue.framework.security.core.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Null;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

/**
 * 安全服务工具类
 *
 * @author keer
 * @date 2024-05-17
 */
@Slf4j(topic = ">>> SecurityFrameworkUtils")
public class SecurityFrameworkUtils {
  // todo

  /** HEADER 认证头 value 的前缀 */
  public static final String AUTHORIZATION_BEARER = "Bearer";

  private SecurityFrameworkUtils() {}
  /**
   * 获得当前用户的编号，从上下文中
   *
   * @return
   */
  @Nullable
  public static Long getLoginUserId() {
    LoginUser loginUser = getLoginUser();
    return loginUser == null ? null : loginUser.getId();
  }

  /**
   * 获取当前用户
   *
   * @return
   */
  @Nullable
  public static LoginUser getLoginUser() {
    Authentication authentication = getAuthentication();
    if (ObjUtil.isNull(authentication)) {
      return null;
    }
    return authentication.getPrincipal() instanceof LoginUser
        ? (LoginUser) authentication.getPrincipal()
        : null;
  }

  /**
   * 获得当前认证信息
   *
   * @return
   */
  private static Authentication getAuthentication() {
    SecurityContext context = SecurityContextHolder.getContext();
    if (ObjUtil.isNull(context)) {
      return null;
    }
    return context.getAuthentication();
  }

  /**
   * 从请求中，获得认证 Token
   *
   * @param request
   * @param o
   * @return
   */
  public static String obtainAuthorization(
      HttpServletRequest request, String headerName, String parameterName) {
    log.info("obtainAuthorization");
    // 1. 获得 Token。优先级：Header > Parameter
    String token = request.getHeader(headerName);
    log.info("obtainAuthorization getHeader: {}",token);
    if (StrUtil.isEmpty(token)) {
      token = request.getParameter(parameterName);
      log.info("obtainAuthorization getParameter: {}",token);
    }
    if (!StringUtils.hasText(token)) {
      log.info("obtainAuthorization: empty token");
      return null;
    }
    // 2. 去除 Token 中带的 Bearer
    int index = token.indexOf(AUTHORIZATION_BEARER + " ");
    return index >= 0 ? token.substring(index + 7).trim() : token;
  }
}
