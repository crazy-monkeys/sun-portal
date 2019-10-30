package com.crazy.portal.bean.api.warehouse;

import lombok.Data;

import java.util.List;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 23:54 2019-10-30
 * @Modified by:
 */
@Data
public class WarehouseOwnerRequest {

    private String reservedMaterialWarehouse;
    private List<String> owners;
}
