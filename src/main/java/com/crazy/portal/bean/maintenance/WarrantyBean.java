package com.crazy.portal.bean.maintenance;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName: WarrantyBean
 * @Author: God Man Qiu~
 * @Date: 2019/10/24 00:58
 */
@Data
public class WarrantyBean {
    //1-parts 2-standard
    private Integer warrantyType;
    private String purchaseOrder;
    private BigDecimal amount;
}
