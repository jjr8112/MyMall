package com.jjr8112.mall.front_end.controller;

import com.jjr8112.mall.common.api.CommonResult;
import com.jjr8112.mall.front_end.domain.OmsOrderReturnApplyParam;
import com.jjr8112.mall.front_end.service.OmsFrontEndOrderReturnApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 申请退货管理Controller
 */
@Controller
@Api(tags = "OmsFrontEndOrderReturnApplyController", description = "申请退货管理")
@RequestMapping("/returnApply")
public class OmsFrontEndOrderReturnApplyController {
    @Autowired
    private OmsFrontEndOrderReturnApplyService returnApplyService;

    @ApiOperation("申请退货")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody OmsOrderReturnApplyParam returnApply) {
        int count = returnApplyService.create(returnApply);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
