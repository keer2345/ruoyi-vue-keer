package com.keer.yudaovue.framework.common.validation;

import com.keer.yudaovue.framework.common.core.IntArrayValuable;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author keer
 * @date 2024-04-17
 */
@Target({
  ElementType.METHOD,
  ElementType.FIELD,
  ElementType.ANNOTATION_TYPE,
  ElementType.CONSTRUCTOR,
  ElementType.PARAMETER,
  ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {InEnumValidator.class, InEnumCollectionValidator.class})
public @interface InEnum {
  /**
   * @return 实现 EnumValuable 接口的
   */
  Class<? extends IntArrayValuable> value();

  String message() default "必须在指定范围 {value}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
