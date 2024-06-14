package com.keer.yudaovue.module.systemBiz.dal.mysql.oauth2;

import com.keer.yudaovue.framework.mybatis.core.mapper.BaseMapperX;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.oauth2.OAuth2RefreshTokenDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author keer
 * @date 2024-06-06
 */
@Mapper
public interface OAuth2AccessTokenMapper extends BaseMapperX<OAuth2AccessTokenDO> {
  default OAuth2AccessTokenDO selectByAccessToken(String accessToken) {
    return selectOne(OAuth2AccessTokenDO::getAccessToken, accessToken);
  }
  ;
}
