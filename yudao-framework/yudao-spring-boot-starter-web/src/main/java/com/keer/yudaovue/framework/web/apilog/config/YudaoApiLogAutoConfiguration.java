package com.keer.yudaovue.framework.web.apilog.config;

import com.keer.yudaovue.framework.web.apilog.core.service.ApiErrorLogFrameworkService;
import com.keer.yudaovue.framework.web.apilog.core.service.ApiErrorLogFrameworkServiceImpl;
import com.keer.yudaovue.framework.web.config.YudaoWebAutoConfiguration;
import com.keer.yudaovue.module.infraApi.api.logger.dto.ApiErrorLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author keer
 * @date 2024-04-21
 */
@AutoConfiguration(after = YudaoWebAutoConfiguration.class)
public class YudaoApiLogAutoConfiguration implements WebMvcConfigurer {

  @Bean
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  public ApiErrorLogFrameworkService apiErrorLogFrameworkService(ApiErrorLogApi apiErrorLogApi) {
    return new ApiErrorLogFrameworkServiceImpl(apiErrorLogApi);
  }
}
