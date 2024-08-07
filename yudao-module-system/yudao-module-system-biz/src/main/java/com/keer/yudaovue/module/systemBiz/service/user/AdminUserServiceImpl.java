package com.keer.yudaovue.module.systemBiz.service.user;

import com.keer.yudaovue.module.systemBiz.dal.dataobject.user.AdminUserDO;
import com.keer.yudaovue.module.systemBiz.dal.mysql.user.AdminUserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author keer
 * @date 2024-04-20
 */
@Service("adminUserService")
@Slf4j(topic = ">>> AdminUserServiceImpl")
public class AdminUserServiceImpl implements AdminUserService {
  @Resource private AdminUserMapper userMapper;
  @Resource private PasswordEncoder passwordEncoder;

  @Override
  public AdminUserDO getUserByUsername(String username) {
    return userMapper.selectByUsername(username);
  }

  @Override
  public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
    return passwordEncoder.matches(rawPassword, encodedPassword);
  }

  @Override
  public void updateUserLogin(Long id, String loginIp) {
    userMapper.updateById(
        new AdminUserDO().setId(id).setLoginIp(loginIp).setLoginDate(LocalDateTime.now()));
  }

  @Override
  public AdminUserDO getUser(Long id) {
    return userMapper.selectById(id);
  }
}
