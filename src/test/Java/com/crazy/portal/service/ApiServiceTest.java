package com.crazy.portal.service;

import com.crazy.portal.bean.api.attachment.AttachmentRequest;
import com.crazy.portal.bean.api.attachment.AttachmentResponse;
import com.crazy.portal.bean.api.device.DeviceData;
import com.crazy.portal.bean.api.device.DeviceEq;
import com.crazy.portal.bean.api.device.DeviceInfoBean;
import com.crazy.portal.bean.api.device.UdfValuesBean;
import com.crazy.portal.bean.api.token.TokenBean;
import com.crazy.portal.bean.api.warehouse.CreateWarehouseRequest;
import com.crazy.portal.bean.api.warehouse.WarehouseOwnerRequest;
import com.crazy.portal.bean.api.warehouse.WarehouseResponse;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 获取头信息
     * @throws Exception
     */
    @Test
    public void getHeader() throws Exception{
        TokenBean tokenBean = baseService.getToken();
        Method method = baseService.getClass().getDeclaredMethod("buildHeader",TokenBean.class);
        method.setAccessible(true);

        Map<String,String> header = (Map)method.invoke(apiService,tokenBean);
        log.info(header.toString());
        Assert.assertTrue(header.get("x-client-id").equals("123d01ed-e117-4ade-afc4-7e697aa4594f"));
    }

    /**
     * 获取设备信息
     */
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

    /**
     * 获取设备地址信息
     */
    @Test
    public void getDeviceAddressInfo() {
        String objectId = "E79E7FDBE94C4D728C15ADB1E8055609";
        String address = apiService.getDeviceAddressInfo(objectId);
        log.info(address);
        Assert.assertTrue(address.equals("US"));
    }

    /**
     * 获取设备容量信息
     */
    @Test
    public void getDevicePowerInfo() {
        String objectId = "A82DD58BA85C4387BBC42DDFE813F5A8";
        UdfValuesBean udfValuesBean = apiService.getDevicePowerInfo(objectId);
        log.info(udfValuesBean.toString());
        Assert.assertTrue(udfValuesBean.getMeta().equals("7C13CCA8BC4A472FA20493D0AD690951"));
        Assert.assertTrue(udfValuesBean.getValue().equals("59"));
        Assert.assertTrue(udfValuesBean.getName().equals("Z_Capacity"));
    }

    /**
     * 附件上传
     */
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

    /**
     * 根据物料号获取物料编码
     */
    @Test
    public void getMaterialIdByCode() {
        String id = apiService.getMaterialIdByCode("P400104");
        log.info(id);
        Assert.assertTrue(id.equals("A82DD58BA85C4387BBC42DDFE813F5A8"));
    }

//    /**
//     * 更新仓库Owner
//     */
//    @Test
//    public void batchUpdateOwner(){
//        List<OwnerBean> ownerBeans = new ArrayList<>();
//        OwnerBean ownerBean = new OwnerBean();
//        ownerBean.setCode("APACSG01");ownerBean.setName("Caitlyn");
//        ownerBeans.add(ownerBean);
//        ownerBean = new OwnerBean();
//        ownerBean.setCode("APACSG01");ownerBean.setName("George");
//        ownerBeans.add(ownerBean);
//        ownerBean = new OwnerBean();
//        ownerBean.setCode("EMEASG01");ownerBean.setName("Jack");
//        ownerBeans.add(ownerBean);
//        ownerBean = new OwnerBean();
//        ownerBean.setCode("APACSG02");ownerBean.setName("Philip");
//        ownerBeans.add(ownerBean);
//        ownerBean = new OwnerBean();
//        ownerBean.setCode("APACSG03");ownerBean.setName("Philip");
//        ownerBeans.add(ownerBean);
//        ownerBean = new OwnerBean();
//        ownerBean.setCode("APACSG04");ownerBean.setName("Peter");
//        ownerBeans.add(ownerBean);
//        ownerBean = new OwnerBean();
//        ownerBean.setCode("APACSG05");ownerBean.setName("Peter");
//        ownerBeans.add(ownerBean);
//        ownerBean = new OwnerBean();
//        ownerBean.setCode("APACSP06");ownerBean.setName("Peter");
//        ownerBeans.add(ownerBean);
//        ownerBean = new OwnerBean();
//        ownerBean.setCode("APACSP07");ownerBean.setName("Peter");
//        ownerBeans.add(ownerBean);
//        ownerBean = new OwnerBean();
//        ownerBean.setCode("APACSG08");ownerBean.setName("Peter");
//        ownerBeans.add(ownerBean);
//        ownerBean = new OwnerBean();
//        ownerBean.setCode("APACSG08");ownerBean.setName("Philip");
//        ownerBeans.add(ownerBean);
//        ownerBean = new OwnerBean();
//        ownerBean.setCode("APACSG08");ownerBean.setName("Jack");
//        ownerBeans.add(ownerBean);
//        ownerBean = new OwnerBean();
//        ownerBean.setCode("APACSG09");ownerBean.setName("Peter");
//        ownerBeans.add(ownerBean);
//        ownerBean = new OwnerBean();
//        ownerBean.setCode("AMERSG01");ownerBean.setName("Peter");
//        ownerBeans.add(ownerBean);
//        ownerBean = new OwnerBean();
//        ownerBean.setCode("APACSP10");ownerBean.setName("Peter");
//        ownerBeans.add(ownerBean);
//        ownerBean = new OwnerBean();
//        ownerBean.setCode("APACSG11");ownerBean.setName("Peter");
//        ownerBeans.add(ownerBean);
//        ownerBean = new OwnerBean();
//        ownerBean.setCode("INDIASG01");ownerBean.setName("Jack");
//        ownerBeans.add(ownerBean);
//
//        Map<String,List<String>> map = new HashMap<>();
//        ownerBeans.forEach(e->{
//            if(null == map.get(e.getCode())){
//                List<String> s = new ArrayList<>();
//                s.add(e.getName());
//                map.put(e.getCode(),s);
//            }else{
//                map.get(e.getCode()).add(e.getName());
//            }
//        });
//
//        map.forEach((k,v)->{
//            System.out.println("开始 Owner 成功！================="+ DateUtil.format(new Date(),DateUtil.NEW_FORMAT));
//            String wareHouseId = apiService.getWarehouseIdByCode(k);
//            List<String> ownerIds = new ArrayList<>();
//            v.forEach(e->{
//                String ownerId = apiService.getOwnerId(e);
//                ownerIds.add(ownerId);
//            });
//
//            WarehouseOwnerRequest warehouseOwnerRequest = new WarehouseOwnerRequest();
//            warehouseOwnerRequest.setReservedMaterialWarehouse(false);
//            warehouseOwnerRequest.setOwners(ownerIds);
//
//            WarehouseResponse warehouseResponse = apiService.updateWarehouseOwner(wareHouseId,warehouseOwnerRequest);
//            System.out.println("更新 Owner 成功！"+ JSON.toJSONString(warehouseResponse));
//            System.out.println("更新 Owner 成功！================="+ DateUtil.format(new Date(),DateUtil.NEW_FORMAT));
//        });
//    }

    /**
     * 根据仓库号获取仓库编码
     */
    @Test
    public void getWarehouseIdByCode() {
        String id = apiService.getWarehouseIdByCode("st02");
        log.info(id);
        Assert.assertTrue(id.equals("DB5BE91DC8F24DB7B95D9693F8EBD531"));
    }

    /**
     * 根据owner名称获取编码
     */
    @Test
    public void getOwnerId() {
        String id = apiService.getOwnerId("Peter");
        log.info(id);
        Assert.assertTrue(id.equals("4E1E9EDC9F1F4628A7316AF90948D34E"));
    }

    /**
     * 创建仓库
     */
    @Test
    public void createWarehouse() {
        CreateWarehouseRequest request = new CreateWarehouseRequest();
        request.setCode("ST04");
        request.setName("测试仓库4");
        request.setReservedMaterialWarehouse(false);

        WarehouseResponse warehouseResponse = apiService.createWarehouse(request);
        Assert.assertTrue(warehouseResponse != null);
    }

    /**
     * 更新仓库owner
     */
    @Test
    public void updateWarehouseOwner() {
        String warehouseId = "39DA29338109419ABD0E56BB35422CFB";
        WarehouseOwnerRequest warehouseOwnerRequest = new WarehouseOwnerRequest();
        warehouseOwnerRequest.setReservedMaterialWarehouse(false);
        warehouseOwnerRequest.setOwners(Arrays.asList("6334C731996E4021B4567A823B666DA6", "4E1E9EDC9F1F4628A7316AF90948D34E"));

        WarehouseResponse warehouseResponse = apiService.updateWarehouseOwner(warehouseId,warehouseOwnerRequest);
        Assert.assertTrue(warehouseResponse != null);
    }



//    /**
//     * 更新库存信息
//     */
//    @Test
//    public void updateInventoryInfo() {
//        List<InventoryBean> beans = new ArrayList<>();
//        InventoryBean bean = new InventoryBean();
//        bean.setItem("FC004148");bean.setWarehouse("APACSG01");bean.setQty("100");beans.add(bean);bean=new InventoryBean();
//        bean.setItem("FD001653");bean.setWarehouse("EMEASG01");bean.setQty("101");beans.add(bean);bean=new InventoryBean();
//        bean.setItem("FD001654");bean.setWarehouse("INDIASG01");bean.setQty("102");beans.add(bean);bean=new InventoryBean();
//        bean.setItem("FD001655");bean.setWarehouse("APACSG02");bean.setQty("103");beans.add(bean);bean=new InventoryBean();
//        bean.setItem("FD001652");bean.setWarehouse("APACSG03");bean.setQty("104");beans.add(bean);bean=new InventoryBean();
//        bean.setItem("FC004146");bean.setWarehouse("APACSG04");bean.setQty("105");beans.add(bean);bean=new InventoryBean();
//        bean.setItem("FC004147");bean.setWarehouse("APACSG05");bean.setQty("106");beans.add(bean);bean=new InventoryBean();
//        bean.setItem("FD001656");bean.setWarehouse("APACSP06");bean.setQty("107");beans.add(bean);bean=new InventoryBean();
//        bean.setItem("FD001657");bean.setWarehouse("APACSP07");bean.setQty("108");beans.add(bean);bean=new InventoryBean();
//        bean.setItem("FD001658");bean.setWarehouse("APACSG08");bean.setQty("109");beans.add(bean);bean=new InventoryBean();
//        bean.setItem("GC000222");bean.setWarehouse("APACSG09");bean.setQty("110");beans.add(bean);bean=new InventoryBean();
//        bean.setItem("FC001291");bean.setWarehouse("AMERSG01");bean.setQty("111");beans.add(bean);bean=new InventoryBean();
//        bean.setItem("VF000083");bean.setWarehouse("APACSP10");bean.setQty("112");beans.add(bean);bean=new InventoryBean();
//        bean.setItem("PI000002");bean.setWarehouse("APACSG11");bean.setQty("113");beans.add(bean);bean=new InventoryBean();
//
//        beans.forEach(e->{
//            System.out.println("========同步开始："+DateUtil.format(new Date(),DateUtil.NEW_FORMAT));
//            String id = apiService.getMaterialIdByCode(e.getItem());
//            String wareHouseId = apiService.getWarehouseIdByCode(e.getWarehouse());
//            InventoryInfoRequest inventoryInfoRequest = new InventoryInfoRequest();
//            inventoryInfoRequest.setWarehouse(wareHouseId);
//            inventoryInfoRequest.setItem(id);
//            inventoryInfoRequest.setInStock(e.getQty());
//            InventoryInfoReponse res = apiService.updateInventoryInfo(inventoryInfoRequest);
//            System.out.println("========同步结束："+DateUtil.format(new Date(),DateUtil.NEW_FORMAT));
//            System.out.println("========同步结果："+JSON.toJSONString(res));
//        });
//
//
//
//        InventoryInfoRequest inventoryInfoRequest = new InventoryInfoRequest();
//        inventoryInfoRequest.setWarehouse("DB5BE91DC8F24DB7B95D9693F8EBD531");
//        inventoryInfoRequest.setItem("A82DD58BA85C4387BBC42DDFE813F5A8");
//        inventoryInfoRequest.setInStock("100");
//        InventoryInfoReponse res = apiService.updateInventoryInfo(inventoryInfoRequest);
//        //对方无返回
//        Assert.assertTrue(res == null);
//    }
}