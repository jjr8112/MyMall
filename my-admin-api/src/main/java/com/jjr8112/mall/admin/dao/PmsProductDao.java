package com.jjr8112.mall.admin.dao;

import com.jjr8112.mall.admin.domain.PmsProductResult;
import org.apache.ibatis.annotations.Param;

/**
 * 自定义商品管理Dao
 */
public interface PmsProductDao {
    /**
     * 获取商品编辑信息
     */
    PmsProductResult getUpdateInfo(@Param("id") Long id);
}
