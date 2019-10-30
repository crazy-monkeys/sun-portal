package com.crazy.portal.service;

import com.alibaba.fastjson.JSON;
import com.crazy.portal.bean.UnitPriceBean;
import com.crazy.portal.bean.api.ActivityBean;
import com.crazy.portal.bean.api.ApiParamBean;
import com.crazy.portal.bean.api.MaterialRequestBodyBean;
import com.crazy.portal.bean.api.ObjectBean;
import com.crazy.portal.bean.vo.EIRegisterBean;
import com.crazy.portal.bean.vo.MTRegistBean;
import com.crazy.portal.bean.vo.MaintenanceBean;
import com.crazy.portal.bean.vo.ProductBean;
import com.crazy.portal.config.exception.BusinessException;
import com.crazy.portal.util.BusinessUtil;
import com.crazy.portal.util.DateUtil;
import com.crazy.portal.util.Enums;
import com.crazy.portal.util.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: MaintenanceCreateService
 * @Author: God Man Qiu~
 * @Date: 2019/10/14 23:10
 */
@Slf4j
@Service
public class MaintenanceService {
    @Resource
    private ApiService apiService;
    @Resource
    private ProductService productService;

    public void mtRegister(MTRegistBean bean){
        try{
            BusinessUtil.assertFlase(null==bean.getInstallDate(),ErrorCodes.SystemManagerEnum.INSTALL_DATE_IS_NOT_NULL);
            Date inDate = DateUtil.parseDate(bean.getInstallDate(),DateUtil.WEB_FORMAT);
            BusinessUtil.assertFlase(new Date().before(inDate),ErrorCodes.SystemManagerEnum.IN_DATE_IS_BEFORE);

            Boolean check = productService.checkProduct(bean.getProducts(), bean.getAddress().getContryCode());
            BusinessUtil.assertFlase(check, ErrorCodes.SystemManagerEnum.PRODUCT_IS_PARALLEL_IMPORTS);

            ApiParamBean apiParamBean = new ApiParamBean();
            apiParamBean.setStatusCode("-3");
            apiParamBean.setStatusName("Technically Complete");
            apiParamBean.setTypeCode("0003");
            apiParamBean.setTypeName("Technically Complete");
            apiParamBean.setSubject(String.format("%s%s%s","Warranty Registration_", DateUtil.format(new Date(),DateUtil.SHORT_FORMAT),1));

            apiParamBean.setEquipments(new String[]{bean.getProducts().get(0).getProductId()});
            apiParamBean.setBusinessPartner(bean.getBusinessPartner());
            apiParamBean.setInstallInstaller(bean.getInstallInstaller());
            apiParamBean.setInstallDate(bean.getInstallDate());
            apiParamBean.setInstallCec(bean.getInstallCec());

            apiParamBean.setPostCode(bean.getAddress().getPostCode());
            apiParamBean.setPostCode(bean.getAddress().getPostCode());
            String customerAddress = String.format("%s%s%s%s",bean.getAddress().getCityName(),
                    bean.getAddress().getStateName(),
                    bean.getAddress().getAddressLine1(),
                    bean.getAddress().getAddressLine2());
            apiParamBean.setCustomerAddress(customerAddress);
            apiParamBean.setShippingAddress(customerAddress);

            apiParamBean.setCustomerContact(bean.getContacts().getContactFirstName()+bean.getContacts().getContactLastName());
            apiParamBean.setContactEmial(bean.getContacts().getContactEmail());
            apiParamBean.setContactNumber(bean.getContacts().getContactNumber());
            apiParamBean.setSuggestions(bean.getSuggestions());
            serviceCall(apiParamBean);
            //TODO save response
        }catch (BusinessException be){
            throw be;
        }catch (Exception e){
            log.error("",e);
            throw new BusinessException(ErrorCodes.CommonEnum.SERVER_MEETING);
        }

    }

    public void serviceCall(MaintenanceBean bean){
        try{
            String contryCode = bean.getContact().getAddress().getContryCode();
            Date inDate = DateUtil.parseDate(bean.getInstallDate(),DateUtil.WEB_FORMAT);
            BusinessUtil.assertFlase(new Date().before(inDate),ErrorCodes.SystemManagerEnum.IN_DATE_IS_BEFORE);

            Boolean check = productService.checkProduct(bean.getProducts(), contryCode);
            BusinessUtil.assertFlase(check, ErrorCodes.SystemManagerEnum.PRODUCT_IS_PARALLEL_IMPORTS);

            ApiParamBean apiParamBean = new ApiParamBean();
            apiParamBean.setStatusCode("-5");
            apiParamBean.setStatusName("Submitted");
            apiParamBean.setTypeCode("0002");
            apiParamBean.setTypeName("Warranty Claim");
            apiParamBean.setSubject(String.format("%s%s%s","Warranty Claim_", DateUtil.format(new Date(),DateUtil.SHORT_FORMAT),2));

            //负责人
            //联系人
            //Reference
            //Electrical Compliance Certificate
            apiParamBean.setContryCode(contryCode);
            apiParamBean.setEquipments(new String[]{bean.getProducts().get(0).getProductId()});
            apiParamBean.setBusinessPartner(bean.getBusinessPartner());

            if(null != bean.getContact()){
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

            apiParamBean.setFaultType(bean.getServiceCall().getFault());
            apiParamBean.setFaultDescription(bean.getServiceCall().getDescription());
            apiParamBean.setLcdMessage(bean.getServiceCall().getLcd());
            apiParamBean.setIsWeather(bean.getServiceCall().getWeather());
            apiParamBean.setBattery(bean.getServiceCall().getBattery());
           // apiParamBean.setInstallInstaller(bean.getInstallInstaller());
            apiParamBean.setInstallDate(bean.getInstallDate());

            serviceCall(apiParamBean);
            activityCreate(apiParamBean,"1");
            //TODO save response
        }catch (BusinessException be){
            throw be;
        }catch (Exception e){
            log.error("",e);
            throw new BusinessException(ErrorCodes.CommonEnum.SERVER_MEETING);
        }
    }

    public void eiRegister(EIRegisterBean bean){
        try{
            Date inDate = DateUtil.parseDate(bean.getInstallDate(),DateUtil.WEB_FORMAT);
            BusinessUtil.assertFlase(new Date().after(inDate),ErrorCodes.SystemManagerEnum.IN_DATE_IS_BEFORE);

            Boolean check = productService.checkProduct(bean.getProducts(), bean.getAddress().getContryCode());
            BusinessUtil.assertFlase(check, ErrorCodes.SystemManagerEnum.PRODUCT_IS_PARALLEL_IMPORTS);

            ApiParamBean apiParamBean = new ApiParamBean();
            apiParamBean.setStatusCode("-5");
            apiParamBean.setStatusName("Submitted");
            apiParamBean.setTypeCode("0004");
            apiParamBean.setTypeName("Warranty Extension");
            apiParamBean.setSubject(String.format("%s%s%s","Warranty Extension_", DateUtil.format(new Date(),DateUtil.SHORT_FORMAT),2));

            apiParamBean.setBusinessName(bean.getBusinessName());
            apiParamBean.setPostCode(bean.getPostCode());
            apiParamBean.setAbn(bean.getAbn());

            apiParamBean.setInstallInstaller(bean.getFirstName()+bean.getLastName());
            apiParamBean.setInstallDate(bean.getInstallDate());
            apiParamBean.setShippingAddress(bean.getShippingAddress());
            apiParamBean.setContryCode(bean.getAddress().getContryCode());

            apiParamBean.setContactEmial(bean.getEmail());
            apiParamBean.setContactNumber(bean.getContactNumber());

            if(bean.getProducts().size()==1){
                apiParamBean.setTotalAmount(String.valueOf(bean.getProducts().get(0).getAmount()));
            }else{
                BigDecimal totalAmount = BigDecimal.ZERO;
                for(ProductBean e : bean.getProducts()){
                    apiParamBean.setGst(String.valueOf(e.getAmount()));
                    totalAmount = e.getAmount().add(totalAmount);
                }
                apiParamBean.setTotalAmount(String.valueOf(totalAmount));
            }
            apiParamBean.setPurchaseOrder(bean.getPurchaseOrder());

            serviceCall(apiParamBean);
            materialCreate(bean);
            //TODO save response
        }catch (BusinessException be){
            throw be;
        }catch (Exception e){
            log.error("",e);
            throw new BusinessException(ErrorCodes.CommonEnum.SERVER_MEETING);
        }
    }

    private void activityCreate(ApiParamBean apiParamBean,String serviceCallId){
        try {
            ActivityBean activityBean = new ActivityBean();
            activityBean.setSubject(apiParamBean.getSubject());
            activityBean.setBusinessPartner(apiParamBean.getBusinessPartner());
            activityBean.setStatus(serviceCallId);

            ObjectBean objectBean = new ObjectBean();
            objectBean.setObjectId(String.valueOf(new Date().getTime()));
            activityBean.setObjectBean(objectBean);

            //activityBean.setEquipments();
            activityBean.setExternalId(String.valueOf(new Date().getTime()));
            //activityBean.setStartDateTime("");
            //activityBean.setEndDateTime("");
            //activityBean.setEarliestStartDateTime("");
            //activityBean.setDueDateTime("");
            apiService.materialCall(JSON.toJSONString(activityBean),"/data/v4/Activity");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void materialCreate(EIRegisterBean bean){
        bean.getProducts().forEach(e->{
            try{
                MaterialRequestBodyBean materialRequestBodyBean = new MaterialRequestBodyBean();
                materialRequestBodyBean.setItem(Enums.API_PARAMS.Customer_Contact.getId(e.getWarrantyType()));
                materialRequestBodyBean.setEquipment(e.getProductId());

                ObjectBean objectBean = new ObjectBean();
                objectBean.setObjectId(String.valueOf(new Date().getTime()));
                materialRequestBodyBean.setObject(objectBean);

                UnitPriceBean unitPriceBean = new UnitPriceBean();
                unitPriceBean.setAmount(e.getAmount());
                unitPriceBean.setCurrency("USD");
                materialRequestBodyBean.setUnitPrice(unitPriceBean);

                apiService.materialCall(JSON.toJSONString(materialRequestBodyBean),"/data/v4/Material");
            }catch (Exception ex){
                log.error("",ex);
            }
        });
    }


    private void serviceCall(ApiParamBean apiParamBean)throws Exception{
        apiService.serviceCall(apiParamBean);
        //TODO save response
    }
}
