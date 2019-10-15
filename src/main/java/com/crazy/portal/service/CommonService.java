package com.crazy.portal.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crazy.portal.bean.api.BaseParamsBean;
import com.crazy.portal.bean.api.DeviceInfoBean;
import com.crazy.portal.bean.api.token.TokenBean;
import com.crazy.portal.bean.common.Constant;
import com.crazy.portal.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.apache.xmlbeans.impl.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 00:14 2019-10-16
 * @Modified by:
 */
@Slf4j
@Service
public class CommonService {

    @Resource
    private ApiService apiService;

    @Value("${coresuite.api.root-url}")
    private String rootUrl;

    @Value("${coresuite.authorization}")
    private String authorization;

    /**
     * 获取设备信息
     * @param serialNumber 序列号
     */
    public DeviceInfoBean getDeviceInfo(String serialNumber) throws Exception{

        String url = String.format("%s%s%s",rootUrl,"/data/query/v1");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("query"," select eq.udf.Z_Model_excl from Equipment eq where eq.serialNumber = '"+serialNumber+"'");
        String body = JSON.toJSONString(jsonObject);
        String response = this.invokeApi(url, body);
        return JSONObject.parseObject(response, DeviceInfoBean.class);
    }

    public String invokeApi(String url, String body) throws Exception{
        TokenBean tokenBean = apiService.getToken();

        Map<String,String> header = this.getHeader(tokenBean);
        String account = tokenBean.getAccount();
        String company = tokenBean.getCompanies().getName();
        String user = tokenBean.getUser();
        BaseParamsBean baseParamsBean = new BaseParamsBean(account, company, user,header);
        String buildUrl = String.format("%s%s",url,baseParamsBean.toString());

        String response = HttpClientUtils.post(buildUrl, body, "application/json", header);
        if(response.isEmpty()){
            throw new RuntimeException("error invoke");
        }
        return response;
    }

    public Map<String,String> getHeader(TokenBean tokenBean){
        Map<String,String> header = new HashMap<>();
        header.put(Constant.Authorization,String.format("%s %s",tokenBean.getToken_type(),tokenBean.getAccess_token()));
        header.put("Accept","application/json");
        header.put("Cache-Control","no-cache");

        Pattern pattern = Pattern.compile("Basic (.*)");
        Matcher m = pattern.matcher(authorization);
        String secret = "";
        if(m.find()){
            secret = m.group(1);
        }
        if(!secret.isEmpty()){
            header.put("x-client-id", Base64.decode(secret.getBytes()).toString().split(":")[0]);
        }
        header.put("x-client-version", "1.0");
        header.put("Content-Type", "application/json");
        return header;
    }
}
