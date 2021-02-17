package com.jjr8112.mall.front_end.service.impl;


import com.jjr8112.mall.front_end.domain.OmsOrderReturnApplyParam;
import com.jjr8112.mall.front_end.service.OmsFrontEndOrderReturnApplyService;
import com.jjr8112.mall.mbg.mapper.OmsOrderReturnApplyMapper;
import com.jjr8112.mall.mbg.model.OmsOrderReturnApply;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 订单退货管理Service实现类
 * Created by macro on 2018/10/17.
 */
@Service
public class OmsFrontEndOrderReturnApplyServiceImpl implements OmsFrontEndOrderReturnApplyService {
    @Autowired
    private OmsOrderReturnApplyMapper returnApplyMapper;
    @Override
    public int create(OmsOrderReturnApplyParam returnApply) {
        OmsOrderReturnApply realApply = new OmsOrderReturnApply();
        BeanUtils.copyProperties(returnApply,realApply);
        realApply.setCreateTime(new Date());
        realApply.setStatus(0);
        return returnApplyMapper.insert(realApply);
    }
}
