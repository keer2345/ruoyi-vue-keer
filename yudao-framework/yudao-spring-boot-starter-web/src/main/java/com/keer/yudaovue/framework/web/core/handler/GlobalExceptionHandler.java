package com.keer.yudaovue.framework.web.core.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.map.MapUtil;
import com.keer.yudaovue.framework.common.exception.ServiceException;
import com.keer.yudaovue.framework.common.pojo.CommonResult;
import com.keer.yudaovue.framework.common.util.json.JsonUtils;
import com.keer.yudaovue.framework.common.util.monitor.TracerUtils;
import com.keer.yudaovue.framework.common.util.servlet.ServletUtils;
import com.keer.yudaovue.framework.web.apilog.core.service.ApiErrorLogFrameworkService;
import com.keer.yudaovue.framework.web.core.util.WebFrameworkUtils;
import com.keer.yudaovue.module.infraApi.api.logger.dto.ApiErrorLogCreateReqDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

import static com.keer.yudaovue.framework.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;

/**
 * 全局异常处理器，将 Exception 翻译成 CommonResult + 对应的异常编号
 *
 * @author keer
 * @date 2024-04-20
 */
@RestControllerAdvice
@AllArgsConstructor
@Slf4j(topic = ">>> GlobalExceptionHandler")
public class GlobalExceptionHandler {

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  private final String applicationName;

  // private final ApiErrorLogFrameworkService apiErrorLogFrameworkService;

  /**
   * 处理所有异常，主要是提供给 Filter 使用
   *
   * <p>因为 Filter 不走 SpringMVC 的流程，但是我们又需要兜底处理异常，
   *
   * <p>所以这里提供一个全量的异常处理过程，保持逻辑统一。
   *
   * @param request
   * @param ex
   * @return
   */
  public CommonResult<?> allExceptionHandler(HttpServletRequest request, Throwable ex) {
    if (ex instanceof ServiceException) {
      return serviceExceptionHandler((ServiceException) ex);
    }
    return defaultExceptionHandler(request, ex);
  }

  /**
   * 处理业务异常 ServiceException
   *
   * <p>例如说，商品库存不足，用户手机号已存在。
   *
   * @param ex
   * @return
   */
  @ExceptionHandler(value = ServiceException.class)
  public CommonResult<?> serviceExceptionHandler(ServiceException ex) {
    log.info("[serviceExceptionHandler][ex({})]", ex.getClass().getName(), ex);
    return CommonResult.error(ex.getCode(), ex.getMessage());
  }

  /** 处理系统异常，兜底处理所有的一切 */
  @ExceptionHandler(value = Exception.class)
  public CommonResult<?> defaultExceptionHandler(HttpServletRequest req, Throwable ex) {
    log.info("[defaultExceptionHandler][ex({})]", ex.getClass().getName(), ex);
    // todo
    // 插入异常日志
    this.createExceptionLog(req, ex);
    // 返回 ERROR CommonResult
    return CommonResult.error(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getMsg());
  }
  private void createExceptionLog(HttpServletRequest req, Throwable e) {
    // 插入错误日志
    ApiErrorLogCreateReqDTO errorLog = new ApiErrorLogCreateReqDTO();
    try {
      // 初始化 errorLog
      buildExceptionLog(errorLog, req, e);
      // 执行插入 errorLog
      // apiErrorLogFrameworkService.createApiErrorLog(errorLog);
    } catch (Throwable th) {
      log.error("[createExceptionLog][url({}) log({}) 发生异常]", req.getRequestURI(),  JsonUtils.toJsonString(errorLog), th);
    }
  }

  private void buildExceptionLog(ApiErrorLogCreateReqDTO errorLog, HttpServletRequest request, Throwable e) {
    // 处理用户信息
    errorLog.setUserId(WebFrameworkUtils.getLoginUserId(request));
    errorLog.setUserType(WebFrameworkUtils.getLoginUserType(request));
    // 设置异常字段
    errorLog.setExceptionName(e.getClass().getName());
    errorLog.setExceptionMessage(ExceptionUtil.getMessage(e));
    errorLog.setExceptionRootCauseMessage(ExceptionUtil.getRootCauseMessage(e));
    errorLog.setExceptionStackTrace(ExceptionUtils.getStackTrace(e));
    StackTraceElement[] stackTraceElements = e.getStackTrace();
    Assert.notEmpty(stackTraceElements, "异常 stackTraceElements 不能为空");
    StackTraceElement stackTraceElement = stackTraceElements[0];
    errorLog.setExceptionClassName(stackTraceElement.getClassName());
    errorLog.setExceptionFileName(stackTraceElement.getFileName());
    errorLog.setExceptionMethodName(stackTraceElement.getMethodName());
    errorLog.setExceptionLineNumber(stackTraceElement.getLineNumber());
    // 设置其它字段
    errorLog.setTraceId(TracerUtils.getTraceId());
    errorLog.setApplicationName(applicationName);
    errorLog.setRequestUrl(request.getRequestURI());
    Map<String, Object> requestParams = MapUtil.<String, Object>builder()
            .put("query", ServletUtils.getParamMap(request))
            .put("body", ServletUtils.getBody(request)).build();
    errorLog.setRequestParams(JsonUtils.toJsonString(requestParams));
    errorLog.setRequestMethod(request.getMethod());
    errorLog.setUserAgent(ServletUtils.getUserAgent(request));
    errorLog.setUserIp(ServletUtils.getClientIP(request));
    errorLog.setExceptionTime(LocalDateTime.now());
  }
}
