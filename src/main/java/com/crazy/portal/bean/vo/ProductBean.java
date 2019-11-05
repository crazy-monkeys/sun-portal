package com.crazy.portal.bean.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: ProductBean
 * @Author: God Man Qiu~
 * @Date: 2019/10/24 00:07
 */
@Data
public class ProductBean {
    //产品id
    private String productId;
    //产品型号
    private String productModel;

    //TODO 前端保存并返回
    private String item = "322EA4550BAC4C7E87CD407C1EB43EA1";

    private String productNumber;

    private String deliveryDate;

    private String warrantyType;

    private String purchaseOrder;

    private BigDecimal amount;

    private String businessPartner;
}
