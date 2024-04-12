package com.keer.yudaovue.framework.web.config;

import com.keer.yudaovue.framework.common.enums.WebFilterOrderEnum;
import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author keer
 * @date 2024-04-12
 */
@Slf4j(topic = ">>> YudaoWebAutoConfiguration")
@AutoConfiguration
@EnableConfigurationProperties(WebProperties.class)
public class YudaoWebAutoConfiguration implements WebMvcConfigurer {

  // todo

  // ========== Filter 相关 ==========

  /** 创建 CorsFilter Bean，解决跨域问题 */
  @Bean
  public FilterRegistrationBean<CorsFilter> corsFilterBean() {
    log.info("corsFilterBean");
    // 创建 CorsConfiguration 对象
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOriginPattern("*"); // 设置访问源地址
    config.addAllowedHeader("*"); // 设置访问源请求头
    config.addAllowedMethod("*"); // 设置访问源请求方法
    // 创建 UrlBasedCorsConfigurationSource 对象
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config); // 对接口配置跨域设置
    return createFilterBean(new CorsFilter(source), WebFilterOrderEnum.CORS_FILTER);
  }

  public static <T extends Filter> FilterRegistrationBean<T> createFilterBean(
      T filter, Integer order) {
    FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
    bean.setOrder(order);
    return bean;
  }
}
