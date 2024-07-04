package com.keer.yudaovue.module.systemBiz.service.dict;

import com.keer.yudaovue.module.systemBiz.dal.dataobject.dict.DictDataDO;
import com.keer.yudaovue.module.systemBiz.dal.mysql.dict.DictDataMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典数据 Service 实现类
 * @author keer
 * @date 2024-07-04
 */
@Service
@Slf4j
public class DictDataServiceImpl implements DictDataService{
    @Resource private DictDataMapper dictDataMapper;
    @Override
    public List<DictDataDO> getDictDataList(Integer status, String dictType) {
        List<DictDataDO> list  = dictDataMapper.selectListByStatusAndDictType(status,dictType);
        return List.of();
    }
}
