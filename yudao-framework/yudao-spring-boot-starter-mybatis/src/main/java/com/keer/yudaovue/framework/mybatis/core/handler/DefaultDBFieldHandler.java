package com.keer.yudaovue.framework.mybatis.core.handler;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.keer.yudaovue.framework.mybatis.core.dataobject.BaseDO;
import com.keer.yudaovue.framework.web.core.util.WebFrameworkUtils;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 通用参数填充实现类
 *
 * <p>如果没有显式的对通用参数进行赋值，这里会对通用参数进行填充、赋值
 *
 * @author keer
 * @date 2024-04-20
 */
public class DefaultDBFieldHandler implements MetaObjectHandler {
  @Override
  public void insertFill(MetaObject metaObject) {
    if (ObjUtil.isNotNull(metaObject) && metaObject.getOriginalObject() instanceof BaseDO) {
      BaseDO baseDO = (BaseDO) metaObject.getOriginalObject();

      LocalDateTime current = LocalDateTime.now();
      // 创建时间为空，则以当前时间为插入时间
      if (Objects.isNull(baseDO.getCreateTime())) {
        baseDO.setCreateTime(current);
      }
      // 更新时间为空，则以当前时间为更新时间
      if (Objects.isNull(baseDO.getUpdateTime())) {
        baseDO.setUpdateTime(current);
      }

      Long userId = WebFrameworkUtils.getLoginUserId();
      // 当前登录用户不为空，创建人为空，则当前登录用户为创建人
      if (Objects.nonNull(userId) && Objects.isNull(baseDO.getCreator())) {
        baseDO.setCreator(userId.toString());
      }
      // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
      if (Objects.nonNull(userId) && Objects.isNull(baseDO.getUpdater())) {
        baseDO.setUpdater(userId.toString());
      }
    }
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    // 更新时间为空，则以当前时间为更新时间
    Object modifyTime = getFieldValByName("updateTime", metaObject);
    if (Objects.isNull(modifyTime)) {
      setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }

    // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
    Object modifier = getFieldValByName("updater", metaObject);
    Long userId = WebFrameworkUtils.getLoginUserId();
    if (Objects.nonNull(userId) && Objects.isNull(modifier)) {
      setFieldValByName("updater", userId.toString(), metaObject);
    }
  }
}
