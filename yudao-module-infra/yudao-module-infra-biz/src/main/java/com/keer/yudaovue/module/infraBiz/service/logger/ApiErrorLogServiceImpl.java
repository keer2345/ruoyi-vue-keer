package com.keer.yudaovue.module.infraBiz.service.logger;

import com.keer.yudaovue.framework.common.util.object.BeanUtils;
import com.keer.yudaovue.module.infraApi.api.logger.dto.ApiErrorLogCreateReqDTO;
import com.keer.yudaovue.module.infraBiz.dal.dataobject.logger.ApiErrorLogDO;
import com.keer.yudaovue.module.infraBiz.dal.mysql.logger.ApiErrorLogMapper;
import com.keer.yudaovue.module.infraBiz.enums.logger.ApiErrorLogProcessStatusEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * API 错误日志 Service 实现类
 *
 * @author keer
 * @date 2024-04-22
 */
@Slf4j(topic = ">>> ApiErrorLogServiceImpl")
@Service
@Validated
public class ApiErrorLogServiceImpl implements ApiErrorLogService {
  // todo

  @Resource private ApiErrorLogMapper apiErrorLogMapper;

  @Override
  public void createApiErrorLog(ApiErrorLogCreateReqDTO createDTO) {
    log.info("createApiErrorLog( {} )", createDTO);

    ApiErrorLogDO apiErrorLog = BeanUtils.toBean(createDTO, ApiErrorLogDO.class);
    apiErrorLog.setProcessStatus(ApiErrorLogProcessStatusEnum.INIT.getStatus());
    apiErrorLogMapper.insert(apiErrorLog);
  }
}
