package com.keer.yudaovue.framework.web.apilog.core.service;

import com.keer.yudaovue.module.infraApi.api.logger.dto.ApiErrorLogApi;
import com.keer.yudaovue.module.infraApi.api.logger.dto.ApiErrorLogCreateReqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

/**
 * API 错误日志 Framework Service 实现类
 *
 * <p>基于 {@link ApiErrorLogApi} 服务，记录错误日志
 *
 * @author keer
 * @date 2024-04-21
 */
@Slf4j(topic = ">>> ApiErrorLogFrameworkServiceImpl")
@RequiredArgsConstructor
public class ApiErrorLogFrameworkServiceImpl implements ApiErrorLogFrameworkService {
  private final ApiErrorLogApi apiErrorLogApi;
  @Override
  @Async
  public void createApiErrorLog(ApiErrorLogCreateReqDTO reqDTO) {
    log.info("[createApiErrorLog][createReqDTO({})]", reqDTO);
  }
}
