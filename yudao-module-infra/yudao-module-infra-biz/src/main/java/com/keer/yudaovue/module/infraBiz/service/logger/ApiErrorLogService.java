package com.keer.yudaovue.module.infraBiz.service.logger;

import com.keer.yudaovue.module.infraApi.api.logger.dto.ApiErrorLogCreateReqDTO;

/**
 * API 错误日志 Service 接口
 *
 * @author keer
 * @date 2024-04-22
 */
public interface ApiErrorLogService {
  // todo

  /**
   * 创建 API 错误日志
   *
   * @param createDTO
   */
  void createApiErrorLog(ApiErrorLogCreateReqDTO createDTO);
}
