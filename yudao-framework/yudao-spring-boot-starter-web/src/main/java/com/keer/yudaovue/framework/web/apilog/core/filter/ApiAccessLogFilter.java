package com.keer.yudaovue.framework.web.apilog.core.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.keer.yudaovue.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.keer.yudaovue.framework.common.pojo.CommonResult;
import com.keer.yudaovue.framework.common.util.json.JsonUtils;
import com.keer.yudaovue.framework.common.util.monitor.TracerUtils;
import com.keer.yudaovue.framework.common.util.servlet.ServletUtils;
import com.keer.yudaovue.framework.web.apilog.core.annotations.ApiAccessLog;
import com.keer.yudaovue.framework.web.apilog.core.enums.OperateTypeEnum;
import com.keer.yudaovue.framework.web.apilog.core.service.ApiAccessLogFrameworkService;
import com.keer.yudaovue.framework.web.config.WebProperties;
import com.keer.yudaovue.framework.web.core.filter.ApiRequestFilter;
import com.keer.yudaovue.framework.web.core.util.WebFrameworkUtils;
import com.keer.yudaovue.module.infraApi.api.logger.dto.ApiAccessLogCreateReqDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.Map;

import static com.keer.yudaovue.framework.common.util.json.JsonUtils.toJsonString;
import static com.keer.yudaovue.framework.web.apilog.core.ApiAccessLogInterceptor.ATTRIBUTE_HANDLER_METHOD;

/**
 * API 访问日志 Filter
 *
 * <p>目的：记录 API 访问日志到数据库中
 *
 * @author keer
 * @date 2024-04-24
 */
@Slf4j(topic = ">>> ApiAccessLogFilter")
public class ApiAccessLogFilter extends ApiRequestFilter {

  private static final String[] SANITIZE_KEYS =
      new String[] {"password", "token", "accessToken", "refreshToken"};

  private final String applicationName;

  private final ApiAccessLogFrameworkService apiAccessLogFrameworkService;

  public ApiAccessLogFilter(
      WebProperties webProperties,
      String applicationName,
      ApiAccessLogFrameworkService apiAccessLogFrameworkService) {
    super(webProperties);
    this.applicationName = applicationName;
    this.apiAccessLogFrameworkService = apiAccessLogFrameworkService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // 获得开始时间
    LocalDateTime beginTime = LocalDateTime.now();
    // 提前获得参数，避免 XssFilter 过滤处理
    Map<String, String> queryString = ServletUtils.getParamMap(request);
    String requestBody = ServletUtils.isJsonRequest(request) ? ServletUtils.getBody(request) : null;

    try {
      // 继续过滤器
      filterChain.doFilter(request, response);
      // 正常执行，记录日志
      // createApiAccessLog(request, beginTime, queryString, requestBody, null);
    } catch (Exception ex) {
      // 异常执行，记录日志
      // createApiAccessLog(request, beginTime, queryString, requestBody, ex);
      throw ex;
    }
  }

  private void createApiAccessLog(
      HttpServletRequest request,
      LocalDateTime beginTime,
      Map<String, String> queryString,
      String requestBody,
      Exception ex) {
    ApiAccessLogCreateReqDTO accessLog = new ApiAccessLogCreateReqDTO();
    try {
      boolean enable =
          buildApiAccessLog(accessLog, request, beginTime, queryString, requestBody, ex);
      if (!enable) {
        return;
      }
      apiAccessLogFrameworkService.createApiAccessLog(accessLog);
    } catch (Throwable th) {
      log.error(
          "[createApiAccessLog][url({}) log({}) 发生异常]",
          request.getRequestURI(),
          toJsonString(accessLog),
          th);
    }
  }

  private boolean buildApiAccessLog(
      ApiAccessLogCreateReqDTO accessLog,
      HttpServletRequest request,
      LocalDateTime beginTime,
      Map<String, String> queryString,
      String requestBody,
      Exception ex) {

    // 判断：是否要记录操作日志
    HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute(ATTRIBUTE_HANDLER_METHOD);
    ApiAccessLog accessLogAnnotation = null;
    if (handlerMethod != null) {
      accessLogAnnotation = handlerMethod.getMethodAnnotation(ApiAccessLog.class);
      if (accessLogAnnotation != null && BooleanUtil.isFalse(accessLogAnnotation.enable())) {
        return false;
      }
    }

    // todo

    return true;
  }
}
