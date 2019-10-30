package com.crazy.portal.bean.api.inventory;

import lombok.Data;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 23:33 2019-10-30
 * @Modified by:
 */
@Data
public class InventoryInfoRequest {

    private String item;
    private String warehouse;
    private String inStock;
}
