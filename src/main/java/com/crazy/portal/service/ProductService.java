package com.crazy.portal.service;

import com.crazy.portal.bean.ResponseBean;
import com.crazy.portal.bean.api.device.DeviceInfoBean;
import com.crazy.portal.bean.api.device.UdfValuesBean;
import com.crazy.portal.bean.vo.MultipleProduct;
import com.crazy.portal.bean.vo.MultipleProductResponse;
import com.crazy.portal.bean.vo.ProductBean;
import com.crazy.portal.config.exception.BusinessException;
import com.crazy.portal.entity.PriceList;
import com.crazy.portal.repository.PriceListRepository;
import com.crazy.portal.util.BusinessUtil;
import com.crazy.portal.util.DateUtil;
import com.crazy.portal.util.Enums;
import com.crazy.portal.util.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName: ProductService
 * @Author: God Man Qiu~
 * @Date: 2019/10/21 19:55
 */
@Slf4j
@Service
public class ProductService {
    @Resource
    private ApiService apiService;
    @Resource
    private PriceListRepository priceListRepository;

    /**
     * 获取设备信息
     * @param serialNumber
     * @return
     */
    public ResponseBean getProduct(String serialNumber, Integer type){
        DeviceInfoBean deviceInfoBean = apiService.getDeviceInfo(serialNumber);
        BusinessUtil.assertFlase(deviceInfoBean.getData().isEmpty() || null == deviceInfoBean.getData().get(0).getEq(), ErrorCodes.SystemManagerEnum.PRODUCT_IS_EMPTY);
        ResponseBean responseBean = new ResponseBean();
        responseBean.setId(deviceInfoBean.getData().get(0).getEq().getId());
        responseBean.setBusinessPartner(deviceInfoBean.getData().get(0).getEq().getBusinessPartner());
        deviceInfoBean.getData().get(0).getEq().getUdfValues().forEach(e->{
            if(e.getMeta().equals(Enums.API_PARAMS.Product_id.getId())){
                responseBean.setProductModel(e.getName());
                responseBean.setProductModelValue(e.getValue());
            }else if(e.getMeta().equals(Enums.API_PARAMS.Delivery_date.getId())){
                responseBean.setDeliveryDate(e.getValue());
            }else if(e.getMeta().equals(Enums.API_PARAMS.Delivery_date.getId())){
                responseBean.setDispatchedDate(e.getValue());
            }
        });

        if(type.equals(2)){
            //获取容量
            UdfValuesBean udfValuesBean = apiService.getDevicePowerInfo(deviceInfoBean.getData().get(0).getEq().getItem());
            responseBean.setDevicePower(null == udfValuesBean?"0":udfValuesBean.getValue());
        }
        return responseBean;
    }

    /**
     *计算单设备价格
     * @param bean
     * @return
     */
    public BigDecimal getPrice(ProductBean bean){
        BigDecimal price = checkPrice(bean);
        UdfValuesBean udfValuesBean = apiService.getDevicePowerInfo(bean.getItem());
        if(null != udfValuesBean &&
                new BigDecimal(udfValuesBean.getValue()).compareTo(new BigDecimal("10"))==-1
                && price.compareTo(new BigDecimal("5000"))==1){
            price = price.multiply(new BigDecimal("0.9"));
        }
        return price;
    }

    /**
     * 批量设备价格计算
     * @param beans
     * @return
     */
    public List<ProductBean> getListPrice(List<ProductBean> beans){
        Map<String,BigDecimal> powerBean = new HashMap<>();
        BigDecimal powerAmount = BigDecimal.ZERO;

        Map<String,String> checkMap = new HashMap<>();
        for(ProductBean bean : beans){
            checkModel(bean.getProductModel(), bean.getSerialNumber());
            checkProduct(checkMap, bean.getProductId());
            BigDecimal price = checkPrice(bean);
            bean.setAmount(price);
            if(new BigDecimal(bean.getDevicePower()).compareTo(new BigDecimal("10"))==-1){
                powerBean.put(bean.getProductId(),bean.getAmount());
                powerAmount = powerAmount.add(price);
            }
        }

        if(powerAmount.compareTo(new BigDecimal("5000"))==1){
            beans.forEach(e->{
                if(powerBean.get(e.getProductId()).compareTo(BigDecimal.ZERO)==1){
                    e.setAmount(e.getAmount().multiply(new BigDecimal("0.9")));
                }
            });
        }
        return beans;
    }

    /**
     * 数据物料设备计算
     * @param multipleProduct
     */
    public MultipleProductResponse multiplePrice(List<MultipleProduct> multipleProduct){
        MultipleProductResponse response = new MultipleProductResponse();
        List<ProductBean> products = new ArrayList<>();
        Map<String,BigDecimal> powerBean = new HashMap<>();

        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal powerAmount = BigDecimal.ZERO;
        Integer totalItem = 0;

        for (MultipleProduct product : multipleProduct) {
            String[] strArray = product.getSerialNumbers().split(",");
            Map<String,String> checkMap = new HashMap<>();
            for(String str : strArray){
                checkProduct(checkMap, str);
                ResponseBean responseBean = this.getProduct(str,2);
                checkModel(responseBean.getProductModelValue(), str);
                ProductBean bean = new ProductBean();
                bean.setProductId(responseBean.getId());
                bean.setBusinessPartner(responseBean.getBusinessPartner());
                bean.setSerialNumber(str);
                bean.setDevicePower(null == responseBean.getDevicePower()?"0":responseBean.getDevicePower());
                bean.setDeliveryDate(responseBean.getDeliveryDate());
                bean.setProductModel(responseBean.getProductModelValue());
                bean.setWarrantyType(product.getWarrantyType());
                if(product.getWarrantyType().equals("W5YS")){
                    bean.setDiscount("Early bird discount");
                    bean.setType("Additional 5 Years Standard Warranty");
                }else{
                    bean.setDiscount("Standard discount");
                    bean.setType("Additional 5 Years Parts Warranty");
                }

                BigDecimal price = this.checkPrice(bean);
                bean.setAmount(price);
                products.add(bean);

                if(new BigDecimal(bean.getDevicePower()).compareTo(new BigDecimal("10"))==-1){
                    powerAmount = powerAmount.add(price);
                    powerBean.put(bean.getProductId(),bean.getAmount());
                }
                totalItem ++;
                amount = amount.add(price);
            }
        }

        if(powerAmount.compareTo(new BigDecimal("5000"))==1){
            amount = BigDecimal.ZERO;
            for(ProductBean product : products){
                if(powerBean.get(product.getProductId()).compareTo(BigDecimal.ZERO)==1){
                    BigDecimal price = product.getAmount().multiply(new BigDecimal("0.9"));
                    product.setAmount(price);
                    amount = amount.add(price);
                }else{
                    amount = amount.add(product.getAmount());
                }
            }
        }

        response.setProducts(products);
        response.setItem(totalItem);
        BigDecimal totalAmount = amount.multiply(new BigDecimal("1.1"));
        response.setInclGst(totalAmount);
        response.setExclGst(amount);
        response.setGst(totalAmount.subtract(amount));
        return response;
    }

    private void checkProduct(Map<String,String> checkMap,String productId){
        boolean flg = checkMap.containsKey(productId);
        if(flg){
            throw new BusinessException(ErrorCodes.SystemManagerEnum.NUMBER_NOT_DUBLE);
        }
        checkMap.put(productId,productId);
    }
    private BigDecimal checkPrice(ProductBean bean){
        BigDecimal price = BigDecimal.ZERO;
        Date endDate = null;
        try{
            Date deliveryDate = null;
            if(StringUtils.isNotEmpty(bean.getDeliveryDate())){
                deliveryDate = DateUtil.parseDate(bean.getDeliveryDate(),DateUtil.WEB_FORMAT);
            }else if(StringUtils.isNotEmpty(bean.getDispatchedDate())){
                deliveryDate = DateUtil.parseDate(bean.getDispatchedDate(),DateUtil.WEB_FORMAT);
            }else{
                deliveryDate = DateUtil.parseDate("1970-01-01",DateUtil.WEB_FORMAT);
            }

            endDate = DateUtil.addDays(deliveryDate,365);
        }catch (Exception e){
            log.error("DELIVERYDATE IS ERROR",e);
        }

        checkModel(bean.getProductModel(), bean.getSerialNumber());
        PriceList priceList = priceListRepository.findByModel(bean.getProductModel());
        BusinessUtil.assertFlase(null == priceList,ErrorCodes.SystemManagerEnum.PRICE_IS_NULL);

        if(new Date().before(endDate)){
            if (bean.getWarrantyType().equals("W5YS")){
                price = priceList.getStandardEarlyBirdDiscount();
            }else if(bean.getWarrantyType().equals("W5YP")){
                price = priceList.getPartEarlyBirdDiscount();
            }
        }else{
            if (bean.getWarrantyType().equals("W5YS")){
                price = priceList.getStandardStandard();
            }else if(bean.getWarrantyType().equals("W5YP")){
                price = priceList.getPartStandard();
            }
        }
        return price;
    }

    /**
     * 校验设备是否在带*好的列表中
     * @param productModel
     * @param serialNumber
     */
    public void checkModel(String productModel, String serialNumber){
        String checkModel = productModel.length()>6?String.format("%s%s",productModel.substring(0,6),"*"):String.format("%s%s",productModel,"*");
        /*校验产品是否是不可延保*/
        PriceList checkPrice = priceListRepository.findByModel(checkModel);
        if(null != checkPrice){
            String hint = String.format(ErrorCodes.SystemManagerEnum.ID_NON_MODEL.getMsg(),serialNumber);
            throw new BusinessException(ErrorCodes.SystemManagerEnum.ID_NON_MODEL.getCode(),hint);
        }
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
