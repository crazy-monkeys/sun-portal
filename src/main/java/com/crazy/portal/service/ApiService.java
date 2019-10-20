package com.crazy.portal.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crazy.portal.bean.api.BaseParamsBean;
import com.crazy.portal.bean.api.DeviceInfoBean;
import com.crazy.portal.bean.api.RequestBodyBean;
import com.crazy.portal.bean.api.UdfValuesBean;
import com.crazy.portal.bean.api.token.TokenBean;
import com.crazy.portal.bean.common.Constant;
import com.crazy.portal.bean.maintenance.MaintenanceBean;
import com.crazy.portal.config.exception.BusinessException;
import com.crazy.portal.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: ApiService
 * @Author: God Man Qiu~
 * @Date: 2019/10/14 22:38
 */
@Slf4j
@Service
public class ApiService {
    private static String TOKEN_ROOT_URL;
    private static String CALL_ROOT_URL;
    private static String AUTHORIZATION;
    private static String TYPE;
    private static String NAME;
    private static String PWD;
    private static String MIMETYPE;

    @Value("${coresuite.api.token-root-url}")
    public void setTokenRootUrl(String tokenRootUrl) {
        TOKEN_ROOT_URL = tokenRootUrl;
    }

    @Value("${coresuite.api.call-root-url}")
    public void setCallRootUrl(String callRootUrl) {
        CALL_ROOT_URL = callRootUrl;
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
             String url = String.format("%s%s",TOKEN_ROOT_URL,"/oauth2/v1/token");
             String params = String.format("grant_type=%s&username=%s&password=%s",TYPE,NAME,PWD);
             Map<String, String> header = new HashMap<>();
             header.put(Constant.Authorization,AUTHORIZATION);
             String response = HttpClientUtils.post(url, params, MIMETYPE, header);
             BusinessUtil.notNull(response, ErrorCodes.SystemManagerEnum.TOKEN_IS_NULL);
             return JSONObject.parseObject(response, TokenBean.class);
         }catch (Exception e){
             log.error("接口异常:"+e);
             throw new BusinessException("token 获取失败",e);
         }
    }

    /**
     * 获取设备信息
     * @param serialNumber 序列号
     */
    public DeviceInfoBean getDeviceInfo(String serialNumber) throws Exception{
        String url = String.format("%s%s%s",CALL_ROOT_URL,"/data/query/v1");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("query"," select eq.udf.Z_Model_excl from Equipment eq where eq.serialNumber = '"+serialNumber+"'");
        String body = JSON.toJSONString(jsonObject);
        String response = this.invokeApi(url, body, Enums.Api_Header_Dtos.product);
        return JSONObject.parseObject(response, DeviceInfoBean.class);
    }

    /**
     *
     * @param bean
     * @param equipments 设备id数组
     */
    public void maintenaceApi(MaintenanceBean bean, String[] equipments, String businessPartner)throws Exception{
        RequestBodyBean requestBodyBean = new RequestBodyBean();
        List<UdfValuesBean> params = new ArrayList<>();

        Map<String, String> mapStr = BeanUtils.transBeanMapStr(bean);
        for(Map.Entry<String, String> entry : mapStr.entrySet()){
            UdfValuesBean param = new UdfValuesBean();
            String paramId = Enums.API_PARAMS.Customer_Contact.getId(entry.getKey());
            if(StringUtil.isNotEmpty(paramId)){
                param.setMeta(paramId);
                param.setValue(entry.getKey());
                params.add(param);
            }
        }
        requestBodyBean.setUdfValues(params);

        requestBodyBean.setEquipments(equipments);
        requestBodyBean.setBusinessPartner(businessPartner);

        String url = String.format("%s%s",CALL_ROOT_URL,"/data/v4/ServiceCall?");
        String response = this.invokeApi(url, JSON.toJSONString(requestBodyBean), Enums.Api_Header_Dtos.callservice);
        System.out.println(response);
    }

    public String invokeApi(String url, String body, Enums.Api_Header_Dtos dtos) throws Exception{
        TokenBean tokenBean = this.getToken();

        Map<String,String> header = this.getHeader(tokenBean);
        String account = tokenBean.getAccount();
        String company = tokenBean.getCompanies().get(0).getName();
        String user = tokenBean.getUser();
        BaseParamsBean baseParamsBean = new BaseParamsBean(account, company, user, header, dtos.getValue());
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
        Matcher m = pattern.matcher(AUTHORIZATION);
        String secret = "";
        if(m.find()){
            secret = m.group(1);
        }
        /*if(!secret.isEmpty()){
            header.put("x-client-id", Base64.decode(secret.getBytes()).toString().split(":")[0]);
        }*/
        header.put("x-client-id", "123d01ed-e117-4ade-afc4-7e697aa4594f");
        header.put("x-client-version", "1.0");
        header.put("Content-Type", "application/json");
        return header;
    }
}
