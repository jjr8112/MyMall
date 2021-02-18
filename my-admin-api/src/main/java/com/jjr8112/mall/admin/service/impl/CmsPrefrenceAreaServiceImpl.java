package com.jjr8112.mall.admin.service.impl;

import com.jjr8112.mall.admin.service.CmsPrefrenceAreaService;
import com.jjr8112.mall.mbg.mapper.CmsPrefrenceAreaMapper;
import com.jjr8112.mall.mbg.model.CmsPrefrenceArea;
import com.jjr8112.mall.mbg.model.CmsPrefrenceAreaExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品优选管理Service实现类
 */
@Service
public class CmsPrefrenceAreaServiceImpl implements CmsPrefrenceAreaService {
    @Autowired
    private CmsPrefrenceAreaMapper prefrenceAreaMapper;

    @Override
    public List<CmsPrefrenceArea> listAll() {
        return prefrenceAreaMapper.selectByExample(new CmsPrefrenceAreaExample());
    }
}
