package com.keer.yudaovue.framework.mybatis.core.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.github.yulichang.base.MPJBaseMapper;

import java.util.List;

/**
 * 在 MyBatis Plus 的 BaseMapper 的基础上拓展，提供更多的能力
 *
 * <p>1. {@link BaseMapper} 为 MyBatis Plus 的基础接口，提供基础的 CRUD 能力
 *
 * <p>2. {@link MPJBaseMapper} 为 MyBatis Plus Join 的基础接口，提供连表 Join 能力
 *
 * @author keer
 * @date 2024-04-16
 */
public interface BaseMapperX<T> extends MPJBaseMapper<T> {
  default T selectOne(SFunction<T, ?> field, Object value) {
    return selectOne(new LambdaQueryWrapper<T>().eq(field, value));
  }

  default List<T> selectList(SFunction<T, ?> field, Object value) {
    return selectList(new LambdaQueryWrapper<T>().eq(field, value));
  }

  default List<T> selectList() {
    return selectList(new QueryWrapper<>());
  }
}
