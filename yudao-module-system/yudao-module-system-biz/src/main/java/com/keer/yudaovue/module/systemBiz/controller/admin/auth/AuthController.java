package com.keer.yudaovue.module.systemBiz.controller.admin.auth;

import cn.hutool.core.collection.CollUtil;
import com.keer.yudaovue.framework.common.enums.CommonStatusEnum;
import com.keer.yudaovue.framework.common.pojo.CommonResult;
import com.keer.yudaovue.module.systemBiz.controller.admin.auth.vo.AuthLoginReqVO;
import com.keer.yudaovue.module.systemBiz.controller.admin.auth.vo.AuthLoginRespVO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.permission.MenuDO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.permission.RoleDO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.user.AdminUserDO;
import com.keer.yudaovue.module.systemBiz.service.auth.AdminAuthService;
import com.keer.yudaovue.module.systemBiz.service.permission.MenuService;
import com.keer.yudaovue.module.systemBiz.service.permission.PermissionService;
import com.keer.yudaovue.module.systemBiz.service.permission.RoleService;
import com.keer.yudaovue.module.systemBiz.service.user.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.keer.yudaovue.framework.common.pojo.CommonResult.success;
import static com.keer.yudaovue.framework.common.util.ollection.CollectionUtils.convertSet;
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
  @Resource private PermissionService permissionService;
  @Resource private RoleService roleService;
  @Resource private MenuService menuService;

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
    Set<Long> roleIds = permissionService.getUserRoleIdListByUserId(getLoginUserId());
    if (CollUtil.isEmpty(roleIds)) {
      // todo
    }

    List<RoleDO> roles = roleService.getRoleList(roleIds);
    roles.removeIf(role -> !CommonStatusEnum.ENABLE.getStatus().equals(role.getStatus()));

    // 1.3 获得菜单列表
    Set<Long> menuIds = permissionService.getRoleMenuListByRoleId(convertSet(roles, RoleDO::getId));
    List<MenuDO> menuList = menuService.getMenuList(menuIds);
    menuList.removeIf(menu -> !CommonStatusEnum.ENABLE.getStatus().equals(menu.getStatus()));

    // 2. 拼接结果返回
    return success(null);
  }
}
