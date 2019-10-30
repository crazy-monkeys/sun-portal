package com.crazy.portal.bean.api;

import lombok.Data;

/**
 * @ClassName: ActivityBean
 * @Author: God Man Qiu~
 * @Date: 2019/10/29 02:36
 */
@Data
public class ActivityBean {
    private String subject;
    private String type="ASSIGNMENT";
    private String businessPartner;
    private String syncStatus="IN_CLOUD";
    private String executionStage="DISPATCHING";
    private Boolean inactive=false;
    private String status;
    private ObjectBean object;
    private String equipments;
    private String externalId;
    private String startDateTime;
    private String endDateTime;
    private String earliestStartDateTime;
    private String dueDateTime;
    private String subType="E911C630FCD2478E84B003F4233CF5B4";
}
