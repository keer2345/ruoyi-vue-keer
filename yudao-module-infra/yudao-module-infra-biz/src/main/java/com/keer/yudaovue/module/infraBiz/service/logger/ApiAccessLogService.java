package com.keer.yudaovue.module.infraBiz.service.logger;

import com.keer.yudaovue.module.infraApi.api.logger.dto.ApiAccessLogCreateReqDTO;

/**
 * API 访问日志 Service 接口
 *
 * @author keer
 * @date 2024-04-27
 */
public interface ApiAccessLogService {
  /**
   * 创建 API 访问日志
   *
   * @param reqDTO
   */
  void createApiAccessLog(ApiAccessLogCreateReqDTO reqDTO);
}
