package com.crazy.portal.controller.initial;

import com.alibaba.fastjson.JSON;
import com.crazy.portal.bean.api.inventory.InventoryInfoReponse;
import com.crazy.portal.bean.api.inventory.InventoryInfoRequest;
import com.crazy.portal.bean.api.warehouse.WarehouseOwnerRequest;
import com.crazy.portal.bean.api.warehouse.WarehouseResponse;
import com.crazy.portal.service.ApiService;
import com.crazy.portal.util.DateUtil;
import com.github.liuhuagui.gridexcel.GridExcel;
import com.github.liuhuagui.gridexcel.util.ExcelType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.util.*;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 22:34 2019-11-20
 * @Modified by:
 */
@RestController
@Slf4j
public class InitialController {


    @Resource
    private ApiService apiService;

    @PostMapping("/initial/inventory")
    public boolean initialInventory(@RequestParam("file") MultipartFile file) {
        try {
            GridExcel.readByEventModel(file.getInputStream(),InventoryBean.class,ExcelType.XLSX)
                    .window(1,
                            ts -> processInventoryBean(ts)
                    ).process(cs ->{
                            InventoryBean inventoryBean = new InventoryBean();
                            inventoryBean.setItem(cs.get(0));
                            inventoryBean.setWarehouse(cs.get(1));
                            inventoryBean.setQty(cs.get(2));
                            return inventoryBean;
                        },1);
        } catch (Exception e) {
            log.error("",e);
        }
        return true;
    }

    @PostMapping("/initial/owner")
    public boolean initialOwner(@RequestParam("file") MultipartFile file) {
        try {
            GridExcel.readByEventModel(file.getInputStream(),WarehouseOwnerBean.class,ExcelType.XLSX)
                    .window(1,ts -> processOwners(ts)
                    ).process(cs ->{
                        WarehouseOwnerBean ownerBean = new WarehouseOwnerBean();
                        ownerBean.setCode(cs.get(0));
                        ownerBean.setName(cs.get(1));
                        return ownerBean;
                    },1);
        } catch (Exception e) {
           log.error("",e);
        }
        log.info("processOwners finished");
        return true;
    }

    public void processInventoryBean(List<InventoryBean> inventoryBean){
        inventoryBean.forEach(e->{
            System.out.println("======== 更新库存开始："+ DateUtil.format(new Date(),DateUtil.NEW_FORMAT));
            String id = apiService.getMaterialIdByCode(e.getItem());
            String wareHouseId = apiService.getWarehouseIdByCode(e.getWarehouse());
            InventoryInfoRequest inventoryInfoRequest = new InventoryInfoRequest();
            inventoryInfoRequest.setWarehouse(wareHouseId);
            inventoryInfoRequest.setItem(id);
            inventoryInfoRequest.setInStock(e.getQty());
            InventoryInfoReponse res = apiService.updateInventoryInfo(inventoryInfoRequest);
            System.out.println("======== 更新库存 -> res "+JSON.toJSONString(res));
            System.out.println("======== 更新库存结束："+DateUtil.format(new Date(),DateUtil.NEW_FORMAT));
        });
    }

    public void processOwners(List<WarehouseOwnerBean> ownerBeans){
        Map<String,List<String>> map = new HashMap<>();
        ownerBeans.forEach(e->{
            if(null == map.get(e.getCode())){
                List<String> s = new ArrayList<>();
                s.add(e.getName());
                map.put(e.getCode(),s);
            }else{
                map.get(e.getCode()).add(e.getName());
            }
        });

        map.forEach((k,v)->{
            System.out.println("开始 Owner================="+ DateUtil.format(new Date(),DateUtil.NEW_FORMAT));
            String wareHouseId = apiService.getWarehouseIdByCode(k);
            List<String> ownerIds = new ArrayList<>();
            v.forEach(e->{
                String ownerId = apiService.getOwnerId(e);
                ownerIds.add(ownerId);
            });

            WarehouseOwnerRequest warehouseOwnerRequest = new WarehouseOwnerRequest();
            warehouseOwnerRequest.setReservedMaterialWarehouse(false);
            warehouseOwnerRequest.setOwners(ownerIds);

            WarehouseResponse warehouseResponse = apiService.updateWarehouseOwner(wareHouseId,warehouseOwnerRequest);
            System.out.println("更新 Owner -> res :"+ JSON.toJSONString(warehouseResponse));
            System.out.println("更新 Owner 成功！================="+ DateUtil.format(new Date(),DateUtil.NEW_FORMAT));
        });
    }
}
