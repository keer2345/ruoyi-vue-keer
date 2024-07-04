package com.keer.yudaovue.module.systemBiz.dal.mysql.dict;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.keer.yudaovue.framework.mybatis.core.mapper.BaseMapperX;
import com.keer.yudaovue.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.dict.DictDataDO;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author keer
 * @date 2024-07-03
 */
@Mapper
public interface DictDataMapper extends BaseMapperX<DictDataDO> {

  default List<DictDataDO> selectListByStatusAndDictType(Integer status, String dictType) {
    return selectList(
        new LambdaQueryWrapperX<DictDataDO>()
            .eqIfPresent(DictDataDO::getStatus, status)
            .eqIfPresent(DictDataDO::getDictType, dictType));
  }
}
