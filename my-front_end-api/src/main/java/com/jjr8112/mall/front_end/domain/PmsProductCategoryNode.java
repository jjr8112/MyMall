package com.jjr8112.mall.front_end.domain;

import com.jjr8112.mall.mbg.model.PmsProductCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 商品分类，包含子分类
 */
@Getter
@Setter

public class PmsProductCategoryNode extends PmsProductCategory {

    private List<PmsProductCategoryNode> children;
}
