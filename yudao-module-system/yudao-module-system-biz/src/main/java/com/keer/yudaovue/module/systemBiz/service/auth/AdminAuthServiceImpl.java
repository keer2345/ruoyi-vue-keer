package com.keer.yudaovue.module.systemBiz.service.auth;

import cn.hutool.core.util.ObjUtil;
import com.google.common.annotations.VisibleForTesting;
import com.keer.yudaovue.framework.common.enums.CommonStatusEnum;
import com.keer.yudaovue.module.systemBiz.controller.admin.auth.vo.AuthLoginReqVO;
import com.keer.yudaovue.module.systemBiz.controller.admin.auth.vo.AuthLoginRespVO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.user.AdminUserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Auth Service 实现类
 *
 * @author keer
 * @date 2024-04-18
 */
@Service
@Slf4j(topic = ">>> AdminAuthServiceImpl")
public class AdminAuthServiceImpl implements AdminAuthService {

  /** 验证码的开关，默认为 true */
  @Value("${yudao.captcha.enable:true}")
  private Boolean captchaEnable;

  private AdminUserDO authenticate(String username, String password) {

    // 判断用户是否存在
    AdminUserDO user = null;
    if (ObjUtil.isNull(user)) {
      // 抛出异常
      return null;
    }
    // 校验密码

    // 校验是否禁用
    if (CommonStatusEnum.isDisable(user.getStatus())) {
      // 抛出异常
    }

    return user;
  }

  @Override
  public AuthLoginRespVO login(AuthLoginReqVO reqVo) {
    // 校验验证码
    validateCaptcha(reqVo);

    // 使用账号密码，进行登录
    AdminUserDO user = authenticate(reqVo.getUsername(), reqVo.getPassword());

    return null;
  }

  @VisibleForTesting
  void validateCaptcha(AuthLoginReqVO reqVo) {
    // 如果验证码关闭，则不进行校验
    if (!captchaEnable) {
      return;
    }
    // 校验验证码
    // todo
  }
}
