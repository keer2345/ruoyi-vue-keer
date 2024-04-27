package com.keer.yudaovue.module.infraBiz.api.logger;

import com.keer.yudaovue.module.infraApi.api.logger.ApiAccessLogApi;
import com.keer.yudaovue.module.infraApi.api.logger.dto.ApiAccessLogCreateReqDTO;
import com.keer.yudaovue.module.infraBiz.service.logger.ApiAccessLogService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * API 访问日志的 API 实现类
 * @author keer
 * @date 2024-04-27
 */
@Slf4j(topic = ">>> ApiAccessLogApiImpl")
@Service
@Validated
public class ApiAccessLogApiImpl implements ApiAccessLogApi {

    @Resource
    private ApiAccessLogService apiAccessLogService;
    @Override
    public void createApiAccessLog(ApiAccessLogCreateReqDTO reqDTO) {
        apiAccessLogService.createApiAccessLog(reqDTO);
    }
}
