package com.jjr8112.mall.admin.service;

import com.jjr8112.mall.mbg.model.OmsCompanyAddress;

import java.util.List;


/**
 * 收货地址管理Service
 */
public interface OmsCompanyAddressService {
    /**
     * 获取全部收货地址
     */
    List<OmsCompanyAddress> list();
}
