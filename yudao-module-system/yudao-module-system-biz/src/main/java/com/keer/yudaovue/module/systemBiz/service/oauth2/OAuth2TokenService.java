package com.keer.yudaovue.module.systemBiz.service.oauth2;

import com.keer.yudaovue.module.systemBiz.dal.dataobject.oauth2.OAuth2AccessTokenDO;

import java.util.List;

/**
 * OAuth2.0 Token Service 接口
 *
 * <p>从功能上，和 Spring Security OAuth 的 DefaultTokenServices + JdbcTokenStore 的功能，提供访问令牌、刷新令牌的操作
 *
 * @author keer
 * @date 2024-04-28
 */
public interface OAuth2TokenService {
  /**
   * 创建访问令牌
   *
   * <p>注意：该流程中，会包含创建刷新令牌的创建
   *
   * <p>参考 DefaultTokenServices 的 createAccessToken 方法
   *
   * @param userId
   * @param userType
   * @param clientId
   * @param scope
   * @return
   */
  OAuth2AccessTokenDO createAccessToken(
      Long userId, Integer userType, String clientId, List<String> scope);

  /**
   * 获得访问令牌
   *
   * <p>参考 DefaultTokenServices 的 getAccessToken 方法
   *
   * @param accessToken
   * @return
   */
  OAuth2AccessTokenDO getAccessToken(String accessToken);

  /**
   * 校验访问令牌
   *
   * @param accessToken
   * @return
   */
  OAuth2AccessTokenDO checkAccessToken(String accessToken);

  /**
   * 移除访问令牌 注意：该流程中，会移除相关的刷新令牌
   *
   * <p>参考 DefaultTokenServices 的 revokeToken 方法
   *
   * @param accessToken
   * @return
   */
  OAuth2AccessTokenDO removeAccessToken(String accessToken);
}
