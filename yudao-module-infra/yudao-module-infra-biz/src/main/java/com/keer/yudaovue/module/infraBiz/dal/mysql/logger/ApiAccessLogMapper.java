package com.keer.yudaovue.module.infraBiz.dal.mysql.logger;

import com.keer.yudaovue.framework.mybatis.core.mapper.BaseMapperX;
import com.keer.yudaovue.module.infraBiz.dal.dataobject.logger.ApiAccessLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * API 访问日志 Mapper
 *
 * @author keer
 * @date 2024-04-27
 */
@Mapper
public interface ApiAccessLogMapper extends BaseMapperX<ApiAccessLogDO> {}
