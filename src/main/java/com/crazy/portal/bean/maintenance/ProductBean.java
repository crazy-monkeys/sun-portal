package com.crazy.portal.bean.maintenance;

import lombok.Data;

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

    private String productNumber;

   private List<WarrantyBean> warrantys;
}
