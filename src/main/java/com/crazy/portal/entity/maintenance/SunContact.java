package com.crazy.portal.entity.maintenance;

import lombok.Data;

import java.util.Date;

/**
 * 联系人
 * @author weiying
 * @date   2019-10-15 01:24::58
 */
@Data
public class SunContact {
    /**
     * 
     */
    private Integer contactId;

    /**
     * 
     */
    private Integer maintenanceId;

    /**
     * 1-Business 2-Individual
     */
    private Integer type;

    /**
     * 1-Claimant 2-endUser
     */
    private Integer contactType;

    /**
     * 公司名
     */
    private String contactBusinessName;

    /**
     * 公司联系人
     */
    private String contactPerson;

    /**
     * 姓
     */
    private String contactFirstName;

    /**
     * 名
     */
    private String contactLastName;

    /**
     * 联系人邮箱
     */
    private String contactEmial;

    /**
     *接收邮件邮箱
     */
    private String sendEmial;

    /**
     * 联系电话
     */
    private String contactNumber;

    /**
     * 
     */
    private Date insertTime;
}