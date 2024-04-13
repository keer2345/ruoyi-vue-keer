package com.keer.yudaovue.module.systemBiz.controller.admin.tenant;

import com.keer.yudaovue.framework.common.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

  // todo
  @GetMapping("get-by-website")
  @Operation(summary = "使用域名，获取租户信息", description = "登录界面，工具用户的域名，获取租户信息")
  public CommonResult<String> getTenantByWebsite() {
    log.info("getTenantByWebsite");
    return CommonResult.success("ok");
  }
}
