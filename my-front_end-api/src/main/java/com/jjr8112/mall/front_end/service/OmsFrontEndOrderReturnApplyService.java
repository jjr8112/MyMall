package com.jjr8112.mall.front_end.service;

import com.jjr8112.mall.front_end.domain.OmsOrderReturnApplyParam;

/**
 * 前台订单退货管理Service
 */
public interface OmsFrontEndOrderReturnApplyService {
    /**
     * 提交申请
     */
    int create(OmsOrderReturnApplyParam returnApply);
}

