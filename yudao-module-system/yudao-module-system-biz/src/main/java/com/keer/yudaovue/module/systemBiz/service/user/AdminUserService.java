package com.keer.yudaovue.module.systemBiz.service.user;

import com.keer.yudaovue.module.systemBiz.dal.dataobject.user.AdminUserDO;

/**
 * 后台用户 Service 接口
 *
 * @author keer
 * @date 2024-04-20
 */
public interface AdminUserService {
  /**
   * 通过用户名查询用户
   *
   * @param username
   * @return
   */
  AdminUserDO getUserByUsername(String username);

  /**
   * 判断密码是否匹配
   *
   * @param rawPassword 未加密的密码
   * @param encodedPassword 加密后的密码
   * @return
   */
  boolean isPasswordMatch(String rawPassword, String encodedPassword);
}
