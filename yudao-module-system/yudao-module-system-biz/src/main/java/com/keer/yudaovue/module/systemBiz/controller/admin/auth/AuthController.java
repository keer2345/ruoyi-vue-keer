package com.keer.yudaovue.module.systemBiz.controller.admin.auth;

import com.keer.yudaovue.framework.common.pojo.CommonResult;
import com.keer.yudaovue.module.systemBiz.controller.admin.auth.vo.AuthLoginReqVO;
import com.keer.yudaovue.module.systemBiz.controller.admin.auth.vo.AuthLoginRespVO;
import com.keer.yudaovue.module.systemBiz.service.auth.AdminAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.keer.yudaovue.framework.common.pojo.CommonResult.success;

/**
 * @author keer
 * @date 2024-04-17
 */
@Tag(name = "管理后台 - 认证")
@RestController
@RequestMapping("/system/auth")
@Validated
@Slf4j(topic = ">>> AuthController")
public class AuthController {
  // todo

  @Resource private AdminAuthService authService;

  // todo
  @PostMapping("login")
  @PermitAll
  @Operation(summary = "使用账号密码登录")
  public CommonResult<AuthLoginRespVO> login(@RequestBody @Valid AuthLoginReqVO reqVo) {
    return success(authService.login(reqVo));
  }
}
