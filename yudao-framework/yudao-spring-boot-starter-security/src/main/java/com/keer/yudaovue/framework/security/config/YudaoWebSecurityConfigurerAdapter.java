package com.keer.yudaovue.framework.security.config;

import com.keer.yudaovue.framework.security.core.filter.TokenAuthenticationFilter;
import com.keer.yudaovue.framework.security.core.handler.AuthenticationEntryPointImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author keer
 * @date 2024-06-02
 */
@Slf4j
@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class YudaoWebSecurityConfigurerAdapter {

  /** 认证失败处理类 Bean */
  @Resource private AuthenticationEntryPoint authenticationEntryPoint;

  /** 权限不够处理器 Bean */
  @Resource
  private AccessDeniedHandler accessDeniedHandler;

  /**
   * Token 认证过滤器 Bean
   */
  @Resource
  private TokenAuthenticationFilter authenticationTokenFilter;

  /**
   * 由于 Spring Security 创建 AuthenticationManager 对象时，没声明 @Bean 注解，导致无法被注入 通过覆写父类的该方法，添加 @Bean
   * 注解，解决该问题
   */
  @Bean
  public AuthenticationManager authenticationManagerBean(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    log.info("vsvs...vsvs");
    // 登出
    http
        // 开启跨域
        .cors(Customizer.withDefaults())
        // CSRF 禁用，因为不使用 Session
        .csrf(AbstractHttpConfigurer::disable)
        // 基于 token 机制，所以不需要 Session
        .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .headers(c -> c.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
        // 一堆自定义的 Spring Security 处理器
        .exceptionHandling(
            c -> c.authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler)
            );

    // 登录、登录暂时不使用 Spring Security 的拓展点，主要考虑一方面拓展多用户、多种登录方式相对复杂，一方面用户的学习成本较高

    http.authorizeHttpRequests(
            r ->
                r
                    // 1.1 静态资源，可匿名访问
                    .requestMatchers(HttpMethod.GET, "/*.html", "/*.css", "/*.js")
                    .permitAll()
                    // 1.2 Knife4j
                    .requestMatchers(
                        HttpMethod.GET,
                        "/doc.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v3/**",
                        "/admin-api/system/captcha/t2")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/admin-api/system/captcha/get")
                    .permitAll()
            // .anyRequest()
            // .authenticated()
            )
        .authorizeHttpRequests(c -> c.anyRequest().authenticated());

    // 添加 Token Filter
    http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
