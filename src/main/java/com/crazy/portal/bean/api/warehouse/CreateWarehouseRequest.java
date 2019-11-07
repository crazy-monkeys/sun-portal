package com.crazy.portal.bean.api.warehouse;

import lombok.Data;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 01:06 2019-11-07
 * @Modified by:
 */
@Data
public class CreateWarehouseRequest {

    /**
     * 仓库号
     */
    private String code;
    /**
     * 仓库名称
     */
    private String name;
    /**
     * Reserved material warehouse
     */
    private boolean reservedMaterialWarehouse;
}
