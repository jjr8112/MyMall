package com.jjr8112.mall.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class CmsHelpCategory implements Serializable {
    @ApiModelProperty(value = "帮助分类表")
    private Long id;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "分类图标")
    private String icon;

    @ApiModelProperty(value = "专题数量")
    private Integer helpCount;

    @ApiModelProperty(value = "是否展示")
    private Integer showStatus;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getHelpCount() {
        return helpCount;
    }

    public void setHelpCount(Integer helpCount) {
        this.helpCount = helpCount;
    }

    public Integer getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Integer showStatus) {
        this.showStatus = showStatus;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", icon=").append(icon);
        sb.append(", helpCount=").append(helpCount);
        sb.append(", showStatus=").append(showStatus);
        sb.append(", sort=").append(sort);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}