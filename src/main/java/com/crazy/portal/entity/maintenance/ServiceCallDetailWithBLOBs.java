package com.crazy.portal.entity.maintenance;

/**
 * 
 * @author weiying
 * @date   2019-10-15 01:24::58
 */
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

    public String getFaultMassage() {
        return faultMassage;
    }

    public void setFaultMassage(String faultMassage) {
        this.faultMassage = faultMassage == null ? null : faultMassage.trim();
    }

    public String getFaultDescription() {
        return faultDescription;
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription == null ? null : faultDescription.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}