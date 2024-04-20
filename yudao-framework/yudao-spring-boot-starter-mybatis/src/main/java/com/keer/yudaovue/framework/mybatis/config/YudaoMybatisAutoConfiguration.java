package com.keer.yudaovue.framework.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.keer.yudaovue.framework.mybatis.core.handler.DefaultDBFieldHandler;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * MyBaits 配置类
 *
 * @author keer
 * @date 2024-04-16
 */
@AutoConfiguration
@MapperScan(
    value = "${yudao.info.base-package}",
    annotationClass = Mapper.class,
    lazyInitialization = "${mybatis.lazy-initialization:false}") // Mapper 懒加载，目前仅用于单元测试
public class YudaoMybatisAutoConfiguration {

  @Bean
  public MetaObjectHandler defaultMetaObjectHandler() {
    return new DefaultDBFieldHandler(); // 自动填充参数类
  }
}
