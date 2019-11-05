package com.crazy.portal.bean.api.device;

import lombok.Data;

import java.util.List;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 02:24 2019-10-21
 * @Modified by:
 */
@Data
public class DeviceEq {

    //设备id
    private String id;
    //合作伙伴id
    private String businessPartner;
    /**
     * 物料ID
     */
    private String item;
    private List<UdfValuesBean> udfValues;
}
