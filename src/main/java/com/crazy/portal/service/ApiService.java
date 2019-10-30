package com.crazy.portal.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.crazy.portal.bean.api.ApiParamBean;
import com.crazy.portal.bean.api.device.DeviceInfoBean;
import com.crazy.portal.bean.api.RequestBodyBean;
import com.crazy.portal.bean.api.device.UdfValuesBean;
import com.crazy.portal.bean.vo.MaintenanceBean;
import com.crazy.portal.config.exception.BusinessException;
import com.crazy.portal.util.BeanUtils;
import com.crazy.portal.util.DateUtil;
import com.crazy.portal.util.Enums;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 23:08 2019-10-21
 * @Modified by:
 */
@Slf4j
@Service
public class ApiService extends BaseService{

    /**
     * 获取设备信息
     * @param serialNumber 序列号
     */
    public DeviceInfoBean getDeviceInfo(String serialNumber){
        try{String url = String.format("%s%s", super.callRootUrl,"/data/query/v1");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("query"," select eq.id,eq.businessPartner,eq.udf.Z_Model_excl,eq.udf.Z_DispatchDate_local" +
                    " from Equipment eq where eq.serialNumber = '"+serialNumber+"'");

            String response = super.invokeApi(url, JSON.toJSONString(jsonObject), Enums.Api_Header_Dtos.EQUIPMENT20);

            if(response.contains("error")) return null;

            return JSONObject.parseObject(response, DeviceInfoBean.class);
        }catch (Exception e){
            throw new BusinessException("",e);
        }
    }

    /**
     * 获取设备信息地址信息(用于检查水货问题)
     * E79E7FDBE94C4D728C15ADB1E8055609
     * @param objectId 设备ID
     * @return
     */
    public String getDeviceAddressInfo(String objectId){
        try {
            String url = String.format("%s%s",super.callRootUrl,"/data/query/v1");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("query"," select adrs.country FROM Address adrs " +
                    "where adrs.object.objectId = '"+objectId+"' and adrs.object.objectType = 'EQUIPMENT'");

            String response = super.invokeApi(url, JSON.toJSONString(jsonObject), Enums.Api_Header_Dtos.ADDRESS18);

            JSONObject adrsObject = this.getApiData(response);
            if (adrsObject == null) return null;

            JSONObject adrs = (JSONObject) adrsObject.get("adrs");
            if(adrs == null) return null;

            Object country = adrs.get("country");
            return country == null ? null : String.valueOf(country);
        } catch (Exception e) {
            log.error("",e);
            throw new BusinessException("",e);
        }
    }

    /**
     * 获取设备容量信息
     * A82DD58BA85C4387BBC42DDFE813F5A8
     * @param id 物料ID
     * @return
     */
    public UdfValuesBean getDevicePowerInfo(String id){
        try {
            String url = String.format("%s%s",super.callRootUrl,"/data/query/v1");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("query"," SELECT itt.udf.Z_Capacity FROM Item itt where itt.id = '"+id+"'");

            String response = super.invokeApi(url, JSON.toJSONString(jsonObject), Enums.Api_Header_Dtos.ITEM22);

            JSONObject adrsObject = this.getApiData(response);
            if (adrsObject == null) return null;

            JSONObject itt = (JSONObject) adrsObject.get("itt");
            if(itt == null) return null;

            JSONArray udfValues = (JSONArray) itt.get("udfValues");
            if(udfValues == null) return null;

            Object devicePowerObj = udfValues.get(0);
            if(devicePowerObj == null) return null;

            return JSON.parseObject(JSON.toJSONString(devicePowerObj),UdfValuesBean.class);
        } catch (Exception e) {
            log.error("",e);
            throw new BusinessException("",e);
        }
    }

    /**
     * 封装提取返回体中的data
     * @param response
     * @return
     */
    private JSONObject getApiData(String response) {
        JSONObject dataArray = JSON.parseObject(response, JSONObject.class);
        if (dataArray == null) return null;

        JSONArray jsonArray = (JSONArray) dataArray.get("data");
        if (jsonArray == null) return null;

        JSONObject adrsObject = (JSONObject) jsonArray.get(0);
        if (adrsObject == null) return null;
        return adrsObject;
    }

    /**
     *service call
     * @param bean
     */
    public void serviceCall(ApiParamBean bean)throws Exception{
        String url = String.format("%s%s", callRootUrl,"/data/v4/ServiceCall");
        String response = super.invokeApi(url, JSON.toJSONString(getParam(bean)), Enums.Api_Header_Dtos.SERVICECALL25);
        System.out.println(response);
    }

    public void materialCall(String param,String function)throws Exception{
        String url = String.format("%s%s", callRootUrl,function);
        String response = super.invokeApi(url, param, Enums.Api_Header_Dtos.SERVICECALL25);
        System.out.println(response);
    }

    private RequestBodyBean getParam(ApiParamBean bean){
        RequestBodyBean requestBodyBean = new RequestBodyBean();
        List<UdfValuesBean> params = new ArrayList<>();

        Map<String, String> mapStr = BeanUtils.transBeanMapStr(bean);
        for(Map.Entry<String, String> entry : mapStr.entrySet()){
            UdfValuesBean param = new UdfValuesBean();
            String paramId = Enums.API_PARAMS.Customer_Contact.getId(entry.getKey());
            if(StringUtils.isNotEmpty(entry.getValue())&&StringUtils.isNotEmpty(paramId)){
                param.setMeta(paramId);
                param.setValue(entry.getValue());
                params.add(param);
            }
        }

        requestBodyBean.setStatusCode(bean.getStatusCode());
        requestBodyBean.setStatusName(bean.getStatusName());
        requestBodyBean.setSubject(bean.getSubject());
        requestBodyBean.setUdfValues(params);
        requestBodyBean.setEquipments(bean.getEquipments());
        requestBodyBean.setBusinessPartner(bean.getBusinessPartner());
        return requestBodyBean;
    }
}
