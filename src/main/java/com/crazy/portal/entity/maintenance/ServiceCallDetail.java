package com.crazy.portal.entity.maintenance;

import lombok.Data;

import java.util.Date;

/**
 * 
 * @author weiying
 * @date   2019-10-15 01:24::58
 */
@Data
public class ServiceCallDetail {
    /**
     * 
     */
    private Integer callId;

    /**
     * 
     */
    private Integer maintenanceId;

    /**
     * 问题 1-Permanent 2-Intermittent
     */
    private Integer fault;

    /**
     * 是否暴露 1-是 0-否
     */
    private Integer isWeather;

    /**
     * 
     */
    private String sungrowStaff;

    /**
     * 
     */
    private String shippingAddress;

    /**
     * 
     */
    private String toShippingAddress;

    /**
     * 逆变器是否与电池连接 1-是 0-否
     */
    private Integer inverterConnect;

    /**
     * 
     */
    private Date insertTime;
}