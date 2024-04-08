package com.keer.yudaovue.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author keer
 * @date 2024-04-06
 */
// todo
@SpringBootApplication(scanBasePackages = {"${yudao.info.base-package}.module"})
public class YudaoServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(YudaoServerApplication.class, args);
  }
}
