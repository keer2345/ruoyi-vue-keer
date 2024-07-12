package com.keer.yudaovue.module.systemBiz.convert.auth;

import static com.keer.yudaovue.framework.common.util.ollection.CollectionUtils.convertSet;
import static com.keer.yudaovue.framework.common.util.ollection.CollectionUtils.filterList;
import static com.keer.yudaovue.module.systemBiz.dal.dataobject.permission.MenuDO.ID_ROOT;

import cn.hutool.core.collection.CollUtil;
import com.keer.yudaovue.framework.common.util.object.BeanUtils;
import com.keer.yudaovue.module.systemApi.enums.permission.MenuTypeEnum;
import com.keer.yudaovue.module.systemBiz.controller.admin.auth.vo.AuthLoginRespVO;
import com.keer.yudaovue.module.systemBiz.controller.admin.auth.vo.AuthPermissionInfoRespVO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.permission.MenuDO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.permission.RoleDO;
import com.keer.yudaovue.module.systemBiz.dal.dataobject.user.AdminUserDO;

import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.LoggerFactory;

/**
 * @author keer
 * @date 2024-06-23
 */
@Mapper
public interface AuthConvert {
  // todo

  AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

  AuthLoginRespVO convert(OAuth2AccessTokenDO bean);

  default AuthPermissionInfoRespVO convert(
      AdminUserDO user, List<RoleDO> roleList, List<MenuDO> menuList) {
    return AuthPermissionInfoRespVO.builder()
        .user(BeanUtils.toBean(user, AuthPermissionInfoRespVO.UserVO.class))
        .roles(convertSet(roleList, RoleDO::getCode))
        // 权限标识信息
        .permissions(convertSet(menuList, MenuDO::getPermission))
        // 菜单树
        .menus(buildMenuTree(menuList))
        .build();
  }

  /**
   * 将菜单列表，构建成菜单树
   *
   * @param menuList
   * @return
   */
  default List<AuthPermissionInfoRespVO.MenuVO> buildMenuTree(List<MenuDO> menuList) {
    if (CollUtil.isEmpty(menuList)) {
      return Collections.emptyList();
    }
    // 移除按钮
    menuList.removeIf(menu -> menu.getType().equals(MenuTypeEnum.BUTTON.getType()));
    // 排序，保证菜单的有序性
    menuList.sort(Comparator.comparing(MenuDO::getSort));

    // 构建菜单树
    // 使用 LinkedHashMap 的原因，是为了排序 。实际也可以用 Stream API ，就是太丑了。
    Map<Long, AuthPermissionInfoRespVO.MenuVO> treeNodeMap = new LinkedHashMap<>();
    menuList.forEach(
        menu -> treeNodeMap.put(menu.getId(), AuthConvert.INSTANCE.convertTreeNode(menu)));
    // 处理父子关系
    treeNodeMap.values().stream()
        .filter(node -> !node.getParentId().equals(ID_ROOT))
        .forEach(
            childNode -> {
              // 获得父节点
              AuthPermissionInfoRespVO.MenuVO parentNode = treeNodeMap.get(childNode.getParentId());
              if (parentNode == null) {
                LoggerFactory.getLogger(getClass())
                    .error(
                        "[buildRouterTree][resource({}) 找不到父资源({})]",
                        childNode.getId(),
                        childNode.getParentId());
                return;
              }
              // 将自己添加到父节点中
              if (parentNode.getChildren() == null) {
                parentNode.setChildren(new ArrayList<>());
              }
              parentNode.getChildren().add(childNode);
            });
    // 获得到所有的根节点
      return filterList(treeNodeMap.values(), node -> ID_ROOT.equals(node.getParentId()));
  }

  AuthPermissionInfoRespVO.MenuVO convertTreeNode(MenuDO menu);

  // todo

}
