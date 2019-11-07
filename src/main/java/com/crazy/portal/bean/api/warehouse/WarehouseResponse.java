package com.crazy.portal.bean.api.warehouse;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 23:55 2019-10-30
 * @Modified by:
 */
@Data
public class WarehouseResponse {

    private String code;
    private Long lastChanged;
    private String createPerson;
    private String externalId;
    private String groups;
    private List<String> owners;
    private JSONObject branches;
    private boolean reservedMaterialWarehouse;
    private String createDateTime;
    private String syncObjectKPIs;
    private boolean inactive;
    private String udfMetaGroups;
    private String name;
    private String location;
    private String id;
    private JSONObject udfValues;
    private String lastChangedBy;
    private String syncStatus;
}
