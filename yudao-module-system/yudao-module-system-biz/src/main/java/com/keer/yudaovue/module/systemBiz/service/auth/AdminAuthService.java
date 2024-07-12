package com.keer.yudaovue.module.systemBiz.service.auth;

import com.keer.yudaovue.module.systemBiz.controller.admin.auth.vo.AuthLoginReqVO;
import com.keer.yudaovue.module.systemBiz.controller.admin.auth.vo.AuthLoginRespVO;
import jakarta.validation.Valid;

/**
 * 管理后台的认证 Service 接口
 *
 * <p>提供用户的登录、登出的能力
 *
 * @author keer
 * @date 2024-04-18
 */
public interface AdminAuthService {
  // todo

  /**
   * 账号登录
   *
   * @param reqVo
   * @return
   */
  AuthLoginRespVO login(@Valid AuthLoginReqVO reqVo);

  /**
   * 基于 token 退出登录
   *
   * @param token
   * @param logoutType
   */
  void logout(String token, Integer logoutType);
}
