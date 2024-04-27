package com.keer.yudaovue.module.infraBiz.api.logger;

import com.keer.yudaovue.module.infraApi.api.logger.ApiErrorLogApi;
import com.keer.yudaovue.module.infraApi.api.logger.dto.ApiErrorLogCreateReqDTO;
import com.keer.yudaovue.module.infraBiz.service.logger.ApiErrorLogService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * @author keer
 * @date 2024-04-22
 */
@Slf4j(topic = ">>> ApiErrorLogApiImpl")
@Service
@Validated
public class ApiErrorLogApiImpl implements ApiErrorLogApi {

  @Resource private ApiErrorLogService apiErrorLogService;

  @Override
  public void createApiErrorLog(ApiErrorLogCreateReqDTO createDTO) {
    log.info("创建 API 错误日志: {}", createDTO);
    apiErrorLogService.createApiErrorLog(createDTO);
  }
}
