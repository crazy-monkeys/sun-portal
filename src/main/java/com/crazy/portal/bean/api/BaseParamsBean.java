package com.crazy.portal.bean.api;

import com.crazy.portal.bean.common.Constant;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 00:42 2019-10-16
 * @Modified by:
 */
@Data
public class BaseParamsBean {

    private BaseParamsBean(){};

    public BaseParamsBean(String account, String company, String user,Map<String, String> header) {
        this.header = header;
        this.account = account;
        this.company = company;
        this.user = user;
    }

    private Map<String, String> header;
    private String dtos = "Equipment.20";
    private String account;
    private String company;
    private String clientIdentifier = "COR_CON_NONE";
    private String user;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("dtos=").append(dtos).append('&');
        sb.append("account=").append(account).append('&');
        sb.append("company=").append(company).append('&');
        sb.append("clientIdentifier=").append(clientIdentifier).append('&');
        sb.append("user=").append(user).append('&');
        return sb.toString();
    }
}
