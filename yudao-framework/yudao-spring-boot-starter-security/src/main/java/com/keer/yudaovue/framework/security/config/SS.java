package com.keer.yudaovue.framework.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author keer
 * @date 2024-06-02
 */
@Slf4j
@Configuration
public class SS {
  public SS() {
    log.info("vvoos1");
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
        .httpBasic(Customizer.withDefaults());
    return http.build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {

    return (web) -> web.ignoring().requestMatchers("/admin-api/system/captcha/t2", "/admin-api/system/captcha/t1");
  }
}
