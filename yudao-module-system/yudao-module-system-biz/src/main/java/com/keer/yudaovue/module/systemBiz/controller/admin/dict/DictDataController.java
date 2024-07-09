package com.keer.yudaovue.module.systemBiz.controller.admin.dict;

import com.keer.yudaovue.framework.common.enums.CommonStatusEnum;
import com.keer.yudaovue.framework.common.pojo.CommonResult;
import com.keer.yudaovue.framework.common.util.object.BeanUtils;
import com.keer.yudaovue.module.systemBiz.controller.admin.dict.vo.data.DictDataSimpleRespVO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.dict.DictDataDO;
import com.keer.yudaovue.module.systemBiz.dal.mysql.dict.DictDataMapper;
import com.keer.yudaovue.module.systemBiz.service.dict.DictDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.keer.yudaovue.framework.common.pojo.CommonResult.success;

/**
 * @author keer
 * @date 2024-07-01
 */
@Tag(name = "管理后台 - 字典数据")
@RestController
@RequestMapping("/system/dict-data")
@Validated
public class DictDataController {

  @Resource private DictDataService dictDataService;

  @GetMapping(value = {"/list-all-simple", "simple-list"})
  @Operation(summary = "获得全部字典数据列表", description = "一般用于管理后台缓存字典数据在本地")
  // @PermitAll
  // 无需添加权限认证，因为前端全局都需要
  public CommonResult<List<DictDataSimpleRespVO>> getSimpleDictDataList() {
    List<DictDataDO> list =
        dictDataService.getDictDataList(CommonStatusEnum.ENABLE.getStatus(), null);
    return success(BeanUtils.toBean(list, DictDataSimpleRespVO.class));
  }
}
