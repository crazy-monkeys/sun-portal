package com.crazy.portal.bean.api;

import lombok.Data;

import java.util.Map;
import java.util.Objects;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 00:42 2019-10-16
 * @Modified by:
 */
@Data
public class ParamsBean {

    public ParamsBean(String account, String company, String user, Map<String, String> header, String dtos,Boolean forceUpdate) {
        this.header = header;
        this.account = account;
        this.company = company;
        this.user = user;
        this.dtos = dtos;
        this.forceUpdate = forceUpdate;
    }

    private Map<String, String> header;
    private Boolean forceUpdate;
    private String dtos;
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
        sb.append("user=").append(user);
        if(Objects.nonNull(forceUpdate)){
            sb.append("&forceUpdate=").append(forceUpdate);
        }
        return sb.toString();
    }
}
