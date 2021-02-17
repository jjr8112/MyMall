package com.jjr8112.mall.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class CmsTopicComment implements Serializable {
    @ApiModelProperty(value = "话题评论表")
    private Long id;

    @ApiModelProperty(value = "会员昵称")
    private String memberNickName;

    @ApiModelProperty(value = "话题id")
    private Long topicId;

    @ApiModelProperty(value = "会员头像")
    private String memberIcon;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "是否展示")
    private Integer showStatus;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberNickName() {
        return memberNickName;
    }

    public void setMemberNickName(String memberNickName) {
        this.memberNickName = memberNickName;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getMemberIcon() {
        return memberIcon;
    }

    public void setMemberIcon(String memberIcon) {
        this.memberIcon = memberIcon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Integer showStatus) {
        this.showStatus = showStatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", memberNickName=").append(memberNickName);
        sb.append(", topicId=").append(topicId);
        sb.append(", memberIcon=").append(memberIcon);
        sb.append(", content=").append(content);
        sb.append(", createTime=").append(createTime);
        sb.append(", showStatus=").append(showStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}