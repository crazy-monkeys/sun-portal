package com.crazy.portal.bean.api.inventory;

import lombok.Data;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 23:38 2019-10-30
 * @Modified by:
 */
@Data
public class InventoryInfoReponse {

    private String ordered;
    private String committed;
    private String item;
    private String lastChanged;
    private String createPerson;
    private String externalid;
    private String groups;
    private String owners;
    private String warehouse;
    private String branched;
}
