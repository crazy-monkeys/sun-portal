package com.crazy.portal.bean.maintenance;

import lombok.Data;

import java.util.Date;

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

    //联系人
    private String contactFirstName;
    private String contactLastName;
    //first+last
    private String contactName;
    private String contactEmial;
    private String contactNumber;

    //产品
    private String productNumber;
    private String productModel;
    private String productId;

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
    //Suburb
    private String detail;
}
