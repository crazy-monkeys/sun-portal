package com.crazy.portal.bean.api.token;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: TokenBean
 * @Author: God Man Qiu~
 * @Date: 2019/10/15 23:50
 */
@Data
public class TokenBean {
    private String access_token;
    private String token_type;
    private Integer expires_in;
    private String scope;
    private String account;
    private Integer account_id;
    private String user;
    private String user_email;
    private Integer user_id;
    private List<CompaniesBean> companies;
    private String authorities;
    private String cluster_url;
    private String dtos="ServiceCall.25";
}
