package com.keer.yudaovue.framework.web.core.filter;

import cn.hutool.core.util.StrUtil;
import com.keer.yudaovue.framework.web.config.WebProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 过滤 /admin-api、/app-api 等 API 请求的过滤器
 *
 * @author keer
 * @date 2024-04-24
 */
@RequiredArgsConstructor
public abstract class ApiRequestFilter extends OncePerRequestFilter {

  protected final WebProperties webProperties;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    // 只过滤 API 请求的地址
    return !StrUtil.startWithAny(
        request.getRequestURI(),
        webProperties.getAdminApi().getPrefix(),
        webProperties.getAppApi().getPrefix());
  }
}
