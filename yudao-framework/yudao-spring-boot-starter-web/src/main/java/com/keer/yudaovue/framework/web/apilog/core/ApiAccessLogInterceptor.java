package com.keer.yudaovue.framework.web.apilog.core;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.keer.yudaovue.framework.common.util.servlet.ServletUtils;
import com.keer.yudaovue.framework.common.util.spring.SpringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * API 访问日志 Interceptor
 *
 * <p>目的：在非 prod 环境时，打印 request 和 response 两条日志到日志文件（控制台）中。
 *
 * <p>https://github.com/YunaiV/ruoyi-vue-pro/issues/474
 *
 * @author keer
 * @date 2024-04-24
 */
@Slf4j(topic = ">>> ApiAccessLogInterceptor")
public class ApiAccessLogInterceptor implements HandlerInterceptor {

  public static final String ATTRIBUTE_HANDLER_METHOD = "HANDLER_METHOD";

  private static final String ATTRIBUTE_STOP_WATCH = "ApiAccessLogInterceptor.StopWatch";

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {
    // 记录 HandlerMethod，提供给 ApiAccessLogFilter 使用
    HandlerMethod handlerMethod = handler instanceof HandlerMethod ? (HandlerMethod) handler : null;
    if (handlerMethod != null) {
      request.setAttribute(ATTRIBUTE_HANDLER_METHOD, handlerMethod);
    }

    // 打印 request 日志
    if (!SpringUtils.isProd() ) {
      // if (!SpringUtils.isProd() && !request.getRequestURI().startsWith("/error")) {
      Map<String, String> queryString = ServletUtils.getParamMap(request);
      String requestBody =
          ServletUtils.isJsonRequest(request) ? ServletUtils.getBody(request) : null;
      if (CollUtil.isEmpty(queryString) && StrUtil.isEmpty(requestBody)) {
        log.info("[preHandle][开始请求 URL({}) 无参数]", request.getRequestURI());
      } else {
        log.info(
            "[preHandle][开始请求 URL({}) 参数({})]",
            request.getRequestURI(),
            StrUtil.nullToDefault(requestBody, queryString.toString()));
      }
      // 计时
      StopWatch stopWatch = new StopWatch();
      stopWatch.start();
      request.setAttribute(ATTRIBUTE_STOP_WATCH, stopWatch);
    }
    return true;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    // 打印 response 日志
    if (!SpringUtils.isProd()) {
      StopWatch stopWatch = (StopWatch) request.getAttribute(ATTRIBUTE_STOP_WATCH);
      // if (stopWatch.isRunning()) {
        stopWatch.stop();
      // }
      log.info(
          "[afterCompletion][完成请求 URL({}) 耗时({} ms)]",
          request.getRequestURI(),
          stopWatch.getTotalTimeMillis());
    }
  }
}
