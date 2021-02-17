package com.jjr8112.mall.front_end.service.impl;


import com.github.pagehelper.PageHelper;
import com.jjr8112.mall.common.api.CommonPage;
import com.jjr8112.mall.front_end.dao.HomeDao;
import com.jjr8112.mall.front_end.service.FrontEndBrandService;
import com.jjr8112.mall.mbg.mapper.PmsBrandMapper;
import com.jjr8112.mall.mbg.mapper.PmsProductMapper;
import com.jjr8112.mall.mbg.model.PmsBrand;
import com.jjr8112.mall.mbg.model.PmsProduct;
import com.jjr8112.mall.mbg.model.PmsProductExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 前台品牌管理Service实现类
 */
@Service
public class FrontEndBrandServiceImpl implements FrontEndBrandService {
    @Autowired
    private HomeDao homeDao;
    @Autowired
    private PmsBrandMapper brandMapper;
    @Autowired
    private PmsProductMapper productMapper;

    @Override
    public List<PmsBrand> recommendList(Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return homeDao.getRecommendBrandList(offset, pageSize);
    }

    @Override
    public PmsBrand detail(Long brandId) {
        return brandMapper.selectByPrimaryKey(brandId);
    }

    @Override
    public CommonPage<PmsProduct> productList(Long brandId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andDeleteStatusEqualTo(0)
                .andBrandIdEqualTo(brandId);
        List<PmsProduct> productList = productMapper.selectByExample(example);
        return CommonPage.restPage(productList);
    }
}
