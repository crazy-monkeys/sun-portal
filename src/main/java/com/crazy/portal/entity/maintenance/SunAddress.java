package com.crazy.portal.entity.maintenance;

import lombok.Data;

import java.util.Date;

/**
 * 
 * @author weiying
 * @date   2019-10-15 01:24::58
 */
@Data
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
}