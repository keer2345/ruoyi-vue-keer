package com.keer.yudaovue.framework.mybatis.core.query;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * 拓展 MyBatis Plus QueryWrapper 类，主要增加如下功能：
 *
 * <p>1. 拼接条件的方法，增加 xxxIfPresent 方法，用于判断值不存在的时候，不要拼接到条件中。
 *
 * @author keer
 * @date 2024-07-04
 */
public class LambdaQueryWrapperX<T> extends LambdaQueryWrapper<T> {
  // todo
  public LambdaQueryWrapperX<T> eqIfPresent(SFunction<T, ?> column, Object val) {
    if (ObjUtil.isNotEmpty(val)) {
      return (LambdaQueryWrapperX<T>) super.eq(column, val);
    }
    return this;
  }
}
