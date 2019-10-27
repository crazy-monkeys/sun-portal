package com.crazy.portal.service;

import com.crazy.portal.bean.maintenance.MaintenanceBean;
import com.crazy.portal.config.exception.BusinessException;
import com.crazy.portal.dao.maintenance.*;
import com.crazy.portal.util.BusinessUtil;
import com.crazy.portal.util.DateUtil;
import com.crazy.portal.util.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public void serviceCall(MaintenanceBean bean){
        try{
            String country = bean.getContact().getAddress().getContryCode();
            if(null != bean.getEndUser()){
                country = bean.getEndUser().getAddress().getContryCode();
            }
            Boolean check = productService.checkProduct(bean.getProducts(), country);
            BusinessUtil.assertFlase(check, ErrorCodes.SystemManagerEnum.PRODUCT_IS_PARALLEL_IMPORTS);
            //this.saveDB(bean);

            //Warranty Claim_日期+流水号:Warranty Claim_20191023001
            //Warranty Extension_日期+流水号

            if(bean.getType().equals(1)){
                bean.setSubject(String.format("%s%s%s","Warranty Registration_", DateUtil.format(new Date(),DateUtil.SHORT_FORMAT),1));
            }else if(bean.getType().equals(2)){
                bean.setSubject(String.format("%s%s%s","Warranty Registration_", DateUtil.format(new Date(),DateUtil.SHORT_FORMAT),2));
            }else if(bean.getType().equals(3)){

            }else{
                throw new BusinessException(ErrorCodes.CommonEnum.REQ_ILLEGAL);
            }
            apiService.serviceCall(bean);
            //TODO save response
        }catch (BusinessException be){
            throw be;
        }catch (Exception e){
            log.error("",e);
            throw new BusinessException(ErrorCodes.CommonEnum.SERVER_MEETING);
        }
    }

    /*private void saveDB(MaintenanceBean bean)throws Exception{
        SunMaintenance maintenance = new SunMaintenance();
        BeanUtils.copyNotNullFields(bean,maintenance);
        sunMaintenanceMapper.insertSelective(maintenance);

        List<String> equipments = new ArrayList<>();
        bean.getProducts().forEach(e->{
            equipments.add(e.getProductId());
            e.setMaintenanceId(maintenance.getId());
            sunProductMapper.insertSelective(e);
        });

        SunContact contact = new SunContact();
        BeanUtils.copyNotNullFields(bean,contact);
        contact.setMaintenanceId(maintenance.getId());
        sunContactMapper.insertSelective(contact);

        SunAddress address = new SunAddress();
        BeanUtils.copyNotNullFields(bean,address);
        address.setContactId(contact.getContactId());
        sunAddressMapper.insertSelective(address);

        //TODO saveFile
    }*/
}
