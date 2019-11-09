package com.crazy.portal.bean.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: MultipleProductResponse
 * @Author: God Man Qiu~
 * @Date: 2019/11/8 00:23
 */
@Data
public class MultipleProductResponse {
    private Integer item;
    private BigDecimal exclGst;
    private BigDecimal gst;
    private BigDecimal inclGst;

    private List<ProductBean> products;
}
