package com.crazy.portal.bean.api.batch;

import lombok.Data;

/**
 * @ClassName: InventoryBean
 * @Author: God Man Qiu~
 * @Date: 2019/11/9 14:49
 */
@Data
public class InventoryBean {
    private String item;
    private String warehouse;
    private String qty;
}
