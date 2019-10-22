package com.crazy.portal.service;

import com.crazy.portal.bean.ResponseBean;
import com.crazy.portal.bean.api.device.DeviceEq;
import com.crazy.portal.bean.api.device.DeviceInfoBean;
import com.crazy.portal.entity.maintenance.SunProduct;
import com.crazy.portal.util.BusinessUtil;
import com.crazy.portal.util.Enums;
import com.crazy.portal.util.ErrorCodes;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: ProductService
 * @Author: God Man Qiu~
 * @Date: 2019/10/21 19:55
 */
@Service
public class ProductService {
    @Resource
    private ApiService apiService;

    /**
     * 获取设备信息
     * @param serialNumber
     * @return
     */
    public ResponseBean getProduct(String serialNumber){
        DeviceInfoBean deviceInfoBean = apiService.getDeviceInfo(serialNumber);
        BusinessUtil.assertFlase(deviceInfoBean.getData().isEmpty() || null == deviceInfoBean.getData().get(0).getEq(), ErrorCodes.SystemManagerEnum.PRODUCT_IS_EMPTY);
        ResponseBean responseBean = new ResponseBean();
        responseBean.setId(deviceInfoBean.getData().get(0).getEq().getId());
        responseBean.setBusinessPartner(deviceInfoBean.getData().get(0).getEq().getBusinessPartner());
        deviceInfoBean.getData().get(0).getEq().getUdfValues().forEach(e->{
            if(e.getMeta().equals(Enums.API_PARAMS.Product_id.getId())){
                responseBean.setProductModel(e.getName());
                responseBean.setProductModelValue(e.getValue());
                responseBean.setItem(e.getItem());
            }else if(e.getMeta().equals(Enums.API_PARAMS.Delivery_date.getId())){
                responseBean.setDeliveryDate(e.getValue());
            }
        });
        return responseBean;
    }

    /**
     * 检查水货  选择国家和物料国家不同为水货
     * @param country
     * @return  水货 true
     */
    public Boolean checkProduct(List<SunProduct> products, String country){
        return false;
    }
}
