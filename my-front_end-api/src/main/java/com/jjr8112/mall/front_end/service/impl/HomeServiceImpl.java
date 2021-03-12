package com.jjr8112.mall.front_end.service.impl;


import com.github.pagehelper.PageHelper;
import com.jjr8112.mall.front_end.dao.HomeDao;
import com.jjr8112.mall.front_end.domain.FlashPromotionProduct;
import com.jjr8112.mall.front_end.domain.HomeContentResult;
import com.jjr8112.mall.front_end.domain.HomeFlashPromotion;
import com.jjr8112.mall.front_end.service.HomeService;
import com.jjr8112.mall.front_end.util.DateUtil;
import com.jjr8112.mall.mbg.mapper.*;
import com.jjr8112.mall.mbg.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 首页内容管理Service实现类
 * Created by macro on 2019/1/28.
 */
@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private SmsHomeAdvertiseMapper advertiseMapper;
    @Autowired
    private HomeDao homeDao;
    @Autowired
    private SmsFlashPromotionMapper flashPromotionMapper;
    @Autowired
    private SmsFlashPromotionSessionMapper promotionSessionMapper;
    @Autowired
    private PmsProductMapper productMapper;
    @Autowired
    private PmsProductCategoryMapper productCategoryMapper;
    @Autowired
    private CmsSubjectMapper subjectMapper;

    /**
     * 获取首页内容
     * @return
     */
    @Override
    public HomeContentResult content() {
        HomeContentResult result = new HomeContentResult();
        // 获取首页广告
        result.setAdvertiseList(getHomeAdvertiseList());
        // 获取推荐品牌
        result.setBrandList(homeDao.getRecommendBrandList(0,6));
        // 获取秒杀信息
        result.setHomeFlashPromotion(getHomeFlashPromotion());
        // 获取新品推荐
        result.setNewProductList(homeDao.getNewProductList(0,4));
        // 获取人气推荐
        result.setHotProductList(homeDao.getHotProductList(0,4));
        // 获取推荐专题
        result.setSubjectList(homeDao.getRecommendSubjectList(0,4));
        return result;
    }

    /**
     * 首页商品推荐(暂时默认推荐所有商品)
     * @param pageSize
     * @param pageNum
     * @return
     */
    @Override
    public List<PmsProduct> recommendProductList(Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria()
                .andDeleteStatusEqualTo(0)          // 未删除
                .andPublishStatusEqualTo(1);        // 已上架
        return productMapper.selectByExample(example);      // 获取所有商品
    }

    /**
     * 获取商品分类
     * @param parentId 0:获取一级分类；其他：获取指定二级分类
     * @return
     */
    @Override
    public List<PmsProductCategory> getProductCateList(Long parentId) {
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        example.createCriteria()
                .andShowStatusEqualTo(1)        // 分类是否显示
                .andParentIdEqualTo(parentId);         // 当前分类的父类id等于传入的参数
        example.setOrderByClause("sort desc");          // order by sort desc
        return productCategoryMapper.selectByExample(example);
    }


    /**
     * 内容管理有待商榷
     */
    /**
     * 分页获取专题数据
     * @param cateId 专题分类id
     * @param pageSize
     * @param pageNum
     * @return
     */
    @Override
    public List<CmsSubject> getSubjectList(Long cateId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        CmsSubjectExample example = new CmsSubjectExample();
        CmsSubjectExample.Criteria criteria = example.createCriteria();
        criteria.andShowStatusEqualTo(1);           // 显示
        if(cateId!=null){                                   // 分类id不为空
            criteria.andCategoryIdEqualTo(cateId);
        }
        return subjectMapper.selectByExample(example);
    }

    /**
     * 分页获取人气商品
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<PmsProduct> hotProductList(Integer pageNum, Integer pageSize) {
        int offset = pageSize * (pageNum - 1);              // 偏移量
        return homeDao.getHotProductList(offset, pageSize); // limit offset pageSize，获取分页从 offset+1开始的 pageSize行
    }

    /**
     * 分页获取新品推荐
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<PmsProduct> newProductList(Integer pageNum, Integer pageSize) {
        int offset = pageSize * (pageNum - 1);      // 偏移量
        return homeDao.getNewProductList(offset, pageSize);
    }

    /**
     * 获取秒杀信息
     * @return
     */
    private HomeFlashPromotion getHomeFlashPromotion() {
        HomeFlashPromotion homeFlashPromotion = new HomeFlashPromotion();   // 存储秒杀信息
        //获取当前秒杀活动
        Date now = new Date();  // 当前时间
        SmsFlashPromotion flashPromotion = getFlashPromotion(now);  // 获取当前秒杀活动   (从某日到某日)
        if (flashPromotion != null) {
            //获取当前秒杀场次
            SmsFlashPromotionSession flashPromotionSession = getFlashPromotionSession(now);     // 注意，是具体场次  (每次从几点到几点)
            if (flashPromotionSession != null) {    // 具体场次信息存在
                // 之前获取的是日期，没有更具体的体现
                // 因此可视作是一个中间变量
                // 用具体的 “某日的某时到某时” 确定真正的当前秒杀活动情况
                homeFlashPromotion.setStartTime(flashPromotionSession.getStartTime());
                homeFlashPromotion.setEndTime(flashPromotionSession.getEndTime());
                //获取下一个秒杀场次
                SmsFlashPromotionSession nextSession = getNextFlashPromotionSession(homeFlashPromotion.getStartTime());
                if(nextSession!=null){
                    // 确定下一场秒杀的时间
                    homeFlashPromotion.setNextStartTime(nextSession.getStartTime());
                    homeFlashPromotion.setNextEndTime(nextSession.getEndTime());
                }
                //获取秒杀商品
                // 秒杀活动 + 具体场次 ---(限时购与商品多对多) ---> 具体商品
                List<FlashPromotionProduct> flashProductList = homeDao.getFlashProductList(flashPromotion.getId(),
                                                                                           flashPromotionSession.getId());
                homeFlashPromotion.setProductList(flashProductList);
            }
        }
        return homeFlashPromotion;
    }

    //获取下一个场次信息
    private SmsFlashPromotionSession getNextFlashPromotionSession(Date date) {      // 该参数为当前秒杀活动的开始时间
        SmsFlashPromotionSessionExample sessionExample = new SmsFlashPromotionSessionExample();
        sessionExample.createCriteria()
                .andStartTimeGreaterThan(date);     // 查询比参数大的开始时间
        sessionExample.setOrderByClause("start_time asc");  // order by start_time asc
        List<SmsFlashPromotionSession> promotionSessionList = promotionSessionMapper.selectByExample(sessionExample);   // 结果集可能不止一个
        if (!CollectionUtils.isEmpty(promotionSessionList)) {
            return promotionSessionList.get(0);
        }
        return null;
    }

    // 获取首页广告
    private List<SmsHomeAdvertise> getHomeAdvertiseList() {
        SmsHomeAdvertiseExample example = new SmsHomeAdvertiseExample();
        example.createCriteria().andTypeEqualTo(1).andStatusEqualTo(1);
        example.setOrderByClause("sort desc");
        return advertiseMapper.selectByExample(example);
    }

    //根据时间获取秒杀活动
    private SmsFlashPromotion getFlashPromotion(Date date) {
        Date currDate = DateUtil.getDate(date);     // 从 date对象中解析出日期
        SmsFlashPromotionExample example = new SmsFlashPromotionExample();
        example.createCriteria()
                .andStatusEqualTo(1)    // 上线状态
                .andStartDateLessThanOrEqualTo(currDate)    // 当前日期大于秒杀开始日期
                .andEndDateGreaterThanOrEqualTo(currDate);  // 当前日期小于秒杀开始日期
        List<SmsFlashPromotion> flashPromotionList = flashPromotionMapper.selectByExample(example); // 结果集可能不止一个秒杀活动信息
        if (!CollectionUtils.isEmpty(flashPromotionList)) {
            return flashPromotionList.get(0);
        }
        return null;
    }

    //根据时间获取秒杀场次
    private SmsFlashPromotionSession getFlashPromotionSession(Date date) {
        Date currTime = DateUtil.getTime(date);     // 从 date对象中解析出时间
        SmsFlashPromotionSessionExample sessionExample = new SmsFlashPromotionSessionExample();
        sessionExample.createCriteria()
                .andStartTimeLessThanOrEqualTo(currTime)        // 当前时间大于秒杀场次开始时间
                .andEndTimeGreaterThanOrEqualTo(currTime);      // 当前时间小于秒杀场次结束时间
        List<SmsFlashPromotionSession> promotionSessionList = promotionSessionMapper.selectByExample(sessionExample);   // 结果集可能不止一个场次
        if (!CollectionUtils.isEmpty(promotionSessionList)) {
            return promotionSessionList.get(0);
        }
        return null;
    }
}

