package com.crazy.portal.bean.api;

import com.crazy.portal.bean.UnitPriceBean;
import lombok.Data;

/**
 * @ClassName: MaterialRequestBodyBean
 * @Author: God Man Qiu~
 * @Date: 2019/10/29 00:34
 */
@Data
public class MaterialRequestBodyBean {
    private String item;
    private String equipment;
    private Integer quantity=1;
    //private String chargeOption = "CHARGEABLE";
    private ObjectBean object;
    private UnitPriceBean unitPrice;
}
