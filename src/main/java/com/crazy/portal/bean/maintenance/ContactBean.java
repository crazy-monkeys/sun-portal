package com.crazy.portal.bean.maintenance;

import lombok.Data;

/**
 * @ClassName: ContactBean
 * @Author: God Man Qiu~
 * @Date: 2019/10/24 00:17
 */
@Data
public class ContactBean {
    //1-business 2-invoice
    private String billType;
    //firstname
    private String contactFirstName;
    //last name
    private String contactLastName;
    //联系人邮箱
    private String contactEmail;
    //收件邮箱
    private String sendEmail;
    //number
    private String contactNumber;

    private String businessName;

    //服务请求 公司的contactParson 和 enduser 的name
    private String person;

    private String abn;

    private AddressBean address;
}
