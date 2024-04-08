package com.keer.yudaovue.module.systemBiz.controller;

import cn.hutool.core.util.StrUtil;
import com.keer.yudaovue.framework.common.util.servlet.ServletUtils;
import com.xingyuv.captcha.model.common.ResponseModel;
import com.xingyuv.captcha.model.vo.CaptchaVO;
import com.xingyuv.captcha.service.CaptchaService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author keer
 * @date 2024-04-07
 */
@Slf4j(topic = ">>> CaptchaController")
@RestController("adminCaptchaController")
@RequestMapping("/system/captcha")
public class CaptchaController {
  @Resource private CaptchaService captchaService;

  @PostMapping("/get")
  public ResponseModel get(@RequestBody CaptchaVO data, HttpServletRequest request) {
    log.info("get");
    assert request.getRemoteHost() != null;
    data.setBrowserInfo(getRemoteId(request));
    return captchaService.get(data);
  }

  @PostMapping("/check")
  public ResponseModel check(@RequestBody CaptchaVO data, HttpServletRequest request) {
    log.info("check");
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
