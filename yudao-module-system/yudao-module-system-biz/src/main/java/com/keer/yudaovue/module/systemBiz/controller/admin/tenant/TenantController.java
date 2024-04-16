package com.keer.yudaovue.module.systemBiz.controller.admin.tenant;

import com.keer.yudaovue.framework.common.pojo.CommonResult;
import com.keer.yudaovue.framework.common.util.object.BeanUtils;
import com.keer.yudaovue.module.systemBiz.controller.admin.tenant.vo.tenant.TenantSimpleRespVO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.tenant.TenantDO;
import com.keer.yudaovue.module.systemBiz.service.tenant.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author keer
 * @date 2024-04-13
 */
@Slf4j(topic = ">>> TenantController")
@Tag(name = "管理后台 - 租户")
@RestController
@RequestMapping("/system/tenant")
public class TenantController {

  @Resource
  private TenantService tenantService;

  // todo
  @GetMapping("get-by-website")
  @Operation(summary = "使用域名，获取租户信息", description = "登录界面，工具用户的域名，获取租户信息")
  @Parameter(name = "website", description = "域名", required = true, example = "www.keer.com")
  public CommonResult<TenantSimpleRespVO> getTenantByWebsite(@RequestParam("website") String website) {
    log.info("getTenantByWebsite");
    TenantDO tenant = tenantService.getTenantByWebsite(website);
    log.info("tenant: {}",tenant.toString());
    return CommonResult.success(BeanUtils.toBean(tenant,TenantSimpleRespVO.class));
  }
}
