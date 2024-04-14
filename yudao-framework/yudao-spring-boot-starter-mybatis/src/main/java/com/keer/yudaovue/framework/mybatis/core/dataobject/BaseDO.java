package com.keer.yudaovue.framework.mybatis.core.dataobject;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体对象
 *
 * <p>为什么实现 {@link TransPojo} 接口？
 *
 * <p>因为使用 Easy-Trans TransType.SIMPLE 模式，集成 MyBatis Plus 查询
 *
 * @author keer
 * @date 2024-04-14
 */
@Data
// @JsonIgnoreProperties(value = "transMap")
// 由于 Easy-Trans 会添加 transMap 属性，避免 Jackson 在 Spring Cache 反序列化报错
public abstract class BaseDO implements Serializable // , TransPojo
{
  /** 创建时间 */
  // @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  /** 最后更新时间 */
  // @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;

  /**
   * 创建者，目前使用 SysUser 的 id 编号
   *
   * <p>使用 String 类型的原因是，未来可能会存在非数值的情况，留好拓展性。
   */
  // @TableField(fill = FieldFill.INSERT, jdbcType = JdbcType.VARCHAR)
  private String creator;

  /**
   * 更新者，目前使用 SysUser 的 id 编号
   *
   * <p>使用 String 类型的原因是，未来可能会存在非数值的情况，留好拓展性。
   */
  // @TableField(fill = FieldFill.INSERT_UPDATE, jdbcType = JdbcType.VARCHAR)
  private String updater;

  /** 是否删除 */
  // @TableLogic
  private Boolean deleted;
}