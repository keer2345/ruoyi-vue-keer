package com.keer.yudaovue.framework.mybatis.core.type;

import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.keer.yudaovue.framework.common.util.json.JsonUtils;

import java.util.Set;

/**
 * 参考 {@link com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler} 实现
 *
 * <p>在我们将字符串反序列化为 Set 并且泛型为 Long 时，如果每个元素的数值太小，会被处理成 Integer 类型，导致可能存在隐性的 BUG。
 *
 * <p>例如说哦，SysUserDO 的 postIds 属性
 *
 * @author keer
 * @date 2024-04-20
 */
public class JsonLongSetTypeHandler extends AbstractJsonTypeHandler<Object> {

  private static final TypeReference<Set<Long>> TYPE_REFERENCE = new TypeReference<Set<Long>>() {};

  @Override
  protected Object parse(String json) {
    // return JsonUtils.parseObject(json, TYPE_REFERENCE);
    return null;
  }

  @Override
  protected String toJson(Object obj) {
    return null;
  }
}
