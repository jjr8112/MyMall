package com.jjr8112.mall.admin.dao;


import com.jjr8112.mall.admin.domain.OmsOrderReturnApplyResult;
import com.jjr8112.mall.admin.domain.OmsReturnApplyQueryParam;
import com.jjr8112.mall.mbg.model.OmsOrderReturnApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单退货申请自定义Dao
 */
public interface OmsOrderReturnApplyDao {
    /**
     * 查询申请列表
     */
    List<OmsOrderReturnApply> getList(@Param("queryParam") OmsReturnApplyQueryParam queryParam);

    /**
     * 获取申请详情
     */
    OmsOrderReturnApplyResult getDetail(@Param("id")Long id);
}

