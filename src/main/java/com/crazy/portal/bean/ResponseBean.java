package com.crazy.portal.bean;

import lombok.Data;

/**
 * @ClassName: ResponseBean
 * @Author: God Man Qiu~
 * @Date: 2019/10/22 22:01
 */
@Data
public class ResponseBean {
    //设备id
    private String id;
    //合作伙伴id
    private String businessPartner;
    //物料id
    private String item;
    //设备型号值
    private String productModelValue;
    //设备型号
    private String productModel;
    //本地发货日期
    private String deliveryDate;
}
