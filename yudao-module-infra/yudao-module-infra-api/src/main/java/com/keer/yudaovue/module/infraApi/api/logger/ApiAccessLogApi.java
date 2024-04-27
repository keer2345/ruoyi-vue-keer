package com.keer.yudaovue.module.infraApi.api.logger;

import com.keer.yudaovue.module.infraApi.api.logger.dto.ApiAccessLogCreateReqDTO;
import jakarta.validation.Valid;

/**
 * API 访问日志的 API 接口
 *
 * @author keer
 * @date 2024-04-27
 */
public interface ApiAccessLogApi {
  /**
   * 创建 API 访问日志
   *
   * @param reqDTO
   */
  void createApiAccessLog(@Valid ApiAccessLogCreateReqDTO reqDTO);
}
