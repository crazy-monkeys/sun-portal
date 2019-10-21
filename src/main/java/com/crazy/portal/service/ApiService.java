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
import org.apache.cxf.Bus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            String response = super.invokeApi(url, body, Enums.Api_Header_Dtos.product);
            return JSONObject.parseObject(response, DeviceInfoBean.class);
        }catch (Exception e){
            throw new BusinessException("",e);
        }
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

        String url = String.format("%s%s", callRootUrl,"/data/v4/ServiceCall");
        String response = this.invokeApi(url, JSON.toJSONString(requestBodyBean), Enums.Api_Header_Dtos.callservice);
        System.out.println(response);
    }



}
