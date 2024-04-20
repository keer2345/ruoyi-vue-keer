package com.keer.yudaovue.framework.common.util.validation;

import cn.hutool.core.collection.CollUtil;
import jakarta.validation.ConstraintDeclarationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;

/**
 * @author keer
 * @date 2024-04-20
 */
public class ValidationUtils {
  public static void validate(Validator validator, Object object, Class<?>... groups) {
    Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
    if (CollUtil.isNotEmpty(constraintViolations)) {
      throw new ConstraintViolationException(constraintViolations);
    }
  }
}
