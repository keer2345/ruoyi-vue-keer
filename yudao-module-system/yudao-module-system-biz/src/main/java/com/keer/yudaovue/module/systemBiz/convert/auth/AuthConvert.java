package com.keer.yudaovue.module.systemBiz.convert.auth;


import com.keer.yudaovue.module.systemBiz.controller.admin.auth.vo.AuthLoginRespVO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author keer
 * @date 2024-06-23
 */
@Mapper
public interface AuthConvert {
    // todo

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    AuthLoginRespVO convert(OAuth2AccessTokenDO bean);
}
