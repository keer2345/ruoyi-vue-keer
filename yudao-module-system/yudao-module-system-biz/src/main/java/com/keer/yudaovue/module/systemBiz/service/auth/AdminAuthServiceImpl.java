package com.keer.yudaovue.module.systemBiz.service.auth;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjUtil;
import com.google.common.annotations.VisibleForTesting;
import com.keer.yudaovue.framework.common.enums.CommonStatusEnum;
import com.keer.yudaovue.framework.common.enums.UserTypeEnum;
import com.keer.yudaovue.framework.common.util.servlet.ServletUtils;
import com.keer.yudaovue.module.systemApi.api.logger.dto.LoginLogCreateReqDTO;
import com.keer.yudaovue.module.systemApi.enums.logger.LoginLogTypeEnum;
import com.keer.yudaovue.module.systemApi.enums.logger.LoginResultEnum;
import com.keer.yudaovue.module.systemBiz.controller.admin.auth.vo.AuthLoginReqVO;
import com.keer.yudaovue.module.systemBiz.controller.admin.auth.vo.AuthLoginRespVO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.user.AdminUserDO;
import com.keer.yudaovue.module.systemBiz.service.logger.LoginLogService;
import jakarta.annotation.Resource;
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
  @Resource private LoginLogService loginLogService;

  /** 验证码的开关，默认为 true */
  @Value("${yudao.captcha.enable:true}")
  private Boolean captchaEnable;

  private AdminUserDO authenticate(String username, String password) {
    final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
    // 判断用户是否存在
    AdminUserDO user = null;
    if (ObjUtil.isNull(user)) {
      // 记录日志
      createLoginLog(null, username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
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

  private void createLoginLog(
      Long userId, String username, LoginLogTypeEnum logTypeEnum, LoginResultEnum loginResult) {
    LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
    // todo
    reqDTO.setLogType(logTypeEnum.getType());
    reqDTO.setTraceId(null);
    reqDTO.setUserId(userId);
    reqDTO.setUserType(getUserType().getValue());
    reqDTO.setUsername(username);
    reqDTO.setUserAgent(ServletUtils.getUserAgent());
    reqDTO.setUserIp(ServletUtils.getClientIP());
    reqDTO.setResult(loginResult.getResult());
    loginLogService.createLoginLog(reqDTO);
    // 更新最后登录时间
    if (ObjUtil.isNotNull(userId)
        && NumberUtil.equals(LoginResultEnum.SUCCESS.getResult(), loginResult.getResult())) {}
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
  private UserTypeEnum getUserType() {
    return UserTypeEnum.ADMIN;
  }
}
