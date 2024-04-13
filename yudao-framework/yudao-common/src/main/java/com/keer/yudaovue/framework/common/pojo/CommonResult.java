package com.keer.yudaovue.framework.common.pojo;

import com.keer.yudaovue.framework.common.exception.enums.GlobalErrorCodeConstants;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回
 *
 * @param <T>
 */
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
}
