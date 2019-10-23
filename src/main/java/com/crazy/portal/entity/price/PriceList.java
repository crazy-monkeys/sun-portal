package com.crazy.portal.entity.price;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author Bill
 * @date   2019-10-24 00:42::15
 */
@Data
public class PriceList {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String model;

    /**
     * 
     */
    private BigDecimal partEarlyBirdDiscount;

    /**
     * 
     */
    private BigDecimal standardEarlyBirdDiscount;

    /**
     * 
     */
    private BigDecimal partStandard;

    /**
     * 
     */
    private BigDecimal standardStandard;

    /**
     * 
     */
    private Integer active;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;
}