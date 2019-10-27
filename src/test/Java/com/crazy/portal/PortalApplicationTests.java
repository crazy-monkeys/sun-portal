package com.crazy.portal;

import com.crazy.portal.bean.api.device.DeviceInfoBean;
import com.crazy.portal.bean.maintenance.*;
import com.crazy.portal.service.ApiService;
import com.crazy.portal.service.MaintenanceService;
import com.crazy.portal.util.BusinessUtil;
import com.crazy.portal.util.Enums;
import com.crazy.portal.util.ErrorCodes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: PortalApplicationTests
 * @Author: God Man Qiu~
 * @Date: 2019/10/14 23:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PortalApplicationTests {
    @Resource
    private MaintenanceService maintenaceApi;
    @Resource
    private ApiService apiService;

    /**
     * 维保注册
     */
    @Test
    public void createTest(){
        MaintenanceBean bean = new MaintenanceBean();
        List<ProductBean> products = new ArrayList<>();

        bean.setType(1);
        bean.setInstallInstaller("Test");
        bean.setInstallDate("2019-09-09");
        bean.setInstallCec("test");

        AddressBean addr = new AddressBean();
        addr.setCityName("AU");
        addr.setPostCode("123");
        addr.setContryCode("AU");

        ContactBean contact = new ContactBean();
        contact.setContactEmail("test@qq.com");
        contact.setContactNumber("12323");
        contact.setAddress(addr);
        bean.setContact(contact);


        String serialNumber = "J1904090450";
        DeviceInfoBean deviceInfoBean = apiService.getDeviceInfo(serialNumber);
        BusinessUtil.assertFlase(deviceInfoBean.getData().isEmpty() || null == deviceInfoBean.getData().get(0).getEq(), ErrorCodes.SystemManagerEnum.PRODUCT_IS_EMPTY);

        bean.setBusinessPartner(deviceInfoBean.getData().get(0).getEq().getBusinessPartner());

        ProductBean product = new ProductBean();
        product.setProductId(deviceInfoBean.getData().get(0).getEq().getId());
        deviceInfoBean.getData().get(0).getEq().getUdfValues().forEach(e->{
            if(e.getMeta().equals(Enums.API_PARAMS.Product_id.getId())){
                product.setProductModel(e.getName());
            }else if(e.getMeta().equals(Enums.API_PARAMS.Delivery_date.getId())){
            }
        });
        products.add(product);
        bean.setProducts(products);
        try{
            maintenaceApi.serviceCall(bean);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 服务请求
     */
    @Test
    public void serviceCall(){
        MaintenanceBean bean = new MaintenanceBean();
        List<ProductBean> products = new ArrayList<>();
        ContactBean contact = new ContactBean();
        AddressBean addr = new AddressBean();

        bean.setType(2);

        String serialNumber = "J1904090450";
        DeviceInfoBean deviceInfoBean = apiService.getDeviceInfo(serialNumber);
        BusinessUtil.assertFlase(deviceInfoBean.getData().isEmpty() || null == deviceInfoBean.getData().get(0).getEq(), ErrorCodes.SystemManagerEnum.PRODUCT_IS_EMPTY);

        bean.setBusinessPartner(deviceInfoBean.getData().get(0).getEq().getBusinessPartner());

        ProductBean product = new ProductBean();
        product.setProductId(deviceInfoBean.getData().get(0).getEq().getId());
        deviceInfoBean.getData().get(0).getEq().getUdfValues().forEach(e->{
            if(e.getMeta().equals(Enums.API_PARAMS.Product_id.getId())){
                product.setProductModel(e.getName());
            }else if(e.getMeta().equals(Enums.API_PARAMS.Delivery_date.getId())){
            }
        });
        products.add(product);
        bean.setProducts(products);

        bean.setInstallDate("2019-09-09");
        bean.setAccessory("accessory");

        bean.setBusinessType("businessType");

        contact.setAbn("abn");
        contact.setBusinessName("businessName");
        contact.setPerson("person");
        contact.setContactNumber("number");
        contact.setContactEmail("email");

        addr.setContryCode("AU");
        addr.setCityName("AU");
        addr.setCityName("state");
        addr.setPostCode("123");
        addr.setAddressLine1("line1");
        addr.setAddressLine2("line2");
        contact.setAddress(addr);
        bean.setContact(contact);

        EndUserBean endUserBean = new EndUserBean();
        endUserBean.setPerson("endUserPerson");
        endUserBean.setContactNumber("endUsernumber");
        endUserBean.setContactEmail("endUserEmail");

        addr = new AddressBean();
        addr.setContryCode("AU");
        addr.setCityName("AU");
        addr.setCityName("state");
        addr.setPostCode("123");
        addr.setAddressLine1("line1");
        addr.setAddressLine2("line2");
        endUserBean.setAddress(addr);
        bean.setEndUser(endUserBean);

        ServiceCallBean callBean = new ServiceCallBean();
        callBean.setFault("permanent");
        callBean.setLcd("lcd");
        callBean.setDescription("description");
        callBean.setShippingAddress("shippingaddress");
        callBean.setWeather("Yes");
        callBean.setWeatherMessage("message");
        callBean.setBattery("battery");
        callBean.setBatteryMessage("batterymessage");
        bean.setServiceCallBean(callBean);

        try{
            maintenaceApi.serviceCall(bean);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
