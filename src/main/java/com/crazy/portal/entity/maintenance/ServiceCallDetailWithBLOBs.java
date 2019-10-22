package com.crazy.portal.entity.maintenance;

import lombok.Data;

/**
 * 
 * @author weiying
 * @date   2019-10-15 01:24::58
 */
@Data
public class ServiceCallDetailWithBLOBs extends ServiceCallDetail {
    /**
     * 
     */
    private String faultMassage;

    /**
     * 
     */
    private String faultDescription;

    /**
     * 
     */
    private String remark;
}