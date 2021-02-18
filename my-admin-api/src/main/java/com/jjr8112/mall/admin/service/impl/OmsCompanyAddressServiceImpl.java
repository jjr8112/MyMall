package com.jjr8112.mall.admin.service.impl;

import com.jjr8112.mall.admin.service.OmsCompanyAddressService;
import com.jjr8112.mall.mbg.mapper.OmsCompanyAddressMapper;
import com.jjr8112.mall.mbg.model.OmsCompanyAddress;
import com.jjr8112.mall.mbg.model.OmsCompanyAddressExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收货地址管理Service实现类
 */
@Service
public class OmsCompanyAddressServiceImpl implements OmsCompanyAddressService {
    @Autowired
    private OmsCompanyAddressMapper companyAddressMapper;
    @Override
    public List<OmsCompanyAddress> list() {
        return companyAddressMapper.selectByExample(new OmsCompanyAddressExample());
    }
}
