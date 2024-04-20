package com.keer.yudaovue.module.systemBiz.service.auth;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjUtil;
import com.google.common.annotations.VisibleForTesting;
import com.keer.yudaovue.framework.common.enums.CommonStatusEnum;
import com.keer.yudaovue.framework.common.enums.UserTypeEnum;
import com.keer.yudaovue.framework.common.util.monitor.TracerUtils;
import com.keer.yudaovue.framework.common.util.servlet.ServletUtils;
import com.keer.yudaovue.framework.common.util.validation.ValidationUtils;
import com.keer.yudaovue.module.systemApi.api.logger.dto.LoginLogCreateReqDTO;
import com.keer.yudaovue.module.systemApi.enums.logger.LoginLogTypeEnum;
import com.keer.yudaovue.module.systemApi.enums.logger.LoginResultEnum;
import com.keer.yudaovue.module.systemBiz.controller.admin.auth.vo.AuthLoginReqVO;
import com.keer.yudaovue.module.systemBiz.controller.admin.auth.vo.AuthLoginRespVO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.user.AdminUserDO;
import com.keer.yudaovue.module.systemBiz.service.logger.LoginLogService;
import com.xingyuv.captcha.model.common.ResponseModel;
import com.xingyuv.captcha.model.vo.CaptchaVO;
import com.xingyuv.captcha.service.CaptchaService;
import jakarta.annotation.Resource;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.keer.yudaovue.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.keer.yudaovue.module.systemApi.enums.ErrorCodeConstants.AUTH_LOGIN_CAPTCHA_CODE_ERROR;

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
  @Resource private Validator validator;
  @Resource private CaptchaService captchaService;

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
    // 插入登录日志
    LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
    reqDTO.setLogType(logTypeEnum.getType());
    reqDTO.setTraceId(TracerUtils.getTraceId());
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
    log.info("login( {} )", reqVo.toString());
    // 校验验证码
    validateCaptcha(reqVo);

    // 使用账号密码，进行登录
    AdminUserDO user = authenticate(reqVo.getUsername(), reqVo.getPassword());

    return null;
  }

  @VisibleForTesting
  void validateCaptcha(AuthLoginReqVO reqVo) {
    log.info("validateCaptcha( {} )", reqVo);
    // 如果验证码关闭，则不进行校验
    if (!captchaEnable) return;
    // 校验验证码
    ValidationUtils.validate(validator, reqVo, AuthLoginReqVO.CodeEnableGroup.class);
    CaptchaVO captchaVO = new CaptchaVO();
    captchaVO.setCaptchaVerification(reqVo.getCaptchaVerification());
    ResponseModel response = captchaService.verification(captchaVO);
    // 验证不通过
    if (!response.isSuccess()) {
      // 创建登录失败日志（验证码不正确)
      log.info("validateCaptcha( {} ) 验证码不通过", reqVo);
      createLoginLog(
          null,
          reqVo.getUsername(),
          LoginLogTypeEnum.LOGIN_USERNAME,
          LoginResultEnum.CAPTCHA_CODE_ERROR);
      throw exception(AUTH_LOGIN_CAPTCHA_CODE_ERROR, response.getRepMsg());
    }
  }

  private UserTypeEnum getUserType() {
    return UserTypeEnum.ADMIN;
  }
}
