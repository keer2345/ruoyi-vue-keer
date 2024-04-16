package com.keer.yudaovue.framework.mybatis.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;

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
public class YudaoMybatisAutoConfiguration {}
