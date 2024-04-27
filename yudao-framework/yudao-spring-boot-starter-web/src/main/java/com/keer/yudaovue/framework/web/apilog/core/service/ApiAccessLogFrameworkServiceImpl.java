package com.keer.yudaovue.framework.web.apilog.core.service;

import com.keer.yudaovue.module.infraApi.api.logger.ApiAccessLogApi;
import com.keer.yudaovue.module.infraApi.api.logger.dto.ApiAccessLogCreateReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

/**
 * API 访问日志 Framework Service 实现类
 *
 * @author keer
 * @date 2024-04-27
 */
@RequiredArgsConstructor
public class ApiAccessLogFrameworkServiceImpl implements ApiAccessLogFrameworkService {
  private final ApiAccessLogApi apiAccessLogApi;

  @Override
  @Async
  public void createApiAccessLog(ApiAccessLogCreateReqDTO reqDTO) {
    apiAccessLogApi.createApiAccessLog(reqDTO);
  }
}
