package com.crazy.portal.service.maintenance;

import com.crazy.portal.bean.api.token.TokenBean;
import com.crazy.portal.bean.maintenance.MaintenanceBean;
import com.crazy.portal.dao.maintenance.*;
import com.crazy.portal.entity.maintenance.SunAddress;
import com.crazy.portal.entity.maintenance.SunContact;
import com.crazy.portal.entity.maintenance.SunMaintenance;
import com.crazy.portal.entity.maintenance.SunProduct;
import com.crazy.portal.service.ApiService;
import com.crazy.portal.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @ClassName: MaintenanceCreateService
 * @Author: God Man Qiu~
 * @Date: 2019/10/14 23:10
 */
@Slf4j
@Service
public class MaintenanceCreateService {
    @Resource
    private ApiService apiService;
    @Resource
    private SunMaintenanceMapper sunMaintenanceMapper;
    @Resource
    private SunProductMapper sunProductMapper;
    @Resource
    private SunContactMapper sunContactMapper;
    @Resource
    private SunAddressMapper sunAddressMapper;
    @Resource
    private SunFilesMapper sunFilesMapper;

    public void saveMaintenance(MaintenanceBean bean){
        //TODO 校验水货
       try{
           SunMaintenance maintenance = new SunMaintenance();
           BeanUtils.copyNotNullFields(bean,maintenance);
           sunMaintenanceMapper.insertSelective(maintenance);

           SunProduct product = new SunProduct();
           BeanUtils.copyNotNullFields(bean,product);
           product.setMaintenanceId(maintenance.getId());
           sunProductMapper.insertSelective(product);

           SunContact contact = new SunContact();
           BeanUtils.copyNotNullFields(bean,contact);
           contact.setMaintenanceId(maintenance.getId());
           sunContactMapper.insertSelective(contact);

           SunAddress address = new SunAddress();
           BeanUtils.copyNotNullFields(bean,address);
           address.setContactId(contact.getContactId());
           sunAddressMapper.insertSelective(address);

           //TODO saveFile
          // apiService.maintenaceApi();
       }catch (Exception e){

       }
    }
}
