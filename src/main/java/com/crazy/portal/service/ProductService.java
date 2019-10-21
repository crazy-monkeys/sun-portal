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

    public Boolean checkProduct(String productId, String country){
        return true;
    }
}
