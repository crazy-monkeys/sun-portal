package com.crazy.portal.service.system;

import com.crazy.portal.bean.system.MailBean;
import com.crazy.portal.config.email.EmailHelper;
import com.crazy.portal.config.exception.BusinessException;
import com.crazy.portal.dao.system.RetrievePasswordMapper;
import com.crazy.portal.dao.system.RoleMapper;
import com.crazy.portal.dao.system.UserMapper;
import com.crazy.portal.dao.system.UserRoleMapper;
import com.crazy.portal.entity.system.RetrievePassword;
import com.crazy.portal.entity.system.Role;
import com.crazy.portal.entity.system.User;
import com.crazy.portal.entity.system.UserRole;
import com.crazy.portal.util.*;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 10:41 2019/4/9
 * @Modified by:
 */
@Slf4j
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private EmailHelper emailHelper;
    @Resource
    private RetrievePasswordMapper retrievePasswordMapper;

    @Value("${portal.view-url}")
    private String portalViewUrl;


    private PasswordEncoder passwordEncoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();

    /**
     * 分页获取用户信息
     * @param user
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<User> selectUserWithPage(User user,Integer pageNum, Integer pageSize){
        PortalUtil.defaultStartPage(pageNum, pageSize);
        return new PageInfo<>(userMapper.selectUserWithPage(user));
    }

    /**
     * 根据用户名查找详细信息
     * @param userName
     * @return
     */
    public User findUser(String userName){
        if(StringUtil.isEmpty(userName)){
            throw new BusinessException(ErrorCodes.SystemManagerEnum.USER_EMPTY_USER_NAME.getCode(),
                    ErrorCodes.SystemManagerEnum.USER_EMPTY_USER_NAME.getMsg());
        }
        return userMapper.findByLoginName(userName);
    }

    /**
     * modifyLifecycle
     * @param loginName
     * @param userId
     * @param userStatus 1 正常 0 冻结
     * @return
     */
    @Transactional
    public int modifyLifecycle(String loginName,Integer userStatus,Integer userId){
        User user = this.findUser(loginName);
        BusinessUtil.notNull(user,ErrorCodes.SystemManagerEnum.USER_NOT_EXISTS);
        user.setUserStatus(userStatus);
        user.setUpdateTime(DateUtil.getCurrentTS());
        user.setUpdateUserId(userId);
        return userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 重置密码
     * @param loginName
     * @param currentUser
     */
    @Transactional
    public void resetUserPwd(String loginName,User currentUser){
        User user = this.findUser(loginName);
        if(user == null){
            throw new BusinessException(ErrorCodes.SystemManagerEnum.USER_NOT_EXISTS.getCode(),
                    ErrorCodes.SystemManagerEnum.USER_NOT_EXISTS.getMsg());
        }
        user.setUpdateTime(new Date());
        user.setUpdateUserId(currentUser.getId());
        String newPasswod = PortalUtil.generateRandomPassword();
        user.setLoginPwd(passwordEncoder.encode(newPasswod));
        userMapper.updateByPrimaryKeySelective(user);

        //发送邮件
        MailBean mailBean = new MailBean();
        mailBean.setTos(user.getEmail());
        mailBean.setSubject("密码重置邮件");

        Map<String, Object> map = new HashMap<>();
        map.put("loginName",user.getLoginName());
        map.put("password",newPasswod);
        mailBean.setParams(map);
        mailBean.setTemplateName(EmailHelper.MAIL_TEMPLATE.RESET_PWD.getTemplateName());
        emailHelper.sendHtmlMail(mailBean);
    }

    /**
     * 用户-修改密码
     * @param loginName
     * @param oldPwd
     * @param newPwd
     * @param userId
     */
    @Transactional
    public void modifyPwd(String loginName,String oldPwd,String newPwd,Integer userId){
        User user = this.findUser(loginName);
        if(user == null){
            throw new BusinessException(ErrorCodes.SystemManagerEnum.USER_NOT_EXISTS.getCode(),
                    ErrorCodes.SystemManagerEnum.USER_NOT_EXISTS.getMsg());
        }
        if(!passwordEncoder.matches(oldPwd,user.getLoginPwd())){
            throw new BusinessException(ErrorCodes.SystemManagerEnum.USER_INVALID_PASSWORD.getCode(),
                    ErrorCodes.SystemManagerEnum.USER_INVALID_PASSWORD.getMsg());
        }
        user.setLoginPwd(passwordEncoder.encode(newPwd));
        user.setUpdateTime(new Date());
        user.setUpdateUserId(userId);
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 忘记密码-修改密码
     * @param loginName
     * @param sid
     * @param newPwd
     * @return
     */
    @Transactional
    public boolean modifyPwd(String loginName,String sid,String newPwd){

        User user = userMapper.findByLoginName(loginName);
        BusinessUtil.notNull(user, ErrorCodes.SystemManagerEnum.USER_NOT_EXISTS);
        BusinessUtil.notNull(user.getEmail(),ErrorCodes.SystemManagerEnum.USER_EMAIL_INVALID);

        RetrievePassword retrievePassword =
                retrievePasswordMapper.selectByRandomCodeAndUserId(user.getId(), sid);

        BusinessUtil.notNull(retrievePassword,ErrorCodes.SystemManagerEnum.USER_FOGET_EMAIL_URL_INVALID);

        log.info("User {} resets the password",user.getLoginName());
        user.setLoginPwd(passwordEncoder.encode(newPwd));
        user.setPwdInvalidTime(DateUtil.addDays(new Date(),365 * 10));
        user.setUpdateTime(new Date());
        user.setUpdateUserId(user.getId());
        userMapper.updateByPrimaryKeySelective(user);

        retrievePassword.setStatus(0);
        retrievePassword.setUpdateTime(new Date());
        retrievePasswordMapper.updateByPrimaryKeySelective(retrievePassword);
        return true;
    }

    /**
     * 发送忘记密码邮件
     * @param loginName
     */
    @Transactional
    public boolean sendForgetEmail(String loginName){
        User user = userMapper.findByLoginName(loginName);
        BusinessUtil.notNull(user, ErrorCodes.SystemManagerEnum.USER_NOT_EXISTS);

        MailBean mailBean = new MailBean();
        mailBean.setTemplateName(EmailHelper.MAIL_TEMPLATE.FORGET_PWD.getTemplateName());
        mailBean.setSubject("忘记密码邮件");
        mailBean.setTos(user.getEmail());
        String randomCode = UUID.randomUUID().toString();
        Map<String,Object> map = new HashMap<>();
        map.put("url",String.format("%s?sid=%s&loginName=%s",portalViewUrl,randomCode,loginName));
        map.put("loginName",loginName);
        mailBean.setParams(map);

        emailHelper.sendHtmlMail(mailBean);

        //保存
        Date now = new Date();
        RetrievePassword retrievePassword = new RetrievePassword();
        retrievePassword.setUserid(user.getId());
        retrievePassword.setRandomCode(randomCode);
        retrievePassword.setInvalidTime(DateUtil.addMinutes(now,30));
        retrievePassword.setStatus(1);
        retrievePassword.setCreateTime(now);

        retrievePasswordMapper.insertSelective(retrievePassword);
        return true;
    }

    @Transactional
    public void createUser(String username, String loginName, String email, Integer dealerId){
        User user = new User();
        user.setLoginName(loginName);
        user.setCustomerName(username);
        user.setEmail(email);
        user.setDealerId(dealerId);
        //域账号使用随机密码
        String password = PortalUtil.generateRandomPassword();
        user.setLoginPwd(passwordEncoder.encode(password));
        user.setActive((short)1);
        user.setUserStatus(1);
        //域账号密码过期跟随ad域,这里设置为20年过期
        user.setPwdInvalidTime(DateUtil.addDays(new Date(),365));
        user.setRegTime(new Date());
        user.setUserType(Enums.USER_TYPE.agent.toString());
        user.setCreateUserId(1);
        user.setCreateTime(new Date());
        userMapper.insertSelective(user);

        Role basicRole = roleMapper.queryRoleList("BASIC_ROLE").get(0);
        UserRole userRole = new UserRole();
        userRole.setCreateId(1);
        userRole.setCreateTime(new Date());
        userRole.setRoleId(basicRole.getId());
        userRole.setUserId(user.getId());
        userRoleMapper.insertSelective(userRole);

        //发送邮件通知用户
        MailBean mailBean = new MailBean();
        mailBean.setTos(user.getEmail());
        mailBean.setSubject("账号开通邮件");
        Map<String, Object> map = new HashMap<>();
        map.put("loginName",user.getLoginName());
        map.put("password",password);
        mailBean.setParams(map);
        mailBean.setTemplateName(EmailHelper.MAIL_TEMPLATE.USER_CREATE.getTemplateName());
        emailHelper.sendHtmlMail(mailBean);
    }
}
