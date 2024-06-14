package com.keer.yudaovue.module.systemApi.api;

import com.keer.yudaovue.module.systemApi.api.oauth2.dto.OAuth2AccessTokenCheckRespDTO;

/**
 * OAuth2.0 Token API 接口
 * @author keer
 * @date 2024-06-05
 */
public interface OAuth2TokenApi {
    // todo
    /**
     *
     * 校验访问令牌
     * @param accessToken
     * @return
     */
    OAuth2AccessTokenCheckRespDTO checkAccessToken(String accessToken);
}
