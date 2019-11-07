package com.crazy.portal.bean.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: MultipleProduct
 * @Author: God Man Qiu~
 * @Date: 2019/11/7 23:51
 */
@Data
public class MultipleProduct {
    private String type;
    private String numberList;

    private Integer item;
    private BigDecimal exclGst;
    private BigDecimal gst;
    private BigDecimal inclGst;

    private List<ProductBean> products;
}
