package com.keer.yudaovue.module.systemBiz.dal.mysql.oauth2;

import com.keer.yudaovue.framework.mybatis.core.mapper.BaseMapperX;
import com.keer.yudaovue.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.oauth2.OAuth2RefreshTokenDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author keer
 * @date 2024-06-22
 */
@Mapper
public interface OAuth2RefreshTokenMapper extends BaseMapperX<OAuth2RefreshTokenDO> {
  default int deleteByRefreshToken(String refreshToken) {
    return delete(
        new LambdaQueryWrapperX<OAuth2RefreshTokenDO>()
            .eq(OAuth2RefreshTokenDO::getRefreshToken, refreshToken));
  }
}
