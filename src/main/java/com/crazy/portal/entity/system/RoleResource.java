package com.crazy.portal.entity.system;

import lombok.Data;

import java.util.Date;

@Data
public class RoleResource {
    private Integer id;

    private Integer roleId;

    private Integer resourceId;

    private Date createTime;

    private Integer createId;

    private Date updateTime;

    private Integer updateId;
}