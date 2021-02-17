package com.jjr8112.mall.front_end.service;

import com.jjr8112.mall.common.api.CommonPage;
import com.jjr8112.mall.mbg.model.PmsBrand;
import com.jjr8112.mall.mbg.model.PmsProduct;

import java.util.List;

/**
 * 前台品牌管理Service
 */
public interface FrontEndBrandService {
    /**
     * 分页获取推荐品牌
     */
    List<PmsBrand> recommendList(Integer pageNum, Integer pageSize);

    /**
     * 获取品牌详情
     */
    PmsBrand detail(Long brandId);

    /**
     * 分页获取品牌关联商品
     */
    CommonPage<PmsProduct> productList(Long brandId, Integer pageNum, Integer pageSize);
}

