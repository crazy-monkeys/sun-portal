package com.crazy.portal.service;

import com.crazy.portal.bean.ResponseBean;
import com.crazy.portal.bean.api.device.DeviceInfoBean;
import com.crazy.portal.bean.api.device.UdfValuesBean;
import com.crazy.portal.bean.vo.ProductBean;
import com.crazy.portal.entity.price.PriceList;
import com.crazy.portal.util.BusinessUtil;
import com.crazy.portal.util.DateUtil;
import com.crazy.portal.util.Enums;
import com.crazy.portal.util.ErrorCodes;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
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
    @Resource
    private PriceService priceService;

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

    public BigDecimal getPrice(ProductBean bean){
        BigDecimal price = BigDecimal.ONE;
        try{
            bean.setDeliveryDate(DateUtil.format(new Date(),DateUtil.WEB_FORMAT));
            Date deliveryDate = DateUtil.parseDate(bean.getDeliveryDate(),DateUtil.WEB_FORMAT);
            Date endDate = DateUtil.addDays(new Date(),365);

            PriceList priceList = priceService.getModelPrice(bean.getProductModel());
            BusinessUtil.assertFlase(null == priceList,ErrorCodes.SystemManagerEnum.PRICE_IS_NULL);
            if(deliveryDate.before(endDate)){
                price = priceList.getStandardEarlyBirdDiscount();
            }else{
                price = priceList.getPartEarlyBirdDiscount();
            }

            UdfValuesBean udfValuesBean = apiService.getDevicePowerInfo(bean.getProductId());
            if(null != udfValuesBean &&
                    new BigDecimal(udfValuesBean.getValue()).compareTo(new BigDecimal("10"))==-1
                    && price.compareTo(new BigDecimal("5000"))==1){
                price = price.multiply(new BigDecimal("0.9"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return price;
    }

    /**
     * 检查水货  选择国家和物料国家不同为水货
     * @param country
     * @return  水货 true
     */
    public Boolean checkProduct(List<ProductBean> products, String country){
        for(ProductBean product : products){
            String deviceCounty=apiService.getDeviceAddressInfo(product.getProductId());
            if(!deviceCounty.equals(country)){
                return true;
            }
        }
        return false;
    }
}
