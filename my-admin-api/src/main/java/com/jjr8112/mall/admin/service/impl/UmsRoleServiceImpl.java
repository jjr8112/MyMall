package com.jjr8112.mall.admin.service.impl;


import com.github.pagehelper.PageHelper;
import com.jjr8112.mall.admin.dao.UmsRoleDao;
import com.jjr8112.mall.admin.service.UmsAdminCacheService;
import com.jjr8112.mall.admin.service.UmsRoleService;
import com.jjr8112.mall.mbg.mapper.UmsRoleMapper;
import com.jjr8112.mall.mbg.mapper.UmsRoleMenuRelationMapper;
import com.jjr8112.mall.mbg.mapper.UmsRoleResourceRelationMapper;
import com.jjr8112.mall.mbg.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 后台角色管理Service实现类
 */
@Service
public class UmsRoleServiceImpl implements UmsRoleService {
    @Autowired
    private UmsRoleMapper roleMapper;
    @Autowired
    private UmsRoleMenuRelationMapper roleMenuRelationMapper;
    @Autowired
    private UmsRoleResourceRelationMapper roleResourceRelationMapper;
    @Autowired
    private UmsRoleDao roleDao;
    @Autowired
    private UmsAdminCacheService adminCacheService;

    /**
     * 添加角色
     * @param role
     * @return
     */
    @Override
    public int create(UmsRole role) {
        role.setCreateTime(new Date());
        role.setAdminCount(0);
        role.setSort(0);
        return roleMapper.insert(role);
    }

    /**
     * 修改角色信息
     * @param id
     * @param role
     * @return
     */
    @Override
    public int update(Long id, UmsRole role) {
        role.setId(id);
        return roleMapper.updateByPrimaryKeySelective(role);
    }


    /**
     * 批量删除角色
     * @param ids
     * @return
     */
    @Override
    public int delete(List<Long> ids) {
        UmsRoleExample example = new UmsRoleExample();
        example.createCriteria().andIdIn(ids);      // 角色 id在传入参数范围内的将被批量删除
        int count = roleMapper.deleteByExample(example);
        adminCacheService.delResourceListByRoleIds(ids);
        return count;
    }

    /**
     * 获取全部角色
     * @return
     */
    @Override
    public List<UmsRole> list() {
        return roleMapper.selectByExample(new UmsRoleExample());    // 根据一个 umsRole实例查询所有角色
    }

    /**
     * 分页获取角色
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    @Override
    public List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        UmsRoleExample example = new UmsRoleExample();
        if (!StringUtils.isEmpty(keyword)) {
            example.createCriteria().andNameLike("%" + keyword + "%");  // 模糊查询
        }
        return roleMapper.selectByExample(example);
    }

    /**
     * 根据管理员ID获取对应菜单
     * 由用户管理的getAdminInfo(获取当前登录用户信息)调用
     * @param adminId
     * @return
     */
    // 用于 UmsAdminController
    @Override
    public List<UmsMenu> getMenuList(Long adminId) {
        return roleDao.getMenuList(adminId);
    }

    /**
     * 获取角色相关菜单
     * @param roleId
     * @return
     */
    @Override
    public List<UmsMenu> listMenu(Long roleId) {
        return roleDao.getMenuListByRoleId(roleId);
    }

    /**
     * 获取角色相关资源
     * @param roleId
     * @return
     */
    @Override
    public List<UmsResource> listResource(Long roleId) {
        return roleDao.getResourceListByRoleId(roleId);
    }

    /**
     * 给角色分配菜单
     * @param roleId
     * @param menuIds
     * @return
     */
    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        //先删除原有关系
        UmsRoleMenuRelationExample example=new UmsRoleMenuRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);  // 获取指定 id的角色
        roleMenuRelationMapper.deleteByExample(example);    // 删除所有该角色的关联菜单
        //批量插入新关系
        for (Long menuId : menuIds) {
            UmsRoleMenuRelation relation = new UmsRoleMenuRelation();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            roleMenuRelationMapper.insert(relation);
        }
        return menuIds.size();  // 成功分配的菜单数
    }

    /**
     * 给角色分配资源
     * @param roleId
     * @param resourceIds
     * @return
     */
    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        //先删除原有关系
        UmsRoleResourceRelationExample example=new UmsRoleResourceRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        roleResourceRelationMapper.deleteByExample(example);
        //批量插入新关系
        for (Long resourceId : resourceIds) {
            UmsRoleResourceRelation relation = new UmsRoleResourceRelation();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            roleResourceRelationMapper.insert(relation);
        }
        adminCacheService.delResourceListByRole(roleId);
        return resourceIds.size();
    }
}
