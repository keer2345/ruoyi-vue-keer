package com.keer.yudaovue.module.infraApi.api.logger;

import com.keer.yudaovue.module.infraApi.api.logger.dto.ApiErrorLogCreateReqDTO;
import jakarta.validation.Valid;

/**
 * API 错误日志的 API 接口
 *
 * @author keer
 * @date 2024-04-21
 */
public interface ApiErrorLogApi {
  /**
   * 创建 API 错误日志
   *
   * @param createDTO 创建信息
   */
  void createApiErrorLog(@Valid ApiErrorLogCreateReqDTO createDTO);
}
