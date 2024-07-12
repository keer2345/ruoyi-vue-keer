package com.keer.yudaovue.module.systemBiz.service.oauth2;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjUtil;
import com.keer.yudaovue.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.keer.yudaovue.framework.common.util.date.DateUtils;
import com.keer.yudaovue.framework.tenant.core.context.TenantContextHolder;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.oauth2.OAuth2ClientDO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.oauth2.OAuth2RefreshTokenDO;
import com.keer.yudaovue.module.systemBiz.dal.mysql.oauth2.OAuth2AccessTokenMapper;
import com.keer.yudaovue.module.systemBiz.dal.mysql.oauth2.OAuth2RefreshTokenMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

import static com.keer.yudaovue.framework.common.exception.util.ServiceExceptionUtil.exception0;

/**
 * OAuth2.0 Token Service 实现类
 *
 * @author keer
 * @date 2024-04-30
 */

/** OAuth2.0 Token Service 实现类 */
@Slf4j(topic = ">>> OAuth2TokenServiceImpl")
@Service
public class OAuth2TokenServiceImpl implements OAuth2TokenService {

  @Resource private OAuth2AccessTokenMapper oAuth2AccessTokenMapper;
  @Resource private OAuth2RefreshTokenMapper oAuth2RefreshTokenMapper;
  @Resource private OAuth2ClientService oauth2ClientService;

  @Override
  public OAuth2AccessTokenDO createAccessToken(
      Long userId, Integer userType, String clientId, List<String> scope) {
    OAuth2ClientDO clientDO = oauth2ClientService.validOAuthClientFormCache(clientId);
    // 创建刷新令牌
    OAuth2RefreshTokenDO refreshTokenDO =
        createOAuth2RefreshToken(userId, userType, clientDO, scope);
    // 创建访问令牌
    return createOAuth2AccessToken(refreshTokenDO, clientDO);
  }

  private OAuth2AccessTokenDO createOAuth2AccessToken(
      OAuth2RefreshTokenDO refreshTokenDO, OAuth2ClientDO clientDO) {
    OAuth2AccessTokenDO accessTokenDO =
        new OAuth2AccessTokenDO()
            .setAccessToken(generateAccessToken())
            .setUserId(refreshTokenDO.getUserId())
            .setUserType(refreshTokenDO.getUserType())
            .setClientId(clientDO.getClientId())
            .setScopes(refreshTokenDO.getScopes())
            .setRefreshToken(refreshTokenDO.getRefreshToken())
            .setExpiresTime(
                LocalDateTime.now().plusSeconds(clientDO.getAccessTokenValiditySeconds()));
    accessTokenDO.setTenantId(
        TenantContextHolder.getTenantId()); // 手动设置租户编号，避免缓存到 Redis 的时候，无对应的租户编号
    oAuth2AccessTokenMapper.insert(accessTokenDO);

    // 记录到Redis中
    // todo

    return accessTokenDO;
  }

  private OAuth2RefreshTokenDO createOAuth2RefreshToken(
      Long userId, Integer userType, OAuth2ClientDO clientDO, List<String> scope) {
    OAuth2RefreshTokenDO refreshToken =
        new OAuth2RefreshTokenDO()
            .setRefreshToken(generateRefreshToken())
            .setUserId(userId)
            .setUserType(userType)
            .setClientId(clientDO.getClientId())
            .setScopes(scope)
            .setExpiresTime(
                LocalDateTime.now().plusSeconds(clientDO.getRefreshTokenValiditySeconds()));
    oAuth2RefreshTokenMapper.insert(refreshToken);
    return refreshToken;
  }

  private static String generateAccessToken() {
    return IdUtil.fastSimpleUUID();
  }

  private static String generateRefreshToken() {
    return IdUtil.fastSimpleUUID();
  }

  @Override
  public OAuth2AccessTokenDO getAccessToken(String accessToken) {
    // 优先从 Redis 中获取
    // todo
    OAuth2AccessTokenDO accessTokenDO = null;

    // 获取不到，从 MySQL 中获取
    accessTokenDO = oAuth2AccessTokenMapper.selectByAccessToken(accessToken);
    // 如果在 MySQL 存在，则往 Redis 中写入
    if (ObjUtil.isNotNull(accessToken) && !DateUtils.isExpired(accessTokenDO.getExpiresTime())) {
      // todo
    }

    return accessTokenDO;
  }

  @Override
  public OAuth2AccessTokenDO checkAccessToken(String accessToken) {
    OAuth2AccessTokenDO accessTokenDO = getAccessToken(accessToken);
    if (ObjUtil.isNull(accessTokenDO)) {
      throw exception0(GlobalErrorCodeConstants.UNAUTHORIZED.getCode(), "访问令牌不存在");
    }
    if (DateUtils.isExpired(accessTokenDO.getExpiresTime())) {
      throw exception0(GlobalErrorCodeConstants.UNAUTHORIZED.getCode(), "访问令牌已过期");
    }
    return accessTokenDO;
  }

  @Override
  public OAuth2AccessTokenDO removeAccessToken(String accessToken) {
    // 删除访问令牌
    OAuth2AccessTokenDO accessTokenDO=oAuth2AccessTokenMapper.selectByAccessToken(accessToken);
    if(ObjUtil.isNull(accessTokenDO)){
      return null;
    }
    oAuth2AccessTokenMapper.deleteById(accessTokenDO.getId());
    // todo redis

    // 删除刷新令牌
    oAuth2RefreshTokenMapper.deleteByRefreshToken(accessTokenDO.getRefreshToken());

    return accessTokenDO;
  }
}
