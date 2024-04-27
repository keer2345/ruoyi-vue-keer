package com.keer.yudaovue.framework.common.pojo;

import com.keer.yudaovue.framework.common.exception.ErrorCode;
import com.keer.yudaovue.framework.common.exception.enums.GlobalErrorCodeConstants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * 通用返回
 *
 * @param <T>
 */
@Slf4j(topic = ">>> CommonResult")
@Data
public class CommonResult<T> implements Serializable {
  /** 状态码 */
  private Integer code;

  /** 数据 */
  private T data;

  /** 错误提示 */
  private String msg;

  public static <T> CommonResult<T> success(T data) {
    CommonResult<T> result = new CommonResult<>();
    result.code = GlobalErrorCodeConstants.SUCCESS.getCode();
    result.data = data;
    result.msg = "";
    return result;
  }

  public static <T> CommonResult<T> error(Integer code, String message) {
    Assert.isTrue(!GlobalErrorCodeConstants.SUCCESS.getCode().equals(code), "code 必须是错误的！");
    CommonResult<T> result = new CommonResult<>();
    result.code = code;
    result.msg = message;
    return result;
  }

  public static <T> CommonResult<T> error(ErrorCode errorCode) {
    return error(errorCode.getCode(), errorCode.getMsg());
  }
}
