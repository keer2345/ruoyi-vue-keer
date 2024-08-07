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
import com.keer.yudaovue.module.systemApi.enums.oauth2.OAuth2ClientConstants;
import com.keer.yudaovue.module.systemBiz.controller.admin.auth.vo.AuthLoginReqVO;
import com.keer.yudaovue.module.systemBiz.controller.admin.auth.vo.AuthLoginRespVO;
import com.keer.yudaovue.module.systemBiz.convert.auth.AuthConvert;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.user.AdminUserDO;
import com.keer.yudaovue.module.systemBiz.service.logger.LoginLogService;
import com.keer.yudaovue.module.systemBiz.service.oauth2.OAuth2TokenService;
import com.keer.yudaovue.module.systemBiz.service.user.AdminUserService;
import com.xingyuv.captcha.model.common.ResponseModel;
import com.xingyuv.captcha.model.vo.CaptchaVO;
import com.xingyuv.captcha.service.CaptchaService;
import jakarta.annotation.Resource;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.keer.yudaovue.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.keer.yudaovue.module.systemApi.enums.ErrorCodeConstants.*;

/**
 * Auth Service 实现类
 *
 * @author keer
 * @date 2024-04-18
 */
@Service
@Slf4j(topic = ">>> AdminAuthServiceImpl")
public class AdminAuthServiceImpl implements AdminAuthService {
  // todo
  @Resource private AdminUserService userService;
  @Resource private LoginLogService loginLogService;
  @Resource private OAuth2TokenService oAuth2TokenService;
  @Resource private Validator validator;
  @Resource private CaptchaService captchaService;

  /** 验证码的开关，默认为 true */
  @Value("${yudao.captcha.enable:true}")
  private Boolean captchaEnable;

  private AdminUserDO authenticate(String username, String password) {
    final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
    // 判断用户是否存在
    AdminUserDO user = userService.getUserByUsername(username);
    if (ObjUtil.isNull(user)) {
      // 记录日志
      createLoginLog(null, username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
      // 抛出异常
      throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
    }
    // 校验密码
    if (!userService.isPasswordMatch(password, user.getPassword())) {
      // 记录日志
      createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
      // 抛出异常
      throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
    }

    // 校验是否禁用
    if (CommonStatusEnum.isDisable(user.getStatus())) {
      // 记录日志
      createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.USER_DISABLED);
      // 抛出异常
      throw exception(AUTH_LOGIN_USER_DISABLED);
    }

    return user;
  }

  @Override
  public AuthLoginRespVO login(AuthLoginReqVO reqVo) {
    log.info("login( {} )", reqVo.toString());
    // 校验验证码
    validateCaptcha(reqVo);

    // 使用账号密码，进行登录
    AdminUserDO user = authenticate(reqVo.getUsername(), reqVo.getPassword());

    // 如果 socialType 非空，说明需要绑定社交用户
    if (ObjUtil.isNotNull(reqVo.getSocialType())) {
      // todo
    }

    // 创建 Token 令牌，记录登录日志
    return createTokenAfterLoginSuccess(
        user.getId(), reqVo.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME);
  }

  @Override
  public void logout(String token, Integer logoutType) {
    // 删除访问令牌
    OAuth2AccessTokenDO accessTokenDO = oAuth2TokenService.removeAccessToken(token);

    if (ObjUtil.isNull(accessTokenDO)) {
      return;
    }
    // 删除成功，则记录登出日志
    createLogoutLog(accessTokenDO.getUserId(), accessTokenDO.getUserType(), logoutType);
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
        && NumberUtil.equals(LoginResultEnum.SUCCESS.getResult(), loginResult.getResult())) {
      log.info("更新最后登录时间");
      userService.updateUserLogin(userId, ServletUtils.getClientIP());
      log.info("更新最后登录时间 end");
    }
  }

  private AuthLoginRespVO createTokenAfterLoginSuccess(
      Long userId, String username, LoginLogTypeEnum loginType) {
    // 插入登陆日志
    createLoginLog(userId, username, loginType, LoginResultEnum.SUCCESS);
    // 创建访问令牌
    OAuth2AccessTokenDO accessTokenDO =
        oAuth2TokenService.createAccessToken(
            userId, getUserType().getValue(), OAuth2ClientConstants.CLIENT_ID_DEFAULT, null);
    // 构建返回结果
    return AuthConvert.INSTANCE.convert(accessTokenDO);
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
      createLoginLog(
          null,
          reqVo.getUsername(),
          LoginLogTypeEnum.LOGIN_USERNAME,
          LoginResultEnum.CAPTCHA_CODE_ERROR);
      throw exception(AUTH_LOGIN_CAPTCHA_CODE_ERROR, response.getRepMsg());
    }
  }

  private void createLogoutLog(Long userId, Integer userType, Integer logoutType) {
    LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
    reqDTO
        .setLogType(logoutType)
        .setTraceId(TracerUtils.getTraceId())
        .setUserId(userId)
        .setUserType(userType);
    if (ObjUtil.equals(getUserType().getValue(), userType)) {
      reqDTO.setUsername(getUsername(userId));
    } else {
      // todo
    }
    reqDTO
        .setUserAgent(ServletUtils.getUserAgent())
        .setUserIp(ServletUtils.getClientIP())
        .setResult(LoginResultEnum.SUCCESS.getResult());
    loginLogService.createLoginLog(reqDTO);
  }

  private String getUsername(Long userId) {
    if (ObjUtil.isNull(userId)) {
      return null;
    }
    AdminUserDO user = userService.getUser(userId);
    return ObjUtil.isNotNull(user) ? user.getUsername() : null;
  }

  private UserTypeEnum getUserType() {
    return UserTypeEnum.ADMIN;
  }
}
