package com.crazy.portal.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crazy.portal.bean.api.BaseParamsBean;
import com.crazy.portal.bean.api.device.DeviceInfoBean;
import com.crazy.portal.bean.api.RequestBodyBean;
import com.crazy.portal.bean.api.device.UdfValuesBean;
import com.crazy.portal.bean.api.token.TokenBean;
import com.crazy.portal.bean.common.Constant;
import com.crazy.portal.bean.maintenance.MaintenanceBean;
import com.crazy.portal.config.exception.BusinessException;
import com.crazy.portal.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.*;
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

    @Value("${coresuite.api.token-root-url}")
    private String tokenRootUrl;

    @Value("${coresuite.api.call-root-url}")
    private String callRootUrl;

    @Value("${coresuite.authorization}")
    private String authorization;

    @Value("${coresuite.grant_type}")
    private String grantType;

    @Value("${coresuite.username}")
    private String username;

    @Value("${coresuite.password}")
    private String password;

    @Value("${coresuite.mimeType}")
    private String mimeType;


    /**
     * 获取token
     * @return
     */
    public TokenBean getToken(){
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


    class DeviceInfo{

    }
    /**
     * 获取设备信息
     * @param serialNumber 序列号
     */
    public DeviceInfoBean getDeviceInfo(String serialNumber) throws Exception{
        String url = String.format("%s%s", callRootUrl,"/data/query/v1");
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

        String url = String.format("%s%s", callRootUrl,"/data/v4/ServiceCall?");
        String response = this.invokeApi(url, JSON.toJSONString(requestBodyBean), Enums.Api_Header_Dtos.callservice);
        System.out.println(response);
    }


    /**
     * 封装通用调用api
     * @param url
     * @param body
     * @param dtos
     * @return
     * @throws Exception
     */
    private String invokeApi(String url, String body, Enums.Api_Header_Dtos dtos) throws Exception{
        TokenBean tokenBean = this.getToken();

        Map<String,String> header = this.buildHeader(tokenBean);
        String account = tokenBean.getAccount();
        String company = tokenBean.getCompanies().get(0).getName();
        String user = tokenBean.getUser();
        BaseParamsBean baseParamsBean = new BaseParamsBean(account, company, user, header, dtos.getValue());
        String buildFinalUrl = String.format("%s?%s",url,baseParamsBean.toString());
        log.info(">>>>> API url to access:"+buildFinalUrl);
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
        header.put("Accept","application/json");
        header.put("Cache-Control","no-cache");

        Pattern pattern = Pattern.compile("Basic (.*)");
        Matcher m = pattern.matcher(authorization);
        if(m.find()){
            String secret = new String(Base64Utils.decode(m.group(1).getBytes()));
            header.put("x-client-id", secret.split(":")[0]);
        }
        header.put("x-client-version", "1.0");
        header.put("Content-Type", "application/json");
        return header;
    }
}
