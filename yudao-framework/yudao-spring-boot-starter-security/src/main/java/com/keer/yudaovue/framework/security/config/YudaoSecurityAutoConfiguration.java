package com.keer.yudaovue.framework.security.config;

import com.keer.yudaovue.framework.security.core.aop.PreAuthenticatedAspect;
import com.keer.yudaovue.framework.security.core.filter.TokenAuthenticationFilter;
import com.keer.yudaovue.framework.security.core.handler.AccessDeniedHandlerImpl;
import com.keer.yudaovue.framework.security.core.handler.AuthenticationEntryPointImpl;
import com.keer.yudaovue.framework.web.core.handler.GlobalExceptionHandler;
import com.keer.yudaovue.module.systemApi.api.OAuth2TokenApi;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * Spring Security 自动配置类，主要用于相关组件的配置
 *
 * <p>注意，不能和 {@link YudaoWebSecurityConfigurerAdapter} 用一个，原因是会导致初始化报错。
 *
 * <p>参见
 * https://stackoverflow.com/questions/53847050/spring-boot-delegatebuilder-cannot-be-null-on-autowiring-authenticationmanager
 * 文档。
 *
 * @author keer
 * @date 2024-05-12
 */
@AutoConfiguration
@EnableConfigurationProperties(SecurityProperties.class)
public class YudaoSecurityAutoConfiguration {
  // todo
  @Resource private SecurityProperties securityProperties;

  /**
   * 处理用户未登录拦截的切面的 Bean
   *
   * @return
   */
  @Bean
  public PreAuthenticatedAspect preAuthenticatedAspect() {
    return new PreAuthenticatedAspect();
  }

  /** 认证失败处理类 Bean */
  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return new AuthenticationEntryPointImpl();
  }

  /** 权限不够处理器 Bean */
  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return new AccessDeniedHandlerImpl();
  }

  /**
   * Spring Security 加密器 考虑到安全性，这里采用 BCryptPasswordEncoder 加密器
   *
   * @see <a href="http://stackabuse.com/password-encoding-with-spring-security/">Password Encoding
   *     with Spring Security</a>
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    // return new BCryptPasswordEncoder();
    return new BCryptPasswordEncoder(securityProperties.getPasswordEncoderLength());
  }

  /** Token 认证过滤器 Bean */
  @Bean
  public TokenAuthenticationFilter authenticationTokenFilter(
      GlobalExceptionHandler globalExceptionHandler, OAuth2TokenApi oauth2TokenApi) {
    return new TokenAuthenticationFilter(
        securityProperties, globalExceptionHandler, oauth2TokenApi);
  }
}
