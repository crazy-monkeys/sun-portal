package com.crazy.portal.service;

import com.crazy.portal.bean.api.device.DeviceData;
import com.crazy.portal.bean.api.device.DeviceInfoBean;
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
    public List<DeviceData> getProduct(String serialNumber){
        DeviceInfoBean deviceInfoBean = apiService.getDeviceInfo(serialNumber);
        return deviceInfoBean.getData();
    }

    /**
     * 检查水货  选择国家和物料国家不同为水货
     * @param productId
     * @param country
     * @return  水货 true
     */
    public Boolean checkProduct(String[] productId, String country){
        return true;
    }
}
