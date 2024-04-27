package com.keer.yudaovue.module.infraBiz.dal.mysql.logger;

import com.keer.yudaovue.framework.mybatis.core.mapper.BaseMapperX;
import com.keer.yudaovue.module.infraBiz.dal.dataobject.logger.ApiErrorLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * API 错误日志 Mapper
 *
 * @author keer
 * @date 2024-04-22
 */
@Mapper
public interface ApiErrorLogMapper extends BaseMapperX<ApiErrorLogDO> {}
