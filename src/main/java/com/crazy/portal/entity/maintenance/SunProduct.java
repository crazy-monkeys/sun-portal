package com.crazy.portal.entity.maintenance;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品信息
 * @author weiying
 * @date   2019-10-15 01:24::58
 */
public class SunProduct {
    /**
     * 
     */
    private Integer proId;

    /**
     * 上游产品id
     */
    private String productId;

    /**
     * 产品序号
     */
    private String productNumber;

    /**
     * 产品型号
     */
    private String productModel;

    /**
     * 认证类型
     */
    private String warrantyType;

    /**
     * 采购订单
     */
    private String purchaseOrder;

    /**
     * 产品附件
     */
    private String productAccessory;

    /**
     * 安装时间
     */
    private Date installDate;

    /**
     * 行价格
     */
    private BigDecimal amount;

    /**
     * 
     */
    private Date insertTime;
}