package com.crazy.portal.bean.maintenance;

import lombok.Data;

/**
 * @ClassName: ServiceCallBean
 * @Author: God Man Qiu~
 * @Date: 2019/10/24 00:26
 */
@Data
public class ServiceCallBean {
    //The fault is : Permanent Intermittent
    private String fault;
    //Status message on the LCD:
    private String lcd;
    //Fault description:
    private String description;
    //Is the inverter exposed to the weather (e.g. rain)?  : yes no
    private String weather;
    private String weatherMessage;
    //Does the inverter connect with a battery : yes no
    private String battery;
    private String batteryMessage;

    private String shippingAddress;
}
