package com.crazy.portal.entity.system;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 23:32 2019-09-15
 * @Modified by:
 */
@Data
public class OperationLogDO {

    public OperationLogDO(Date createTime) {
        this.createTime = createTime;
    }

    private String operator;
    private String url;
    private String invoke;
    private String businessKey;
    private String errorMsg;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
