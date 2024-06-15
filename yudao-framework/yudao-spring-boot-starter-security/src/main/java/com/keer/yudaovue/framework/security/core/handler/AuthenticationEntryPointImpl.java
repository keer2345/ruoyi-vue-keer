package com.keer.yudaovue.framework.security.core.handler;

import com.keer.yudaovue.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.keer.yudaovue.framework.common.pojo.CommonResult;
import com.keer.yudaovue.framework.common.util.servlet.ServletUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import java.io.IOException;

import static com.keer.yudaovue.framework.common.exception.enums.GlobalErrorCodeConstants.UNAUTHORIZED;

/**
 * 访问一个需要认证的 URL 资源，但是此时自己尚未认证（登录）的情况下，返回 {@link GlobalErrorCodeConstants#UNAUTHORIZED}
 * 错误码，从而使前端重定向到登录页
 *
 * <p>补充：Spring Security 通过 {@link
 * ExceptionTranslationFilter#sendStartAuthentication(HttpServletRequest, HttpServletResponse,
 * FilterChain, AuthenticationException)} 方法，调用当前类
 *
 * @author keer
 * @date 2024-05-17
 */
@Slf4j(topic = ">>> AuthenticationEntryPointImpl")
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
  // todo
  @Override
  public void commence(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
      throws IOException, ServletException {

    log.info("[commence][访问 URL({}) 时，没有登录]", request.getRequestURI(), e);
    // 返回 401
    ServletUtils.writeJSON(response, CommonResult.error(UNAUTHORIZED));
  }
}
