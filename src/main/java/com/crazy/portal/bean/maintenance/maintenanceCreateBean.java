package com.crazy.portal.bean.maintenance;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: maintenanceCreateBean
 * @Author: God Man Qiu~
 * @Date: 2019/10/15 01:28
 */
@Data
public class maintenanceCreateBean {
    private Integer type;
    private String contry;


    //联系人
    private String contactFirstName;
    private String contactLastName;
    private String contactEmial;
    private String contactNumber;

    //产品
    private String productNumber;
    private String productModel;
    private String productId;

    //install
    private Date installDate;
    private String contryCode;
    private String contryName;
}
