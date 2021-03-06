package com.crazy.portal.bean.maintenance;

import lombok.Data;

/**
 * @ClassName: AddressBean
 * @Author: God Man Qiu~
 * @Date: 2019/10/24 00:08
 */
@Data
public class AddressBean {
    private String addressType;
    private String contryCode;
    private String cityName;
    private String stateName;
    private String postCode;
    private String addressLine1;
    private String addressLine2;

    private String installAddress;

    private String shippingAddress;
}
