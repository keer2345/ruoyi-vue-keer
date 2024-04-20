package com.keer.yudaovue.module.systemBiz.dal.mysql.logger;

import com.keer.yudaovue.framework.mybatis.core.mapper.BaseMapperX;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.logger.LoginLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author keer
 * @date 2024-04-19
 */
@Mapper
public interface LoginLogMapper extends BaseMapperX<LoginLogDO> {}
