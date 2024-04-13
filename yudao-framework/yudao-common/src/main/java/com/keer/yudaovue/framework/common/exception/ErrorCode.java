package com.keer.yudaovue.framework.common.exception;

import com.keer.yudaovue.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.keer.yudaovue.framework.common.exception.enums.ServiceErrorCodeRange;
import lombok.Data;

/**
 * 错误码对象
 *
 * <p>全局错误码，占用 [1000, 9999], 参见 {@link GlobalErrorCodeConstants}
 *
 * <p>业务异常错误码，占用 [1 000 000 000, +∞)，参见 {@link ServiceErrorCodeRange}
 *
 * <p>TODO 错误码设计成对象的原因，为未来的 i18 国际化做准备
 *
 * @author keer
 * @date 2024-04-13
 */
@Data
public class ErrorCode {
    private final Integer code;
    private final String msg;
}
