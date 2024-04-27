package com.keer.yudaovue.framework.web.apilog.core.service;

import com.keer.yudaovue.module.infraApi.api.logger.dto.ApiAccessLogCreateReqDTO;

/**
 * API 访问日志 Framework Service 接口
 *
 * @author keer
 * @date 2024-04-24
 */
public interface ApiAccessLogFrameworkService {

  /**
   * 创建 API 访问日志
   *
   * @param reqDTO API 访问日志
   */
  void createApiAccessLog(ApiAccessLogCreateReqDTO reqDTO);
}
