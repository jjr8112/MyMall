package com.jjr8112.mall.front_end.service.impl;


import com.jjr8112.mall.security.util.JwtTokenUtil;
import com.jjr8112.mall.common.exception.Asserts;
import com.jjr8112.mall.front_end.domain.MemberDetails;
import com.jjr8112.mall.front_end.service.UmsMemberCacheService;
import com.jjr8112.mall.front_end.service.UmsMemberService;
import com.jjr8112.mall.mbg.mapper.UmsMemberLevelMapper;
import com.jjr8112.mall.mbg.mapper.UmsMemberMapper;
import com.jjr8112.mall.mbg.model.UmsMember;
import com.jjr8112.mall.mbg.model.UmsMemberExample;
import com.jjr8112.mall.mbg.model.UmsMemberLevel;
import com.jjr8112.mall.mbg.model.UmsMemberLevelExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 会员管理Service实现类
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsMemberServiceImpl.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UmsMemberMapper memberMapper;
    @Autowired
    private UmsMemberLevelMapper memberLevelMapper;
    @Autowired
    private UmsMemberCacheService memberCacheService;
    @Value("${redis.key.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${redis.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;

    /**
     * 根据用户名获取会员
     * @param username
     * @return
     */
    @Override
    public UmsMember getByUsername(String username) {
        UmsMember member = memberCacheService.getMember(username);              // 先尝试从缓存中通过会员名获取会员
        if(member!=null) return member;                                         // 缓存中未找到
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andUsernameEqualTo(username);                  // 与指定会员名相同
        List<UmsMember> memberList = memberMapper.selectByExample(example);     // 从数据库中获取相应会员
        if (!CollectionUtils.isEmpty(memberList)) {                             // 数据库中存在相应会员
            member = memberList.get(0);
            memberCacheService.setMember(member);                               // 同步到缓存
            return member;
        }
        return null;
    }

    /**
     * 根据会员编号获取会员
     * @param id
     * @return
     */
    @Override
    public UmsMember getById(Long id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    /**
     * 用户注册
     * @param username 会员名
     * @param password 密码
     * @param telephone 电话
     * @param authCode 验证码
     */
    @Override
    public void register(String username, String password, String telephone, String authCode) {
        //验证验证码
        if(!verifyAuthCode(authCode,telephone)){
            Asserts.fail("验证码错误");
        }
        //查询是否已有该用户
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andUsernameEqualTo(username);  // 根据会员名查询是否已有该会员
        example.or(example.createCriteria().andPhoneEqualTo(telephone));    // 或根据电话号码查询是否有该会员
        List<UmsMember> umsMembers = memberMapper.selectByExample(example); // select结果集
        if (!CollectionUtils.isEmpty(umsMembers)) {     // 结果集不为空
            Asserts.fail("该用户已经存在");
        }
        //没有该用户进行添加操作
        UmsMember umsMember = new UmsMember();
        umsMember.setUsername(username);
        umsMember.setPhone(telephone);
        umsMember.setPassword(passwordEncoder.encode(password));
        umsMember.setCreateTime(new Date());
        umsMember.setStatus(1);
        //获取默认会员等级并设置
        UmsMemberLevelExample levelExample = new UmsMemberLevelExample();
        levelExample.createCriteria().andDefaultStatusEqualTo(1);
        List<UmsMemberLevel> memberLevelList = memberLevelMapper.selectByExample(levelExample);
        if (!CollectionUtils.isEmpty(memberLevelList)) {
            umsMember.setMemberLevelId(memberLevelList.get(0).getId());
        }
        memberMapper.insert(umsMember);
        umsMember.setPassword(null);
    }

    /**
     * 生成验证码
     * @param telephone
     * @return
     */
    @Override
    public String generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<6;i++){   // 六位随机数
            sb.append(random.nextInt(10));
        }
        memberCacheService.setAuthCode(telephone,sb.toString());    // 保存验证码到缓存中
        return sb.toString();
    }

    /**
     * 修改密码
     * @param telephone
     * @param password
     * @param authCode
     */
    @Override
    public void updatePassword(String telephone, String password, String authCode) {
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andPhoneEqualTo(telephone);                // 通过电话号码获取会员信息
        List<UmsMember> memberList = memberMapper.selectByExample(example); // 获取符合上述电话号码的所有会员
        if(CollectionUtils.isEmpty(memberList)){                            // 不存在这样的会员
            Asserts.fail("该账号不存在");                                    // 自定义断言类
        }
        //验证验证码
        if(!verifyAuthCode(authCode,telephone)){                            // 校验验证码出错
            Asserts.fail("验证码错误");
        }
        UmsMember umsMember = memberList.get(0);                            // 获取相应会员
        umsMember.setPassword(passwordEncoder.encode(password));            // 修改其密码
        memberMapper.updateByPrimaryKeySelective(umsMember);                // 跟新会员密码
        memberCacheService.delMember(umsMember.getId());                    // 删除相应缓存
    }

    /**
     * 获取当前登录会员
     * @return
     */
    @Override
    public UmsMember getCurrentMember() {
        SecurityContext ctx = SecurityContextHolder.getContext();   // 存储当前用户账号信息和相关权限
        Authentication auth = ctx.getAuthentication();              // spring security中用于获取当前用户信息的对象
        MemberDetails memberDetails = (MemberDetails) auth.getPrincipal();  // 自定义封装类
        return memberDetails.getUmsMember();
    }

    /**
     * 根据会员id修改会员积分
     * @param id
     * @param integration
     */
    @Override
    public void updateIntegration(Long id, Integer integration) {
        UmsMember record=new UmsMember();
        record.setId(id);
        record.setIntegration(integration); // 设置积分
        memberMapper.updateByPrimaryKeySelective(record);   // 更新操作
        memberCacheService.delMember(id);   // 删除相应缓存
    }

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        UmsMember member = getByUsername(username);
        if(member!=null){
            return new MemberDetails(member);       // 会员信息封装
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    /**
     * 登录后获取token
     * @param username
     * @param password
     * @return
     */
    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);     // 获取会员信息
            if(!passwordEncoder.matches(password,userDetails.getPassword())){   // 匹配验证
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);   // Authentication 会员信息的封装
            token = jwtTokenUtil.generateToken(userDetails);    // 创建一个新的 token
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    @Override
    public String refreshToken(String token) {
        return jwtTokenUtil.refreshHeadToken(token);
    }

    /**
     * 对输入的验证码进行校验
     */
    private boolean verifyAuthCode(String authCode, String telephone){
        if(StringUtils.isEmpty(authCode)){
            return false;
        }
        String realAuthCode = memberCacheService.getAuthCode(telephone);    // 获取验证码
        return authCode.equals(realAuthCode);
    }

}
