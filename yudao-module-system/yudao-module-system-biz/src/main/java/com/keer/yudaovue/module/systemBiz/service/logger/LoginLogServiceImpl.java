package com.keer.yudaovue.module.systemBiz.service.logger;

import com.keer.yudaovue.framework.common.util.object.BeanUtils;
import com.keer.yudaovue.module.systemApi.api.logger.dto.LoginLogCreateReqDTO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.logger.LoginLogDO;
import com.keer.yudaovue.module.systemBiz.dal.mysql.logger.LoginLogMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * @author keer
 * @date 2024-04-19
 */
@Service
@Validated
public class LoginLogServiceImpl implements LoginLogService {
  @Resource private LoginLogMapper loginLogMapper;

  @Override
  public void createLoginLog(LoginLogCreateReqDTO reqDTO) {
    LoginLogDO loginLog = BeanUtils.toBean(reqDTO, LoginLogDO.class);
    loginLogMapper.insert(loginLog);
  }
}
