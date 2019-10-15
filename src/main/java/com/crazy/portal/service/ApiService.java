package com.crazy.portal.service;

import com.alibaba.fastjson.JSONObject;
import com.crazy.portal.bean.api.token.TokenBean;
import com.crazy.portal.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ApiService
 * @Author: God Man Qiu~
 * @Date: 2019/10/14 22:38
 */
@Slf4j
@Service
public class ApiService {
    private static String TOKEN_API_URL;
    private static String AUTHORIZATION;
    private static String TYPE;
    private static String NAME;
    private static String PWD;
    private static String MIMETYPE;

    @Value("${coresuite.api.root-local}")
    public void setTokenApiUrl(String tokenApiUrl) {
        TOKEN_API_URL = tokenApiUrl;
    }

    @Value("${coresuite.authorization}")
    public void setAUTHORIZATION(String AUTHORIZATION) {
        ApiService.AUTHORIZATION = AUTHORIZATION;
    }

    @Value("${coresuite.grant_type}")
    public void setTYPE(String TYPE) {
        ApiService.TYPE = TYPE;
    }

    @Value("${coresuite.username}")
    public void setNAME(String NAME) {
        ApiService.NAME = NAME;
    }

    @Value("${coresuite.password}")
    public void setPWD(String PWD) {
        ApiService.PWD = PWD;
    }

    @Value("${coresuite.mimeType}")
    public void setMIMETYPE(String MIMETYPE) {
        ApiService.MIMETYPE = MIMETYPE;
    }

    public TokenBean getToken(){
         try{
             String params = String.format("grant_type=%s&username=%s&password=%s",TYPE,NAME,PWD);
             String response = HttpClientUtils.post(TOKEN_API_URL, params, MIMETYPE, AUTHORIZATION);
             if(null != response){
                 return JSONObject.parseObject(response, TokenBean.class);
             }
         }catch (Exception e){
             log.error("接口异常:"+e);
         }
        return null;
    }
}
