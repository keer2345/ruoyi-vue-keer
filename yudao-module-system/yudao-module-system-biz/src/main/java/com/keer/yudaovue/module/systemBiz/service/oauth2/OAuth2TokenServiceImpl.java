package com.keer.yudaovue.module.systemBiz.service.oauth2;

import com.keer.yudaovue.module.systemBiz.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.oauth2.OAuth2ClientDO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.oauth2.OAuth2RefreshTokenDO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * OAuth2.0 Token Service 实现类
 *
 * @author keer
 * @date 2024-04-30
 */

/** OAuth2.0 Token Service 实现类 */
@Service
public class OAuth2TokenServiceImpl implements OAuth2TokenService {

  @Resource
  private OAuth2ClientService oauth2ClientService;
  @Override
  public OAuth2AccessTokenDO createAccessToken(
      Long userId, Integer userType, String clientId, List<String> scope) {
    OAuth2ClientDO clientDO=oauth2ClientService.validOAuthClientFormCache(clientId);
    // 创建刷新令牌
    // OAuth2RefreshTokenDO refreshTokenDO=createOAuth2RefreshToken(userId,userType,clientDO,scope);

    return null;
  }
}
