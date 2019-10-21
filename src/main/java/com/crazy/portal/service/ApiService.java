package com.crazy.portal.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crazy.portal.bean.api.device.DeviceInfoBean;
import com.crazy.portal.bean.api.RequestBodyBean;
import com.crazy.portal.bean.api.device.DeviceInfoBean;
import com.crazy.portal.bean.api.device.UdfValuesBean;
import com.crazy.portal.bean.maintenance.MaintenanceBean;
import com.crazy.portal.config.exception.BusinessException;
import com.crazy.portal.util.BeanUtils;
import com.crazy.portal.util.Enums;
import com.crazy.portal.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.*;

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
        try{
            String url = String.format("%s%s", super.callRootUrl,"/data/query/v1");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("query"," select eq.udf.Z_Model_excl from Equipment eq where eq.serialNumber = '"+serialNumber+"'");
            String body = JSON.toJSONString(jsonObject);
            String response = super.invokeApi(url, body, Enums.Api_Header_Dtos.EQUIPMENT20);
            return JSONObject.parseObject(response, DeviceInfoBean.class);
        }catch (Exception e){
            throw new BusinessException("",e);
        }
    }

    /**
     *
     * @param bean
     */
    public String maintenaceApi(MaintenanceBean bean)throws Exception{
    public void maintenaceApi(MaintenanceBean bean, String[] equipments, String businessPartner) throws Exception{
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

        requestBodyBean.setEquipments(bean.getProductId());
        requestBodyBean.setBusinessPartner(bean.getBusinessPartner());

        String url = String.format("%s%s", callRootUrl,"/data/v4/ServiceCall");
        return this.invokeApi(url, JSON.toJSONString(requestBodyBean), Enums.Api_Header_Dtos.callservice);
        String url = String.format("%s%s", super.callRootUrl,"/data/v4/ServiceCall");
        String response = super.invokeApi(url, JSON.toJSONString(requestBodyBean), Enums.Api_Header_Dtos.SERVICECALL25);
        System.out.println(response);
    }



}
