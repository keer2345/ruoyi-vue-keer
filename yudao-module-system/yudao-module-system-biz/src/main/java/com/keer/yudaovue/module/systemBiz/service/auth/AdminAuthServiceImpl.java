package com.keer.yudaovue.module.systemBiz.service.auth;

import com.google.common.annotations.VisibleForTesting;
import com.keer.yudaovue.module.systemBiz.controller.admin.auth.vo.AuthLoginReqVO;
import com.keer.yudaovue.module.systemBiz.controller.admin.auth.vo.AuthLoginRespVO;
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

  @Override
  public AuthLoginRespVO login(AuthLoginReqVO reqVo) {
    // 校验验证码
    validateCaptcha(reqVo);
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
