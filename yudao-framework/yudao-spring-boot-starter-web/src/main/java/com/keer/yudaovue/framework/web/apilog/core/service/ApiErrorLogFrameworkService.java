package com.keer.yudaovue.framework.web.apilog.core.service;

import com.keer.yudaovue.module.infraApi.api.logger.dto.ApiErrorLogCreateReqDTO;

/**
 * API 错误日志 Framework Service 接口
 * @author keer
 * @date 2024-04-21
 */
public interface ApiErrorLogFrameworkService {
    /**
     * 创建 API 错误日志
     *
     * @param reqDTO API 错误日志
     */
    void createApiErrorLog(ApiErrorLogCreateReqDTO reqDTO);
}
