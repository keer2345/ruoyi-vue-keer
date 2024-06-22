package com.keer.yudaovue.module.systemBiz.dal.mysql.oauth2;

import com.keer.yudaovue.framework.mybatis.core.mapper.BaseMapperX;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.oauth2.OAuth2ClientDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * OAuth2 客户端 Mapper
 *
 * @author keer
 * @date 2024-06-22
 */
@Mapper
public interface OAuth2ClientMapper extends BaseMapperX<OAuth2ClientDO> {
  default OAuth2ClientDO selectByClientId(String clientId) {
    return selectOne(OAuth2ClientDO::getClientId, clientId);
  }
  ;
}
