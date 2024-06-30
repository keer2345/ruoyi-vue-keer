package com.keer.yudaovue.framework.security.core.service;

import com.keer.yudaovue.module.systemApi.api.permission.PermissionApi;
import lombok.AllArgsConstructor;

/**
 * 默认的 {@link SecurityFrameworkService} 实现类
 * @author keer
 * @date 2024-06-30
 */
@AllArgsConstructor
public class SecurityFrameworkServiceImpl implements SecurityFrameworkService{
    // todo
    private final PermissionApi permissionApi;
}
