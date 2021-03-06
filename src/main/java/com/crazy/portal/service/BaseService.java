package com.crazy.portal.service;

import com.alibaba.fastjson.JSONObject;
import com.crazy.portal.bean.api.BaseParamsBean;
import com.crazy.portal.bean.api.token.TokenBean;
import com.crazy.portal.bean.common.Constant;
import com.crazy.portal.config.exception.BusinessException;
import com.crazy.portal.util.BusinessUtil;
import com.crazy.portal.util.Enums;
import com.crazy.portal.util.ErrorCodes;
import com.crazy.portal.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 23:08 2019-10-21
 * @Modified by:
 */
@Component
@Slf4j
public class BaseService {

    @Value("${coresuite.api.token-root-url}")
    protected String tokenRootUrl;

    @Value("${coresuite.api.call-root-url}")
    protected String callRootUrl;

    @Value("${coresuite.authorization}")
    protected String authorization;

    @Value("${coresuite.grant_type}")
    protected String grantType;

    @Value("${coresuite.username}")
    protected String username;

    @Value("${coresuite.password}")
    protected String password;

    @Value("${coresuite.mimeType}")
    protected String mimeType;

    /**
     * 获取token
     * @return
     */
    protected TokenBean getToken(){
        try{
            String url = String.format("%s%s", tokenRootUrl,"/oauth2/v1/token");
            String params = String.format("grant_type=%s&username=%s&password=%s", grantType, username, password);
            Map<String, String> header = new HashMap<>();
            header.put(Constant.Authorization, authorization);
            String response = HttpClientUtils.post(url, params, mimeType, header);
            BusinessUtil.notNull(response, ErrorCodes.SystemManagerEnum.TOKEN_IS_NULL);
            return JSONObject.parseObject(response, TokenBean.class);
        }catch (Exception e){
            log.error("接口异常:"+e);
            throw new BusinessException("token 获取失败",e);
        }
    }

    /**
     * 封装通用调用api
     * @param url
     * @param body
     * @param dtos
     * @return
     * @throws Exception
     */
    protected String invokeApi(String url, String body, Enums.Api_Header_Dtos dtos) throws Exception{
        TokenBean tokenBean = this.getToken();

        Map<String,String> header = this.buildHeader(tokenBean);
        String account = tokenBean.getAccount();
        String company = tokenBean.getCompanies().get(0).getName();
        String user = tokenBean.getUser();
        BaseParamsBean baseParamsBean = new BaseParamsBean(account, company, user, header, dtos.getValue());
        String buildFinalUrl = String.format("%s?%s",url,baseParamsBean.toString());
        log.info(">>>>> API url to access:"+buildFinalUrl);
        log.info(">>>>>API Param :"+body);
        String response = HttpClientUtils.post(buildFinalUrl, body, "application/json", header);
        log.info(">>>>> API return "+response);
        if(response.isEmpty()){
            throw new RuntimeException("error invoke");
        }
        return response;
    }

    /**
     * 构建接口调用头信息
     * @param tokenBean
     * @return
     */
    private Map<String,String> buildHeader(TokenBean tokenBean){
        Map<String,String> header = new HashMap<>();
        header.put(Constant.Authorization,String.format("%s %s",tokenBean.getToken_type(),tokenBean.getAccess_token()));
        header.put("Accept",MediaType.APPLICATION_JSON_VALUE);
        header.put("Cache-Control","no-cache");

        Pattern pattern = Pattern.compile("Basic (.*)");
        Matcher m = pattern.matcher(authorization);
        if(m.find()){
            String secret = new String(Base64Utils.decode(m.group(1).getBytes()));
            header.put("x-client-id", secret.split(":")[0]);
        }
        header.put("x-client-version", "1.0");
        header.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return header;
    }
}
