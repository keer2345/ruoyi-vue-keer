package com.keer.yudaovue.module.systemBiz.api.oauth2;

import com.keer.yudaovue.framework.common.util.object.BeanUtils;
import com.keer.yudaovue.module.systemApi.api.OAuth2TokenApi;
import com.keer.yudaovue.module.systemApi.api.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import com.keer.yudaovue.module.systemBiz.service.oauth2.OAuth2TokenService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * OAuth2.0 Token API 实现类
 * @author keer
 * @date 2024-06-06
 */
@Service
public class OAuth2TokenApiImpl implements OAuth2TokenApi {
    @Resource
    private OAuth2TokenService oauth2TokenService;
    @Override
    public OAuth2AccessTokenCheckRespDTO checkAccessToken(String accessToken) {
        OAuth2AccessTokenDO accessTokenDO=oauth2TokenService.checkAccessToken(accessToken);
        return BeanUtils.toBean(accessTokenDO, OAuth2AccessTokenCheckRespDTO.class);
    }
}
