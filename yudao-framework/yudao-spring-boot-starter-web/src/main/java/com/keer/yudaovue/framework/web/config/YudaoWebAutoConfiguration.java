package com.keer.yudaovue.framework.web.config;

import com.keer.yudaovue.framework.common.enums.WebFilterOrderEnum;
import com.keer.yudaovue.framework.web.apilog.core.service.ApiErrorLogFrameworkService;
import com.keer.yudaovue.framework.web.core.handler.GlobalExceptionHandler;
import jakarta.annotation.Resource;
import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
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
  @Resource private WebProperties webProperties;

  /** 应用名 */
  @Value("${spring.application.name}")
  private String applicationName;

  public YudaoWebAutoConfiguration() {
    log.info("App Name: {}", applicationName);
  }

  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    // configurer.addPathPrefix("/admin-api", c -> c.isAnnotationPresent(RestController.class));
    configurePathMatch(configurer, webProperties.getAdminApi());
    configurePathMatch(configurer, webProperties.getAppApi());
  }

  /**
   * 设置 API 前缀，仅仅匹配 controller 包下的
   *
   * @param configurer
   * @param api
   */
  private void configurePathMatch(PathMatchConfigurer configurer, WebProperties.Api api) {
    AntPathMatcher antPathMatcher = new AntPathMatcher(".");
    configurer.addPathPrefix(
        api.getPrefix(),
        clazz ->
            clazz.isAnnotationPresent(RestController.class)
                && antPathMatcher.match(
                    api.getController(), clazz.getPackage().getName())); // 仅仅匹配 controller 包
  }


  @Bean
  public GlobalExceptionHandler globalExceptionHandler(
      ApiErrorLogFrameworkService apiErrorLogFrameworkService) {
    return new GlobalExceptionHandler(applicationName, apiErrorLogFrameworkService);
  }

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
