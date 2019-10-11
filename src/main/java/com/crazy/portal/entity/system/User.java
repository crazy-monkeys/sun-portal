package com.crazy.portal.entity.system;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User {
    private Integer id;

    private String email;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    private String loginName;

    @JSONField(serialize=false)
    private String loginPwd;

    private String mobile;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date pwdInvalidTime;

    /**
     * 客户姓名
     */
    private String customerName;
    /**
     * 注册时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date regTime;

    /**
     * 用户状态
     * 正常:1 冻结:0
     */
    private Integer userStatus;

    /**
     * 用户类型
     * @link Enums.USER_TYPE
     */
    private String userType;

    /**
     * 是否有效
     * 正常:1 逻辑删除:0
     */
    private Short active;

    @JSONField(serialize=false)
    private Integer createUserId;

    @JSONField(serialize=false)
    private Date createTime;

    @JSONField(serialize=false)
    private Integer updateUserId;

    @JSONField(serialize=false)
    private Date updateTime;

    /** ext **/
    private Role role;

    @JSONField(serialize=false)
    private Date regStartTime;
    @JSONField(serialize=false)
    private Date regEndTime;

    /**
     * dealer 用户同步进用户表 关联上dealerId
     */
    private Integer dealerId;
}