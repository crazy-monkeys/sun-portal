package com.crazy.portal.service;

import com.crazy.portal.bean.api.attachment.AttachmentRequest;
import com.crazy.portal.bean.api.attachment.AttachmentResponse;
import com.crazy.portal.bean.api.device.DeviceData;
import com.crazy.portal.bean.api.device.DeviceEq;
import com.crazy.portal.bean.api.device.DeviceInfoBean;
import com.crazy.portal.bean.api.device.UdfValuesBean;
import com.crazy.portal.bean.api.token.TokenBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 00:10 2019-10-21
 * @Modified by:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ApiServiceTest {

    @Resource
    private ApiService apiService;
    @Resource
    private BaseService baseService;

    @Test
    public void getHeader() throws Exception{
        TokenBean tokenBean = baseService.getToken();
        Method method = baseService.getClass().getDeclaredMethod("buildHeader",TokenBean.class);
        method.setAccessible(true);

        Map<String,String> header = (Map)method.invoke(apiService,tokenBean);
        log.info(header.toString());
        Assert.assertTrue(header.get("x-client-id").equals("123d01ed-e117-4ade-afc4-7e697aa4594f"));
    }

    @Test
    public void getDeviceInfo(){
        String serialNumber = "J1904090450";
        DeviceInfoBean deviceInfoBean = apiService.getDeviceInfo(serialNumber);
        log.info(deviceInfoBean.toString());

        DeviceData data = deviceInfoBean.getData().get(0);
        DeviceEq deviceEq = data.getEq();
        List<UdfValuesBean> udfValues = deviceEq.getUdfValues();
        UdfValuesBean udfValuesBean = udfValues.get(0);
        Assert.assertTrue(udfValuesBean.getValue().equals("SG5K-D"));
    }

    @Test
    public void getDeviceAddressInfo() {
        String objectId = "E79E7FDBE94C4D728C15ADB1E8055609";
        String address = apiService.getDeviceAddressInfo(objectId);
        log.info(address);
        Assert.assertTrue(address.equals("US"));
    }

    @Test
    public void getDevicePowerInfo() {
        String objectId = "A82DD58BA85C4387BBC42DDFE813F5A8";
        UdfValuesBean udfValuesBean = apiService.getDevicePowerInfo(objectId);
        log.info(udfValuesBean.toString());
        Assert.assertTrue(udfValuesBean.getMeta().equals("7C13CCA8BC4A472FA20493D0AD690951"));
        Assert.assertTrue(udfValuesBean.getValue().equals("59"));
        Assert.assertTrue(udfValuesBean.getName().equals("Z_Capacity"));
    }

    @Test
    public void attachmentUpload() {
        AttachmentRequest attachmentRequest = new AttachmentRequest();
        attachmentRequest.setFileName("test2.txt");
        attachmentRequest.setFileContent("MTIzZDAxZWQtZTExNy00YWRlLWFmYzQtN2U2OTdhYTQ1OTRmOjZjZmZmZGM1LTdlMjktNGM4YS1iNTRkLTEyZjgyMzI5ZTdmNg==");
        attachmentRequest.setTitle("test2.txt");
        attachmentRequest.setType("TXT");

        AttachmentResponse attachmentResponse = apiService.attachmentUpload(attachmentRequest);
        log.info(attachmentResponse.toString());
        Assert.assertTrue(attachmentResponse.getFileName().equals("test2.txt"));
    }
}