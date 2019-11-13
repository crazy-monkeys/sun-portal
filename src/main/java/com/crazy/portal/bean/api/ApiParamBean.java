package com.crazy.portal.bean.api;

import lombok.Data;

/**
 * @ClassName: ApiParamBean
 * @Author: God Man Qiu~
 * @Date: 2019/10/28 11:05
 */
@Data
public class ApiParamBean {
    //默认参数
    private String statusCode;
    private String statusName;
    private String typeCode;
    private String typeName;
    //主题
    private String subject;
    private String[] equipments;
    private String businessPartner;


    private String installInstaller; //安装人
    private String installDate;  //安装时间
    private String installCec;  //cec
    private String abn;   //abn\
    private String postCode;
    private String customerContact;  //维保公司名
    private String contactEmial;   //邮件
    private String contactNumber; //number
    private String customerAddress; //安装地址
    private String businessName; //终端客户
    private String electricalComplianceCertificate;  //电气合格证
    private String invliceUpload;
    //Invoice_Uploaded("A20288A9EAD24CC28564B4EEA7B7968F",""),
    private String faultType; //担保类型
    private String faultDescription;  //fault
    private String lcdMessage; //faultMassage =  Status_Message_LCD
    private String isWeather;
    private String battery; //Intervter_Connect_Battery
    private String suggestions;
    private String contryCode;
    private String totalAmount;//AMOUNT_EXCL_GST
    private String amountGST;
    private String gst;
    private String purchaseOrder;
    private String remark;
    private String location;
    private String batteryModel;
    private String reference;
    private String currency;
    private String shippingAddress;
    private String accessory;
}
