package com.jjr8112.mall.admin.service.impl;


import com.github.pagehelper.PageHelper;
import com.jjr8112.mall.admin.domain.UmsMenuNode;
import com.jjr8112.mall.admin.service.UmsMenuService;
import com.jjr8112.mall.mbg.mapper.UmsMenuMapper;
import com.jjr8112.mall.mbg.model.UmsMenu;
import com.jjr8112.mall.mbg.model.UmsMenuExample;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台菜单管理Service实现类
 */
@Service
public class UmsMenuServiceImpl implements UmsMenuService {
    @Autowired
    private UmsMenuMapper menuMapper;

    @Override
    public int create(UmsMenu umsMenu) {
        umsMenu.setCreateTime(new Date());
        updateLevel(umsMenu);
        return menuMapper.insert(umsMenu);
    }

    /**
     * 修改菜单层级
     */
    private void updateLevel(UmsMenu umsMenu) {
        if (umsMenu.getParentId() == 0) {
            //没有父菜单时为一级菜单
            umsMenu.setLevel(0);
        } else {
            //有父菜单时选择根据父菜单level设置
            UmsMenu parentMenu = menuMapper.selectByPrimaryKey(umsMenu.getParentId());
            if (parentMenu != null) {
                umsMenu.setLevel(parentMenu.getLevel() + 1);
            } else {
                umsMenu.setLevel(0);
            }
        }
    }

    @Override
    public int update(Long id, UmsMenu umsMenu) {
        umsMenu.setId(id);
        updateLevel(umsMenu);
        return menuMapper.updateByPrimaryKeySelective(umsMenu);
    }

    @Override
    public UmsMenu getItem(Long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(Long id) {
        return menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<UmsMenu> list(Long parentId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        UmsMenuExample example = new UmsMenuExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andParentIdEqualTo(parentId);
        return menuMapper.selectByExample(example);
    }

    @Override
    public List<UmsMenuNode> treeList() {
        List<UmsMenu> menuList = menuMapper.selectByExample(new UmsMenuExample());  // 菜单列表
        List<UmsMenuNode> result = menuList.stream()            // 流操作
                .filter(menu -> menu.getParentId().equals(0L))  // 过滤条件：传入一个 menu，得到满足父级菜单为 0L的menu
                .map(menu -> covertMenuNode(menu, menuList))    // 将原本的 menu转换为具有 children的节点类型
                .collect(Collectors.toList());                  // 再加入集合中
        return result;
    }

    @Override
    public int updateHidden(Long id, Integer hidden) {
        UmsMenu umsMenu = new UmsMenu();
        umsMenu.setId(id);
        umsMenu.setHidden(hidden);
        return menuMapper.updateByPrimaryKeySelective(umsMenu);
    }

    /**
     * 将UmsMenu转化为UmsMenuNode并设置children属性
     */
    private UmsMenuNode covertMenuNode(UmsMenu menu, List<UmsMenu> menuList) {
        UmsMenuNode node = new UmsMenuNode();
        BeanUtils.copyProperties(menu, node);                                   // 将 menu中已包含的属性加入到 node中
        List<UmsMenuNode> children = menuList.stream()                          // 流操作
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))  // 过滤条件：获取当前 menu的子级 menu
                .map(subMenu -> covertMenuNode(subMenu, menuList))              // 传入当前 menu的子级 menu，递归向下找到最低级 menu
                .collect(Collectors.toList());                                  // 加入集合
        node.setChildren(children);
        return node;
    }
}

