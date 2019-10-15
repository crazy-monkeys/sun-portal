package com.crazy.portal.bean.api;

import lombok.Data;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 00:22 2019-10-16
 * @Modified by:
 */
@Data
public class DeviceInfoBean {

    /**
     * 设备ID
     */
    private String id;
    /**
     * 合作伙伴ID
     */
    private String businessPartner;
    /**
     * 自定义字段ID(设备型号)
     */
    private String meta;
    /**
     * 设备型号值
     */
    private String value;
    /**
     * 自定义字段名（设备型号）
     */
    private String name;
    /**
     * 物料ID
     */
    private String item;
}
