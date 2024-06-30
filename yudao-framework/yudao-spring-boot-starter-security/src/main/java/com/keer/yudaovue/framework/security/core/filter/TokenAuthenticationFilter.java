package com.keer.yudaovue.framework.security.core.filter;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.keer.yudaovue.framework.common.exception.ServiceException;
import com.keer.yudaovue.framework.common.pojo.CommonResult;
import com.keer.yudaovue.framework.common.util.servlet.ServletUtils;
import com.keer.yudaovue.framework.security.config.SecurityProperties;
import com.keer.yudaovue.framework.security.core.LoginUser;
import com.keer.yudaovue.framework.security.core.util.SecurityFrameworkUtils;
import com.keer.yudaovue.framework.web.core.handler.GlobalExceptionHandler;
import com.keer.yudaovue.framework.web.core.util.WebFrameworkUtils;
import com.keer.yudaovue.module.systemApi.api.OAuth2TokenApi;
import com.keer.yudaovue.module.systemApi.api.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Token 过滤器，验证 token 的有效性 验证通过后
 *
 * <p>获得 {@link LoginUser} 信息，并加入到 Spring Security 上下文
 *
 * @author keer
 * @date 2024-06-05
 */
@Slf4j(topic = ">>> TokenAuthenticationFilter")
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
  private final SecurityProperties securityProperties;
  private final GlobalExceptionHandler globalExceptionHandler;
  private final OAuth2TokenApi oAuth2TokenApi;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    log.info("doFilterInternal");

    String token =
        SecurityFrameworkUtils.obtainAuthorization(
            request, securityProperties.getTokenHeader(), securityProperties.getTokenParameter());

    if (StrUtil.isNotEmpty(token)) {
      Integer userType = WebFrameworkUtils.getLoginUserType(request);
      try {
        // 1.1 基于 token 构建登录用户
        LoginUser loginUser = buildLoginUserByToken(token, userType);
        // 1.2 模拟 Login 功能，方便日常开发调试
        // todo
      } catch (Throwable ex) {
        CommonResult<?> result = globalExceptionHandler.allExceptionHandler(request, ex);
        ServletUtils.writeJSON(response, result);
        return;
      }
    }

    // 继续过滤链
    chain.doFilter(request, response);
  }

  private LoginUser buildLoginUserByToken(String token, Integer userType) {
    try {
      OAuth2AccessTokenCheckRespDTO accessToken = oAuth2TokenApi.checkAccessToken(token);
      if (ObjUtil.isNull(accessToken)) {
        return null;
      }
      // 用户类型不匹配，无权限
      // 注意：只有 /admin-api/* 和 /app-api/* 有 userType，才需要比对用户类型
      // 类似 WebSocket 的 /ws/* 连接地址，是不需要比对用户类型的
      if (ObjUtil.isNotNull(userType) && ObjUtil.notEqual(accessToken.getUserType(), userType)) {
        throw new AccessDeniedException("错误的用户类型");
      }
      // 构建登录用户
      return new LoginUser()
          .setId(accessToken.getUserId())
          .setUserType(accessToken.getUserType())
          .setTenantId(accessToken.getTenantId())
          .setScopes(accessToken.getScopes());
    } catch (ServiceException serviceException) {
      // 校验 Token 不通过时，考虑到一些接口是无需登录的，所以直接返回 null 即可
      return null;
    }
  }
}
