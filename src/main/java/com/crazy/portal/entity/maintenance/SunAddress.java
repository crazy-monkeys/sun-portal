package com.crazy.portal.entity.maintenance;

import java.util.Date;

/**
 * 
 * @author weiying
 * @date   2019-10-15 01:24::58
 */
public class SunAddress {
    /**
     * 
     */
    private Integer addressId;

    /**
     * 
     */
    private Integer contactId;

    /**
     * 
     */
    private String contryCode;

    /**
     * 
     */
    private String contryName;

    /**
     * 
     */
    private String stateCode;

    /**
     * 
     */
    private String stateName;

    /**
     * 
     */
    private String streetCode;

    /**
     * 
     */
    private String streetName;

    /**
     * 
     */
    private String cityCode;

    /**
     * 
     */
    private String cityName;

    /**
     * 
     */
    private String detail;

    /**
     * 
     */
    private String postCode;

    /**
     * 
     */
    private String abn;

    /**
     * 
     */
    private Date insertTime;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getContryCode() {
        return contryCode;
    }

    public void setContryCode(String contryCode) {
        this.contryCode = contryCode == null ? null : contryCode.trim();
    }

    public String getContryName() {
        return contryName;
    }

    public void setContryName(String contryName) {
        this.contryName = contryName == null ? null : contryName.trim();
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode == null ? null : stateCode.trim();
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName == null ? null : stateName.trim();
    }

    public String getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(String streetCode) {
        this.streetCode = streetCode == null ? null : streetCode.trim();
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName == null ? null : streetName.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }

    public String getAbn() {
        return abn;
    }

    public void setAbn(String abn) {
        this.abn = abn == null ? null : abn.trim();
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}