package com.crazy.portal.entity.system;

import lombok.Data;

import java.util.Date;

@Data
public class UserRole {


    public UserRole(){}
    public UserRole(Integer userId,Integer roleId){
        this.userId = userId;
        this.roleId = roleId;
    }
    private Integer id;

    private Integer userId;

    private Integer roleId;

    private Date createTime;

    private Integer createId;

    private Date updateTime;

    private Integer updateId;
}