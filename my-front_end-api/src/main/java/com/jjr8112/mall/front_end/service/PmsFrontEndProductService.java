package com.jjr8112.mall.front_end.service;

import com.jjr8112.mall.front_end.domain.PmsFrontEndProductDetail;
import com.jjr8112.mall.front_end.domain.PmsProductCategoryNode;
import com.jjr8112.mall.mbg.model.PmsProduct;

import java.util.List;

/**
 * 前台商品管理Service
 */
public interface PmsFrontEndProductService {
    /**
     * 综合搜索商品
     */
    List<PmsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort);

    /**
     * 以树形结构获取所有商品分类
     */
    List<PmsProductCategoryNode> categoryTreeList();

    /**
     * 获取前台商品详情
     */
    PmsFrontEndProductDetail detail(Long id);
}

