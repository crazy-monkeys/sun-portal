package com.crazy.portal.bean.api.device;

import lombok.Data;

/**
 * @ClassName: UdfValuesBean
 * @Author: God Man Qiu~
 * @Date: 2019/10/16 01:02
 */
@Data
public class UdfValuesBean {
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
