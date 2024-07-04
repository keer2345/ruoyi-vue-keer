package com.keer.yudaovue.module.systemBiz.service.dict;

import com.keer.yudaovue.module.systemBiz.dal.dataobject.dict.DictDataDO;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 字典数据 Service 接口
 * @author keer
 * @date 2024-07-04
 */
public interface DictDataService {
    /**
     *
     * 获得字典数据列表
     * @param status
     * @param dictType
     * @return
     */
    List<DictDataDO> getDictDataList(@Nullable Integer status, @Nullable String dictType);
}
