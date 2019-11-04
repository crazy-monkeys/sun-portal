package com.crazy.portal.bean.api;

import com.crazy.portal.bean.api.device.UdfValuesBean;
import lombok.Data;

import java.util.List;

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
    private String status="OPEN";
    private ObjectBean object;
    private String equipment;
    private String externalId;
    private String startDateTime;
    private String endDateTime;
    private String earliestStartDateTime;
    private String dueDateTime;
    //private String subType;
    private List<UdfValuesBean> udfValues;
}
