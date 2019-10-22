package com.crazy.portal.bean.maintenance;

import lombok.Data;
/**
 * @ClassName: MaintenanceBean
 * @Author: God Man Qiu~
 * @Date: 2019/10/16 23:15
 */
@Data
public class MaintenanceBean {
    private Integer type;
    private String contry;
    private String installInstaller;
    private String installCec;
    private String suggestions;

    //firstname
    private String contactFirstName;
    //last name
    private String contactLastName;
    //business name
    private String contactBusinessName;
    //first+last
    private String contactName;
    /**
     * 联系人邮箱
     */
    private String contactEmial;
    /**
     *接收邮件邮箱
     */
    private String sendEmial;
    private String contactNumber;

    //产品
    private String productNumber;
    private String productModel;
    private String[] productId;
    private String businessPartner;

    //install
    private String installDate;
    private String contryCode;
    private String contryName;
    private String cityCode;
    private String cityName;
    private String stateCode;
    private String stateName;
    private String streetName;
    private String postCode;
    private String addressLine1;
    private String addressLine2;


    private String abn;

    //Suburb
    private String detail;
}
