package com.keer.yudaovue.framework.security.core.aop;

import com.keer.yudaovue.framework.security.core.annotations.PreAuthenticated;
import com.keer.yudaovue.framework.security.core.util.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import static com.keer.yudaovue.framework.common.exception.enums.GlobalErrorCodeConstants.UNAUTHORIZED;
import static com.keer.yudaovue.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * @author keer
 * @date 2024-05-21
 */
@Aspect
@Slf4j(topic = ">>> PreAuthenticatedAspect")
public class PreAuthenticatedAspect {
  @Around("@annotation(preAuthenticated)")
  public Object around(ProceedingJoinPoint joinPoint, PreAuthenticated preAuthenticated)
      throws Throwable {
    if (SecurityFrameworkUtils.getLoginUser() == null) {
      throw exception(UNAUTHORIZED);
    }
    return joinPoint.proceed();
  }
}
