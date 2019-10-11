package com.crazy.portal.entity.system;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Resource {
    private Integer id;

    private Short active;

    private String iconClass;

    private Integer parentId;

    private String resourceDesc;

    private String resourceName;

    private Integer resourceOrder;

    private Integer resourceType;

    private String resourceUrl;

    private String permissionPrefixUrl;

    @JSONField(serialize=false)
    private Date createTime;
    @JSONField(serialize=false)
    private Integer createUserId;
    @JSONField(serialize=false)
    private Date updateTime;
    @JSONField(serialize=false)
    private Integer updateUserId;

    private List<Resource> children = new ArrayList<>();
}