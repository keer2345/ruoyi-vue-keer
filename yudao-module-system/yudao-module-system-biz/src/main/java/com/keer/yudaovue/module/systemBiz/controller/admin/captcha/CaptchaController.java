package com.keer.yudaovue.module.systemBiz.controller.admin.captcha;

import cn.hutool.core.util.StrUtil;
import com.keer.yudaovue.framework.common.pojo.CommonResult;
import com.keer.yudaovue.framework.common.util.servlet.ServletUtils;
import com.xingyuv.captcha.model.common.ResponseModel;
import com.xingyuv.captcha.model.vo.CaptchaVO;
import com.xingyuv.captcha.service.CaptchaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author keer
 * @date 2024-04-07
 */
@Tag(name = "管理后台 - 验证码")
@Slf4j(topic = ">>> CaptchaController")
@RestController("adminCaptchaController")
@RequestMapping("/system/captcha")
public class CaptchaController {
  @Resource private CaptchaService captchaService;

  @PostMapping("/get")
  @Operation(summary = "获取验证码")
  @PermitAll
  public ResponseModel get(@RequestBody CaptchaVO data, HttpServletRequest request) {
    assert request.getRemoteHost() != null;
    data.setBrowserInfo(getRemoteId(request));
    return captchaService.get(data);
  }

  @PostMapping("/check")
  @Operation(summary = "校验验证码")
  @PermitAll
  public ResponseModel check(@RequestBody CaptchaVO data, HttpServletRequest request) {
    log.info("校验验证码");
    data.setBrowserInfo(getRemoteId(request));
    return captchaService.check(data);
  }

  public static String getRemoteId(HttpServletRequest request) {
    String ip = ServletUtils.getClientIP(request);
    String ua = request.getHeader("user-agent");
    if (StrUtil.isNotBlank(ip)) {
      return ip + ua;
    }
    return request.getRemoteAddr() + ua;
  }
}
