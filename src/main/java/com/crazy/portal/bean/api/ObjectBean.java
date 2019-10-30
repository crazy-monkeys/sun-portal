package com.crazy.portal.bean.api;

import lombok.Data;

/**
 * @ClassName: ObjectBean
 * @Author: God Man Qiu~
 * @Date: 2019/10/29 02:21
 */
@Data
public class ObjectBean {
    private String objectId;
    private String objectType = "SERVICECALL";
}
