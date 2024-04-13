package com.keer.yudaovue.framework.common.exception.enums;

import cn.hutool.http.HttpStatus;
import com.keer.yudaovue.framework.common.exception.ErrorCode;

/**
 * 一般情况下，使用 HTTP 响应状态码 https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Status
 *
 * <p>虽然说，HTTP响应状态码作为业务使用表达能力偏弱，但是使用在系统层面还是非常不错的
 *
 * @author keer
 * @date 2024-04-13
 */
public interface GlobalErrorCodeConstants {
  ErrorCode SUCCESS = new ErrorCode(HttpStatus.HTTP_OK, "成功");

  // ========== 客户端错误段 ==========

  ErrorCode BAD_REQUEST = new ErrorCode(HttpStatus.HTTP_BAD_REQUEST, "请求参数不正确");
  ErrorCode UNAUTHORIZED = new ErrorCode(HttpStatus.HTTP_UNAUTHORIZED, "账号未登录");
  ErrorCode FORBIDDEN = new ErrorCode(HttpStatus.HTTP_FORBIDDEN, "没有该操作权限");
  ErrorCode NOT_FOUND = new ErrorCode(HttpStatus.HTTP_NOT_FOUND, "请求未找到");
  ErrorCode METHOD_NOT_ALLOWED = new ErrorCode(HttpStatus.HTTP_BAD_METHOD, "请求方法不正确");
  ErrorCode LOCKED = new ErrorCode(HttpStatus.HTTP_LOCKED, "请求失败，请稍后重试"); // 并发请求，不允许
  ErrorCode TOO_MANY_REQUESTS = new ErrorCode(HttpStatus.HTTP_TOO_MANY_REQUESTS, "请求过于频繁，请稍后重试");

  // ========== 服务端错误段 ==========

  ErrorCode INTERNAL_SERVER_ERROR = new ErrorCode(HttpStatus.HTTP_INTERNAL_ERROR, "系统异常");
  ErrorCode NOT_IMPLEMENTED = new ErrorCode(HttpStatus.HTTP_NOT_IMPLEMENTED, "功能未实现/未开启");
  ErrorCode ERROR_CONFIGURATION = new ErrorCode(HttpStatus.HTTP_BAD_GATEWAY, "错误的配置项");

  // ========== 自定义错误段 ==========
  ErrorCode REPEATED_REQUESTS = new ErrorCode(900, "重复请求，请稍后重试"); // 重复请求
  ErrorCode DEMO_DENY = new ErrorCode(901, "演示模式，禁止写操作");

  ErrorCode UNKNOWN = new ErrorCode(999, "未知错误");
}
