package com.crazy.portal.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.crazy.portal.bean.api.ApiParamBean;
import com.crazy.portal.bean.api.attachment.AttachmentRequest;
import com.crazy.portal.bean.api.attachment.AttachmentResponse;
import com.crazy.portal.bean.api.device.DeviceInfoBean;
import com.crazy.portal.bean.api.RequestBodyBean;
import com.crazy.portal.bean.api.device.UdfValuesBean;
import com.crazy.portal.bean.api.inventory.InventoryInfoReponse;
import com.crazy.portal.bean.api.inventory.InventoryInfoRequest;
import com.crazy.portal.bean.api.warehouse.CreateWarehouseRequest;
import com.crazy.portal.bean.api.warehouse.WarehouseOwnerRequest;
import com.crazy.portal.bean.api.warehouse.WarehouseResponse;
import com.crazy.portal.config.exception.BusinessException;
import com.crazy.portal.util.BeanUtils;
import com.crazy.portal.util.Enums;
import com.crazy.portal.util.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        try{
            String url = String.format("%s%s", super.callRootUrl,"/data/query/v1");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("query"," select eq.id,eq.item,eq.businessPartner,eq.udf.Z_Model_excl,eq.udf.Z_DispatchDate_local,eq.udf.Z_DispatchedDate" +
                    " from Equipment eq where eq.serialNumber = '"+serialNumber+"'");

            String response = super.invokeApi(url, JSON.toJSONString(jsonObject), Enums.API_HEADER_DTOS.EQUIPMENT20);

            if(response.contains("error")) return null;

            return JSONObject.parseObject(response, DeviceInfoBean.class);
        }catch (Exception e){
            throw new BusinessException(ErrorCodes.CommonEnum.SYSTEM_EXCEPTION.getCode(),e.getMessage());
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

            String response = super.invokeApi(url, JSON.toJSONString(jsonObject), Enums.API_HEADER_DTOS.ADDRESS18);

            JSONObject adrsObject = this.getApiData(response);

            if (adrsObject == null) return null;

            JSONObject adrs = (JSONObject) adrsObject.get("adrs");
            if(adrs == null) return null;

            Object country = adrs.get("country");
            return country == null ? null : String.valueOf(country);
        } catch (Exception e) {
            log.error("",e);
            throw new BusinessException(ErrorCodes.CommonEnum.SYSTEM_EXCEPTION.getCode(),e.getMessage());
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

            String response = super.invokeApi(url, JSON.toJSONString(jsonObject), Enums.API_HEADER_DTOS.ITEM22);

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
            throw new BusinessException(ErrorCodes.CommonEnum.SYSTEM_EXCEPTION.getCode(),e.getMessage());
        }
    }

    /**
     * 附件上传
     * @return
     */
    public AttachmentResponse attachmentUpload(AttachmentRequest attachmentRequest){
        try {
            String url = String.format("%s%s",super.callRootUrl,"/data/v4/Attachment");
            String response = super.invokeApi(url, JSON.toJSONString(attachmentRequest), Enums.API_HEADER_DTOS.ATTACHMENT15);

            JSONObject data = this.getApiData(response);
            if (data == null) return null;

            Object attachment = data.get("attachment");
            if(attachment == null) return null;

            return JSON.parseObject(JSON.toJSONString(attachment),AttachmentResponse.class);
        } catch (Exception e) {
            log.error("",e);
            throw new BusinessException(ErrorCodes.CommonEnum.SYSTEM_EXCEPTION.getCode(),e.getMessage());
        }
    }

    /**
     * 根据物料号获取物料ID
     * @param code
     * @return
     */
    public String getMaterialIdByCode(String code){
        try {
            String url = String.format("%s%s",super.callRootUrl,"/data/query/v1");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("query"," SELECT itt.id FROM Item itt WHERE itt.code = '"+code+"'");

            String response = super.invokeApi(url, JSON.toJSONString(jsonObject), Enums.API_HEADER_DTOS.ITEM22);
            JSONObject data = this.getApiData(response);
            if (data == null) return null;

            Object itt = data.get("itt");

            if(itt == null) return null;


            JSONObject materialObj = JSON.parseObject(JSON.toJSONString(itt),JSONObject.class);

            return materialObj != null ? (String)materialObj.get("id") : null;
        } catch (Exception e) {
            log.error("",e);
            throw new BusinessException(ErrorCodes.CommonEnum.SYSTEM_EXCEPTION.getCode(),e.getMessage());
        }
    }


    /**
     * 根据仓库号获取Id
     * @param code
     * @return
     */
    public String getWarehouseIdByCode(String code){
        try {
            String url = String.format("%s%s",super.callRootUrl,"/data/query/v1");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("query"," SELECT whh.id FROM Warehouse whh WHERE whh.code = '"+code+"'");

            String response = super.invokeApi(url, JSON.toJSONString(jsonObject), Enums.API_HEADER_DTOS.WAREHOUSE15);
            JSONObject data = this.getApiData(response);
            if (data == null) return null;

            Object whh = data.get("whh");

            if(whh == null) return null;


            JSONObject materialObj = JSON.parseObject(JSON.toJSONString(whh),JSONObject.class);

            return materialObj != null ? (String)materialObj.get("id") : null;
        } catch (Exception e) {
            log.error("",e);
            throw new BusinessException(ErrorCodes.CommonEnum.SYSTEM_EXCEPTION.getCode(),e.getMessage());
        }
    }

    /**
     * 创建仓库
     */
    public WarehouseResponse createWarehouse(CreateWarehouseRequest createWarehouseRequest){
        try {
            String url = String.format("%s%s",super.callRootUrl,"/data/v4/Warehouse");
            String response = super.invokeApi(url, JSON.toJSONString(createWarehouseRequest), Enums.API_HEADER_DTOS.WAREHOUSE15);

            return this.getWarehouseResponse(response);
        } catch (Exception e) {
            log.error("",e);
            throw new BusinessException(ErrorCodes.CommonEnum.SYSTEM_EXCEPTION.getCode(),e.getMessage());
        }
    }

    /**
     * 更新仓库owner
     * @param warehouseId
     * @param warehouseOwnerRequest
     */
    public WarehouseResponse updateWarehouseOwner(String warehouseId, WarehouseOwnerRequest warehouseOwnerRequest){
        try {
            String url = String.format("%s%s%s",super.callRootUrl,"/data/v4/Warehouse/",warehouseId);
            String response = super.invokeApi(url,
                            JSON.toJSONString(warehouseOwnerRequest),
                            Enums.API_HEADER_DTOS.WAREHOUSE15,true);

            return this.getWarehouseResponse(response);
        } catch (Exception e) {
            log.error("",e);
            throw new BusinessException(ErrorCodes.CommonEnum.SYSTEM_EXCEPTION.getCode(),e.getMessage());
        }
    }

    /**
     * 获取ownerId
     * @param externalld 外部ID
     * @return
     */
    public String getOwnerId(String externalld){
        try {
            String url = String.format("%s%s",super.callRootUrl,"/data/query/v1");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("query"," SELECT pr.id FROM Person pr WHERE pr.externalId = '"+externalld+"'");

            String response = super.invokeApi(url, JSON.toJSONString(jsonObject), Enums.API_HEADER_DTOS.PERSON20);

            JSONObject data = this.getApiData(response);
            if (data == null) return null;

            Object prObj = data.get("pr");
            if(prObj == null) return null;

            JSONObject pr = JSON.parseObject(JSON.toJSONString(prObj),JSONObject.class);

            if(pr == null) return null;

            return (String) pr.get("id");
        } catch (Exception e) {
            log.error("",e);
            throw new BusinessException(ErrorCodes.CommonEnum.SYSTEM_EXCEPTION.getCode(),e.getMessage());
        }
    }

    /**
     * 批量更新库存
     * @return
     */
    public InventoryInfoReponse updateInventoryInfo(InventoryInfoRequest inventoryInfoRequest){
        try {
            String url = String.format("%s%s",super.callRootUrl,"/data/v4/ItemWarehouseLevel");
            String response = super.invokeApi(url, JSON.toJSONString(inventoryInfoRequest), Enums.API_HEADER_DTOS.ITEMWAREHOUSELEVEL14);

            JSONObject data = this.getApiData(response);
            if (data == null) return null;

            Object itemWarehouseLevel = data.get("itemWarehouseLevel");
            if(itemWarehouseLevel == null) return null;


            return JSON.parseObject(JSON.toJSONString(itemWarehouseLevel),InventoryInfoReponse.class);
        } catch (Exception e) {
            log.error("",e);
            throw new BusinessException(ErrorCodes.CommonEnum.SYSTEM_EXCEPTION.getCode(),e.getMessage());
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
        if (jsonArray == null || jsonArray.size() == 0) return null;

        JSONObject adrsObject = (JSONObject) jsonArray.get(0);

        if (adrsObject == null) return null;

        return adrsObject;
    }

    /**
     *service call
     * @param bean
     */
    public String serviceCall(ApiParamBean bean)throws Exception{
        String url = String.format("%s%s", callRootUrl,"/data/v4/ServiceCall");
        return super.invokeApi(url, JSON.toJSONString(getParam(bean)), Enums.API_HEADER_DTOS.SERVICECALL25);
    }

    public void materialCall(String param,String function,  Enums.API_HEADER_DTOS dtos)throws Exception{
        String url = String.format("%s%s", callRootUrl,function);
        String response = super.invokeApi(url, param, dtos);
        System.out.println(response);
    }

    private RequestBodyBean getParam(ApiParamBean bean){
        RequestBodyBean requestBodyBean = new RequestBodyBean();
        List<UdfValuesBean> params = new ArrayList<>();

        Map<String, String> mapStr = BeanUtils.transBeanMapStr(bean);
        for(Map.Entry<String, String> entry : mapStr.entrySet()){
            UdfValuesBean param = new UdfValuesBean();
            String paramId = Enums.API_PARAMS.getId(entry.getKey());
            if(StringUtils.isNotEmpty(entry.getValue())&&StringUtils.isNotEmpty(paramId)){
                param.setMeta(paramId);
                param.setValue(entry.getValue());
                params.add(param);
            }
        }

        requestBodyBean.setEquipments(bean.getEquipments());
        requestBodyBean.setBusinessPartner(bean.getBusinessPartner());
        requestBodyBean.setStatusCode(bean.getStatusCode());
        requestBodyBean.setStatusName(bean.getStatusName());
        requestBodyBean.setSubject(bean.getSubject());
        requestBodyBean.setTypeCode(bean.getTypeCode());
        requestBodyBean.setTypeName(bean.getTypeName());
        requestBodyBean.setUdfValues(params);
        requestBodyBean.setRemarks(bean.getRemark());
        return requestBodyBean;
    }

    private WarehouseResponse getWarehouseResponse(String response) {
        JSONObject data = this.getApiData(response);
        if (data == null) return null;

        Object warehouseOwner = data.get("warehouse");
        if (warehouseOwner == null) return null;

        return JSON.parseObject(JSON.toJSONString(warehouseOwner), WarehouseResponse.class);
    }
}
