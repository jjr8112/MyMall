package com.jjr8112.mall.admin.service;

import com.jjr8112.mall.mbg.model.CmsSubject;

import java.util.List;

/**
 * 商品专题管理Service
 */
public interface CmsSubjectService {
    /**
     * 查询所有专题
     */
    List<CmsSubject> listAll();

    /**
     * 分页查询专题
     */
    List<CmsSubject> list(String keyword, Integer pageNum, Integer pageSize);
}
