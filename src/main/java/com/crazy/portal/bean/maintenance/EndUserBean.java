package com.crazy.portal.bean.maintenance;

import lombok.Data;

/**
 * @ClassName: EndUserBean
 * @Author: God Man Qiu~
 * @Date: 2019/10/24 01:50
 */
@Data
public class EndUserBean {
    //联系人邮箱
    private String contactEmail;
    //number
    private String contactNumber;

    // enduser 的name
    private String person;

    private AddressBean address;
}
