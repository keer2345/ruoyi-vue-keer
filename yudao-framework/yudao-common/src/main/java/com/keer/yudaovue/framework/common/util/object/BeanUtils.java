package com.keer.yudaovue.framework.common.util.object;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import com.keer.yudaovue.framework.common.util.ollection.CollectionUtils;

import java.util.List;

/**
 * Bean 工具类
 *
 * <p>1. 默认使用 {@link cn.hutool.core.bean.BeanUtil} 作为实现类，虽然不同 bean 工具的性能有差别，但是对绝大多数同学的项目，不用在意这点性能
 *
 * <p>2. 针对复杂的对象转换，可以搜参考 AuthConvert 实现，通过 mapstruct + default 配合实现
 *
 * @author keer
 * @date 2024-04-14
 */
public class BeanUtils {
  // todo

  public static <T> T toBean(Object source, Class<T> targetClass) {
    return BeanUtil.toBean(source, targetClass);
  }

  public static <S, T> List<T> toBean(List<S> source, Class<T> targetType) {
    if (ObjUtil.isNull(source)) {
      return null;
    }
    return CollectionUtils.convertList(source, s -> toBean(s, targetType));
  }
}
