package com.crazy.portal.service;

import com.crazy.portal.bean.api.attachment.AttachmentRequest;
import com.crazy.portal.bean.api.attachment.AttachmentResponse;
import com.crazy.portal.bean.api.device.DeviceData;
import com.crazy.portal.bean.api.device.DeviceEq;
import com.crazy.portal.bean.api.device.DeviceInfoBean;
import com.crazy.portal.bean.api.device.UdfValuesBean;
import com.crazy.portal.bean.api.inventory.InventoryInfoReponse;
import com.crazy.portal.bean.api.inventory.InventoryInfoRequest;
import com.crazy.portal.bean.api.token.TokenBean;
import com.crazy.portal.bean.api.warehouse.WarehouseOwnerRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;
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

    @Test
    public void getMaterialIdByCode() {
        String id = apiService.getMaterialIdByCode("P400104");
        log.info(id);
        Assert.assertTrue(StringUtils.isNotEmpty(id));
    }

    @Test
    public void getWarehouseIdByCode() {
        String id = apiService.getWarehouseIdByCode("P400104");
        log.info(id);
        Assert.assertTrue(StringUtils.isNotEmpty(id));
    }

    @Test
    public void updateWarehouseOwner() {
        String warehouseId = "DB5BE91DC8F24DB7B95D9693F8EBD531";
        WarehouseOwnerRequest warehouseOwnerRequest = new WarehouseOwnerRequest();
        warehouseOwnerRequest.setReservedMaterialWarehouse(false);
        warehouseOwnerRequest.setOwners(Arrays.asList("92DD31EF89FD46C38B1DDA98108D3F2F", "C1E42CCAF0554C7DA4E5286C88B5E135"));
        //没有返回值？？
        apiService.updateWarehouseOwner(warehouseId,warehouseOwnerRequest);
    }

    @Test
    public void getOwnerId() {
        String id = apiService.getOwnerId("rxie01");
        log.info(id);
        Assert.assertTrue(StringUtils.isNotEmpty(id));
    }

    @Test
    public void updateInventoryInfo() {
        InventoryInfoRequest inventoryInfoRequest = new InventoryInfoRequest();
        inventoryInfoRequest.setWarehouse("F23AE0184E734D99B4C321C9ACF49F8");
        inventoryInfoRequest.setItem("6F9B4E73D0C64971B64407267B022341");
        inventoryInfoRequest.setInStock("100");
        InventoryInfoReponse res = apiService.updateInventoryInfo(inventoryInfoRequest);
        Assert.assertTrue(res.getItem().equals("6F9B4E73D0C64971B64407267B022341"));
    }
}