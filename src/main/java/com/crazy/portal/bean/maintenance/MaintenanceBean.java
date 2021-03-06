package com.crazy.portal.bean.maintenance;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: MaintenanceBean
 * @Author: God Man Qiu~
 * @Date: 2019/10/16 23:15
 */
@Data
public class MaintenanceBean {
    //1-维保注册 2-服务请求 3-维保注册
    private Integer type;
    //客户  产品带出
    private String businessPartner;
    //设备id 组
    private String[] equipments;
    //安装时间
    private String installDate;
    //安装人
    private String installInstaller;
    //安装国家
    private String contryCode;
    //产品
    private List<ProductBean> products;
    private ContactBean contact;
    private EndUserBean endUser;
    /**
     *维保注册
     */
    //Do you have any product suggestions?
    private String suggestions;
    //cec
    private String installCec;

    /**
     * 服务请求
     */
    //服务请求 type
    private String businessType;
    //Want to claim an accessory? Specify here
    private String accessory;

    private ServiceCallBean serviceCallBean;

    private BigDecimal totalAmount;
}
