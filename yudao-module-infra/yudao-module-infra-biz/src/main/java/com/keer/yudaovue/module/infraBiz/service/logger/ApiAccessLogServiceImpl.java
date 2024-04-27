package com.keer.yudaovue.module.infraBiz.service.logger;

import com.keer.yudaovue.framework.common.util.object.BeanUtils;
import com.keer.yudaovue.module.infraApi.api.logger.dto.ApiAccessLogCreateReqDTO;
import com.keer.yudaovue.module.infraBiz.dal.dataobject.logger.ApiAccessLogDO;
import com.keer.yudaovue.module.infraBiz.dal.mysql.logger.ApiAccessLogMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * API 访问日志 Service 实现类
 *
 * @author keer
 * @date 2024-04-27
 */
@Slf4j(topic = ">>> ApiAccessLogServiceImpl")
@Service
@Validated
public class ApiAccessLogServiceImpl implements ApiAccessLogService {
  @Resource private ApiAccessLogMapper apiAccessLogMapper;

  @Override
  public void createApiAccessLog(ApiAccessLogCreateReqDTO reqDTO) {
    ApiAccessLogDO apiAccessLogDO = BeanUtils.toBean(reqDTO, ApiAccessLogDO.class);
    apiAccessLogMapper.insert(apiAccessLogDO);
  }
}
