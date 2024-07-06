package com.keer.yudaovue.module.systemBiz.controller.admin.auth;

import com.keer.yudaovue.framework.common.pojo.CommonResult;
import com.keer.yudaovue.module.systemBiz.controller.admin.auth.vo.AuthLoginReqVO;
import com.keer.yudaovue.module.systemBiz.controller.admin.auth.vo.AuthLoginRespVO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.user.AdminUserDO;
import com.keer.yudaovue.module.systemBiz.service.auth.AdminAuthService;
import com.keer.yudaovue.module.systemBiz.service.permission.PermissionService;
import com.keer.yudaovue.module.systemBiz.service.user.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.keer.yudaovue.framework.common.pojo.CommonResult.success;
import static com.keer.yudaovue.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

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
  @Resource private AdminUserService userService;
  @Resource private PermissionService permissionService ;

  // todo
  @PostMapping("login")
  @PermitAll
  @Operation(summary = "使用账号密码登录")
  public CommonResult<AuthLoginRespVO> login(@RequestBody @Valid AuthLoginReqVO reqVo) {
    return success(authService.login(reqVo));
  }

  @GetMapping("get-permission-info")
  @Operation(summary = "获取登录用户的权限信息")
  public CommonResult<String> getPermissionInfo() {
    log.info("getPermissionInfo");
    // 1.1 获得用户信息
    AdminUserDO user = userService.getUser(getLoginUserId());
    if (user == null) {
      return success(null);
    }

    // 1.2 获得角色列表
    Set<Long> roleIds=permissionService.getUserRoleIdListByUserId(getLoginUserId());
  }
}
