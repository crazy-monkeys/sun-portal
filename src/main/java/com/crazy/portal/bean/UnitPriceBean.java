package com.crazy.portal.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName: UnitPriceBean
 * @Author: God Man Qiu~
 * @Date: 2019/10/29 00:40
 */
@Data
public class UnitPriceBean {
    private BigDecimal amount;
    private String currency;
}
