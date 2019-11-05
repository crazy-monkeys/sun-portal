package com.crazy.portal.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crazy.portal.bean.UnitPriceBean;
import com.crazy.portal.bean.api.ActivityBean;
import com.crazy.portal.bean.api.ApiParamBean;
import com.crazy.portal.bean.api.MaterialRequestBodyBean;
import com.crazy.portal.bean.api.ObjectBean;
import com.crazy.portal.bean.api.device.UdfValuesBean;
import com.crazy.portal.bean.vo.EIRegisterBean;
import com.crazy.portal.bean.vo.MTRegistBean;
import com.crazy.portal.bean.vo.MaintenanceBean;
import com.crazy.portal.bean.vo.ProductBean;
import com.crazy.portal.config.exception.BusinessException;
import com.crazy.portal.dao.system.SysSeqMapper;
import com.crazy.portal.entity.system.SysSeq;
import com.crazy.portal.util.BusinessUtil;
import com.crazy.portal.util.DateUtil;
import com.crazy.portal.util.Enums;
import com.crazy.portal.util.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: MaintenanceCreateService
 * @Author: God Man Qiu~
 * @Date: 2019/10/14 23:10
 */
@Slf4j
@Service
public class MaintenanceService {
    private final static String DATA = "data";
    private final static String SERVICE = "serviceCall";
    private final static String CODE = "code";
    private final static String OBJECTID = "id";
    @Resource
    private ApiService apiService;
    @Resource
    private ProductService productService;
    @Resource
    private SysSeqMapper sysSeqMapper;

    public String mtRegister(MTRegistBean bean){
        try{
            this.checkDate(bean.getInstallDate());
            String country = bean.getCountry();
            this.checkProduct(bean.getProducts(),country);

            ApiParamBean apiParamBean = mappingApiParamBean(bean.getType());
            apiParamBean.setContryCode(country);
            this.checkFile(null, apiParamBean);

            apiParamBean.setEquipments(new String[]{bean.getProducts().get(0).getProductId()});
            apiParamBean.setBusinessPartner(bean.getBusinessPartner());
            apiParamBean.setInstallInstaller(bean.getInstallInstaller());
            apiParamBean.setInstallDate(bean.getInstallDate());
            apiParamBean.setInstallCec(bean.getInstallCec());

            apiParamBean.setCurrency("AUD");
            apiParamBean.setPostCode(bean.getAddress().getPostCode());
            String customerAddress = String.format("%s%s%s%s",
                    bean.getAddress().getCityName(),
                    bean.getAddress().getStateName(),
                    bean.getAddress().getAddressLine1(),
                    bean.getAddress().getAddressLine2());
            apiParamBean.setCustomerAddress(customerAddress);
            apiParamBean.setCustomerContact(bean.getContacts().getContactFirstName()+bean.getContacts().getContactLastName());
            apiParamBean.setContactEmial(bean.getContacts().getContactEmail());
            apiParamBean.setContactNumber(bean.getContacts().getContactNumber());
            apiParamBean.setRemark(bean.getSuggestions());

            //TODO save response
            String code = JSONObject.parseObject(serviceCall(apiParamBean)).getJSONArray(DATA).getJSONObject(0).getJSONObject(SERVICE).getString(CODE);
            if(StringUtils.isNotEmpty(code)){
                System.out.println("service Code :"+code);
                return code;
            }
            throw new BusinessException(ErrorCodes.CommonEnum.SERVER_MEETING);
        }catch (BusinessException be){
            throw be;
        }catch (Exception e){
            log.error("",e);
            throw new BusinessException(ErrorCodes.CommonEnum.SERVER_MEETING);
        }
    }

    public String serviceCall(MaintenanceBean bean){
        try{
            this.checkDate(bean.getInstallDate());
            String country = bean.getCountry();
            this.checkProduct(bean.getProducts(),country);

            ApiParamBean apiParamBean = mappingApiParamBean(bean.getType());
            //this.checkFile(null, apiParamBean);

            apiParamBean.setContryCode(country);
            apiParamBean.setEquipments(new String[]{bean.getProducts().get(0).getProductId()});
            apiParamBean.setBusinessPartner(bean.getBusinessPartner());

            if(null != bean.getContact().getAddress()){
                apiParamBean.setContactEmial(bean.getContact().getContactEmail());
                apiParamBean.setAbn(bean.getContact().getAbn());
                apiParamBean.setCustomerContact(bean.getContact().getPerson());
                apiParamBean.setContactNumber(bean.getContact().getContactNumber());
                String customerAddress = String.format("%s%s%s%s",bean.getContact().getAddress().getCityName(),
                        bean.getContact().getAddress().getStateName(),
                        bean.getContact().getAddress().getAddressLine1(),
                        bean.getContact().getAddress().getAddressLine2());
                apiParamBean.setCustomerAddress(customerAddress);
                apiParamBean.setShippingAddress(customerAddress);
                apiParamBean.setBusinessName(bean.getContact().getBusinessName());
                apiParamBean.setPostCode(bean.getContact().getAddress().getPostCode());
            }else{
                apiParamBean.setContactEmial(bean.getEndUser().getContactEmail());
                apiParamBean.setCustomerContact(bean.getEndUser().getPerson());
                apiParamBean.setContactNumber(bean.getEndUser().getContactNumber());
                String customerAddress = String.format("%s%s%s%s",bean.getEndUser().getAddress().getCityName(),
                        bean.getEndUser().getAddress().getStateName(),
                        bean.getEndUser().getAddress().getAddressLine1(),
                        bean.getEndUser().getAddress().getAddressLine2());
                apiParamBean.setCustomerAddress(customerAddress);
                apiParamBean.setShippingAddress(customerAddress);
                apiParamBean.setPostCode(bean.getEndUser().getAddress().getPostCode());
            }

            apiParamBean.setFaultType(bean.getServiceCall().getFault().equals("Permanent")?"1":"2");
            apiParamBean.setFaultDescription(bean.getServiceCall().getDescription());
            apiParamBean.setLcdMessage(bean.getServiceCall().getLcd());
            apiParamBean.setIsWeather(bean.getServiceCall().getWeather());
            apiParamBean.setBatteryModel(bean.getServiceCall().getModel());
           // apiParamBean.setInstallInstaller(bean.getInstallInstaller());
            apiParamBean.setInstallDate(bean.getInstallDate());

            apiParamBean.setReference(bean.getServiceCall().getWeatherMsg());
            apiParamBean.setLocation(bean.getServiceCall().getLocation());
            apiParamBean.setRemark(bean.getServiceCall().getBatteryMsg());

            //TODO save response
            JSONObject serviceCall = JSONObject.parseObject(serviceCall(apiParamBean)).getJSONArray(DATA).getJSONObject(0).getJSONObject(SERVICE);
            String code = serviceCall.getString(CODE);
            String objectId = serviceCall.getString(OBJECTID);
            if(StringUtils.isNotEmpty(code)){
                activityCreate(apiParamBean,objectId);
                return code;
            }
            throw new BusinessException(ErrorCodes.CommonEnum.SERVER_MEETING);
        }catch (BusinessException be){
            log.error("",be);
            throw be;
        }catch (Exception e){
            log.error("",e);
            throw new BusinessException(ErrorCodes.CommonEnum.SERVER_MEETING);
        }
    }

    public String eiRegister(EIRegisterBean bean){
        try{
            String country = bean.getCountry();
            this.checkProduct(bean.getProducts(),country);
            BusinessUtil.assertTrue(bean.getEmail().equals(bean.getSendEmail()),ErrorCodes.SystemManagerEnum.EMAIL_IS_NO);

            ApiParamBean apiParamBean = mappingApiParamBean(bean.getType());
            this.getTotalAmount(bean.getProducts(),apiParamBean, bean);
            apiParamBean.setContryCode(country);
            apiParamBean.setBusinessName(bean.getBusinessName());
            apiParamBean.setPostCode(bean.getAddress().getPostCode());
            apiParamBean.setAbn(bean.getAbn());

            apiParamBean.setInstallInstaller(bean.getFirstName()+bean.getLastName());
            apiParamBean.setInstallDate(bean.getInstallDate());
            String customerAddress = String.format("%s%s%s%s",bean.getAddress().getCityName(),
                    bean.getAddress().getStateName(),
                    bean.getAddress().getAddressLine1(),
                    bean.getAddress().getAddressLine2());
            apiParamBean.setCustomerAddress(customerAddress);
            apiParamBean.setShippingAddress(bean.getShippingAddress());
            apiParamBean.setContryCode(bean.getAddress().getCountryCode());

            apiParamBean.setCustomerContact(bean.getFirstName()+bean.getLastName());
            apiParamBean.setContactEmial(bean.getEmail());
            apiParamBean.setContactNumber(bean.getContactNumber());
            apiParamBean.setPurchaseOrder(bean.getPurchaseOrder());

            //TODO save response
            JSONObject serviceCall = JSONObject.parseObject(serviceCall(apiParamBean)).getJSONArray(DATA).getJSONObject(0).getJSONObject(SERVICE);
            String code = serviceCall.getString(CODE);
            String objectId = serviceCall.getString(OBJECTID);
            if(StringUtils.isNotEmpty(code)){
                materialCreate(bean, objectId);
                return code;
            }
            throw new BusinessException(ErrorCodes.CommonEnum.SERVER_MEETING);
        }catch (BusinessException be){
            throw be;
        }catch (Exception e){
            log.error("",e);
            throw new BusinessException(ErrorCodes.CommonEnum.SERVER_MEETING);
        }
    }

    private void activityCreate(ApiParamBean apiParamBean,String objectId){
        try {
            ActivityBean activityBean = new ActivityBean();
            activityBean.setSubject(apiParamBean.getSubject());
            activityBean.setBusinessPartner(apiParamBean.getBusinessPartner());

            ObjectBean objectBean = new ObjectBean();
            objectBean.setObjectId(objectId);
            activityBean.setObject(objectBean);

            UdfValuesBean udfValuesBean = new UdfValuesBean();
            udfValuesBean.setMeta("75AD82098BAC40E2BD709AC1E10D81E4");
            udfValuesBean.setValue("Z001");
            List<UdfValuesBean> udfValuesBeans = new ArrayList<>();
            udfValuesBeans.add(udfValuesBean);
            activityBean.setUdfValues(udfValuesBeans);

            activityBean.setEquipment(apiParamBean.getEquipments()[0]);
            activityBean.setExternalId(String.valueOf(new Date().getTime()));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            String date = sdf.format(new Date());
            activityBean.setStartDateTime(date);
            activityBean.setEndDateTime(date);
            activityBean.setEarliestStartDateTime(date);
            activityBean.setDueDateTime(date);
            apiService.materialCall(JSON.toJSONString(activityBean),"/data/v4/Activity", Enums.Api_Header_Dtos.ACTIVITY);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void materialCreate(EIRegisterBean bean, String objectId){
        bean.getProducts().forEach(e->{
            try{
                MaterialRequestBodyBean materialRequestBodyBean = new MaterialRequestBodyBean();
                materialRequestBodyBean.setItem(e.getItem());
                materialRequestBodyBean.setEquipment(e.getProductId());

                ObjectBean objectBean = new ObjectBean();
                objectBean.setObjectId(objectId);
                materialRequestBodyBean.setObject(objectBean);

                UnitPriceBean unitPriceBean = new UnitPriceBean();
                unitPriceBean.setAmount(e.getAmount());
                unitPriceBean.setCurrency("AUD");
                materialRequestBodyBean.setUnitPrice(unitPriceBean);

                apiService.materialCall(JSON.toJSONString(materialRequestBodyBean),"/data/v4/Material", Enums.Api_Header_Dtos.MATERIAL);
            }catch (Exception ex){
                log.error("",ex);
            }
        });
    }

    private String serviceCall(ApiParamBean apiParamBean)throws Exception{
        return apiService.serviceCall(apiParamBean);
        //TODO save response
    }

    /**
     * 校验时间是否 在当前时间之前
     * @param installDate
     * @throws Exception
     */
    private void checkDate(String installDate)throws Exception{
        BusinessUtil.assertFlase(null==installDate,ErrorCodes.SystemManagerEnum.INSTALL_DATE_IS_NOT_NULL);
        Date inDate = DateUtil.parseDate(installDate,DateUtil.WEB_FORMAT);
        BusinessUtil.assertFlase(new Date().before(inDate),ErrorCodes.SystemManagerEnum.IN_DATE_IS_BEFORE);
    }

    /**
     * 校验是否为水货  国家和设备国家不一致
     * @param products
     * @param country
     */
    private void checkProduct(List<ProductBean> products, String country){
        Boolean check = productService.checkProduct(products, country);
        BusinessUtil.assertFlase(check, ErrorCodes.SystemManagerEnum.PRODUCT_IS_PARALLEL_IMPORTS);
    }

    /**
     * 计算价格
     * @param products
     * @param apiParamBean
     */
    private void getTotalAmount(List<ProductBean> products,ApiParamBean apiParamBean, EIRegisterBean bean)throws Exception{
        apiParamBean.setCurrency("4");
        if(products.size()==1){
            BigDecimal amount = products.get(0).getAmount();
            apiParamBean.setAmountGST(String.valueOf(amount));
            BigDecimal totalAmount = products.get(0).getAmount().multiply(new BigDecimal("1.1"));
            apiParamBean.setTotalAmount(String.valueOf(totalAmount));
            apiParamBean.setGst(String.valueOf(totalAmount.subtract(amount)));

            this.checkDate(bean.getInstallDate());
            apiParamBean.setEquipments(new String[]{products.get(0).getProductId()});
            apiParamBean.setBusinessPartner(bean.getBusinessPartner());
        }else{
            BigDecimal totalAmount = BigDecimal.ZERO;
            BigDecimal amount = BigDecimal.ZERO;
            String businessPartner = "";
            List<String> equipments = new ArrayList<>();
            for(ProductBean e : products){
                if(StringUtils.isEmpty(businessPartner)){
                    businessPartner = e.getBusinessPartner();
                }else{
                    if(!businessPartner.equals(e.getBusinessPartner())){
                        throw new BusinessException(ErrorCodes.SystemManagerEnum.PRODUCT_BUSINESSPARTNER_IS_DIF);
                    }
                }
                amount = amount.add(e.getAmount());
                equipments.add(e.getProductId());
            }
            totalAmount = amount.multiply(new BigDecimal("1.1"));
            apiParamBean.setAmountGST(String.valueOf(amount));
            apiParamBean.setTotalAmount(String.valueOf(totalAmount));
            apiParamBean.setGst(String.valueOf(totalAmount.subtract(amount)));

            apiParamBean.setBusinessPartner(bean.getBusinessPartner());
            apiParamBean.setEquipments(equipments.toArray(new String[equipments.size()]));
        }
    }
    private void checkFile(MultipartFile file, ApiParamBean apiParamBean){
        if(null == file){
            apiParamBean.setElectricalComplianceCertificate(true);
            apiParamBean.setInvliceUpload(true);
        }else{
            apiParamBean.setElectricalComplianceCertificate(true);
            apiParamBean.setInvliceUpload(true);
        }
    }

    /**
     * 初始化请求参数对象
     * @param type
     * @return
     */
    private ApiParamBean mappingApiParamBean(String type){
        ApiParamBean apiParamBean = new ApiParamBean();
        String statusCode = "";
        String statusName = "";
        String typeCode = "";
        String typeName = "";
        String subject = "";

        if(type.equals("1")){
            statusCode = "-3";
            statusName = "9-Technically Complete";
            typeCode = "0003";
            typeName = "Warranty Registration";
            String seq = String.format("%0" + 3 + "d", this.getSeq(Enums.Sys_Seq.maintenance.toString()));
            subject = String.format("%s%s%s","Warranty Registration_", DateUtil.format(new Date(),DateUtil.SHORT_FORMAT),seq);
        }else if(type.equals("2")){
            statusCode = "-5";
            statusName = "1-Submitted";
            typeCode = "0002";
            typeName = "Warranty Claim";
            String seq = String.format("%0" + 3 + "d", this.getSeq(Enums.Sys_Seq.servicecall.toString()));
            subject = String.format("%s%s%s","Warranty Claim_", DateUtil.format(new Date(),DateUtil.SHORT_FORMAT), seq);
        }else if(type.equals("3")){
            statusCode = "-5";
            statusName = "1-Submitted";
            typeCode = "0004";
            typeName = "Warranty Extension";
            String seq = String.format("%0" + 3 + "d", this.getSeq(Enums.Sys_Seq.insurance.toString()));
            subject = String.format("%s%s%s","Warranty Extension_", DateUtil.format(new Date(),DateUtil.SHORT_FORMAT), seq);
        }

        apiParamBean.setStatusCode(statusCode);
        apiParamBean.setStatusName(statusName);
        apiParamBean.setTypeCode(typeCode);
        apiParamBean.setTypeName(typeName);
        apiParamBean.setSubject(subject);
        return apiParamBean;
    }

    private Integer getSeq(String model){
        SysSeq seq = sysSeqMapper.selectByDayModel(DateUtil.format(new Date(),DateUtil.WEB_FORMAT),model);
        if(null == seq){
            seq = new SysSeq();
            seq.setSeqDay(DateUtil.format(new Date(),DateUtil.WEB_FORMAT));
            seq.setSeqModel(model);
            seq.setSeqValue(1);
            sysSeqMapper.insertSelective(seq);
        }else{
            Integer seqNum = seq.getSeqValue()+1;
            seq.setSeqValue(seqNum);
            sysSeqMapper.updateByPrimaryKeySelective(seq);
        }
        return seq.getSeqValue();
    }

}
