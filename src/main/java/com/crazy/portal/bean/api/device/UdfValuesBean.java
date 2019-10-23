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
     * 自定义字段ID(设备型号)/电容量ID
     */
    private String meta;
    /**
     * 设备型号值/电容量值
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
