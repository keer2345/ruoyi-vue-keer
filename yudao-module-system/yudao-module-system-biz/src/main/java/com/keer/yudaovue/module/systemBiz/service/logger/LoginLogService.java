package com.keer.yudaovue.module.systemBiz.service.logger;

import com.keer.yudaovue.module.systemApi.api.logger.dto.LoginLogCreateReqDTO;
import jakarta.validation.Valid;

/**
 * 登录日志 Service 接口
 * @author keer
 * @date 2024-04-19
 */
public interface LoginLogService {
    /**
     * 创建登录日志
     * @param reqDTO
     */
    void createLoginLog(@Valid LoginLogCreateReqDTO reqDTO);
}
