package com.crazy.portal.entity.maintenance;

import java.util.Date;

/**
 * 
 * @author weiying
 * @date   2019-10-15 01:24::58
 */
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

    public Integer getCallId() {
        return callId;
    }

    public void setCallId(Integer callId) {
        this.callId = callId;
    }

    public Integer getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(Integer maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public Integer getFault() {
        return fault;
    }

    public void setFault(Integer fault) {
        this.fault = fault;
    }

    public Integer getIsWeather() {
        return isWeather;
    }

    public void setIsWeather(Integer isWeather) {
        this.isWeather = isWeather;
    }

    public String getSungrowStaff() {
        return sungrowStaff;
    }

    public void setSungrowStaff(String sungrowStaff) {
        this.sungrowStaff = sungrowStaff == null ? null : sungrowStaff.trim();
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress == null ? null : shippingAddress.trim();
    }

    public String getToShippingAddress() {
        return toShippingAddress;
    }

    public void setToShippingAddress(String toShippingAddress) {
        this.toShippingAddress = toShippingAddress == null ? null : toShippingAddress.trim();
    }

    public Integer getInverterConnect() {
        return inverterConnect;
    }

    public void setInverterConnect(Integer inverterConnect) {
        this.inverterConnect = inverterConnect;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}