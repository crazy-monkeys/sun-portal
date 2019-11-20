package com.crazy.portal.controller.initial;

import com.alibaba.fastjson.JSON;
import com.crazy.portal.bean.api.inventory.InventoryInfoReponse;
import com.crazy.portal.bean.api.inventory.InventoryInfoRequest;
import com.crazy.portal.bean.api.warehouse.WarehouseOwnerRequest;
import com.crazy.portal.bean.api.warehouse.WarehouseResponse;
import com.crazy.portal.entity.InitialLog;
import com.crazy.portal.repository.InitialLogRepository;
import com.crazy.portal.service.ApiService;
import com.crazy.portal.util.DateUtil;
import com.crazy.portal.util.ExceptionUtils;
import com.github.liuhuagui.gridexcel.GridExcel;
import com.github.liuhuagui.gridexcel.usermodel.read.ReadExcel;
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

    @Resource
    private InitialLogRepository initialLogRepository;

    @PostMapping("/initial/inventory")
    public boolean initialInventory(@RequestParam("file") MultipartFile file) {
        try {
            ReadExcel<List<String>, InventoryBean> process = GridExcel.readByEventModel(file.getInputStream(), InventoryBean.class, ExcelType.XLSX)
                    .window(0, null
                    ).process(cs -> {
                        InventoryBean inventoryBean = new InventoryBean();
                        inventoryBean.setItem(cs.get(0));
                        inventoryBean.setWarehouse(cs.get(1));
                        inventoryBean.setQty(cs.get(2));
                        return inventoryBean;
                    }, 1);

            processInventoryBean(process.get());
        } catch (Exception e) {
            log.error("initialInventory error",e);
            return false;
        }
        return true;
    }

    @PostMapping("/initial/owner")
    public boolean initialOwner(@RequestParam("file") MultipartFile file) {
        try {
            ReadExcel<List<String>, WarehouseOwnerBean> process = GridExcel.readByEventModel(file.getInputStream(), WarehouseOwnerBean.class, ExcelType.XLSX)
                    .window(0, null
                    ).process(cs -> {
                        WarehouseOwnerBean ownerBean = new WarehouseOwnerBean();
                        ownerBean.setCode(cs.get(0));
                        ownerBean.setName(cs.get(1));
                        return ownerBean;
                    }, 1);

            processOwners(process.get());
        } catch (Exception e) {
           log.error("initialOwner error",e);
           return false;
        }
        log.info("processOwners finished");
        return true;
    }

    public void processInventoryBean(List<InventoryBean> inventoryBean){
        inventoryBean.forEach(e->{
            InitialLog initialLog = new InitialLog();
            String item = e.getItem();
            String warehouse = e.getWarehouse();
            String qty = e.getQty();
            String businessKey = String.format("%s|%s|%s",item,warehouse,qty);
            initialLog.setBusinessKey(businessKey);
            try {
                initialLog.setStatus(1);
                initialLog.setType(1);
                log.info("======== 更新库存开始："+ DateUtil.format(new Date(),DateUtil.NEW_FORMAT));
                String id = apiService.getMaterialIdByCode(item);
                String wareHouseId = apiService.getWarehouseIdByCode(warehouse);
                InventoryInfoRequest inventoryInfoRequest = new InventoryInfoRequest();
                inventoryInfoRequest.setWarehouse(wareHouseId);
                inventoryInfoRequest.setItem(id);
                inventoryInfoRequest.setInStock(qty);
                InventoryInfoReponse res = apiService.updateInventoryInfo(inventoryInfoRequest);
                log.info("======== 更新库存 -> res "+JSON.toJSONString(res));
                log.info("======== 更新库存结束："+DateUtil.format(new Date(),DateUtil.NEW_FORMAT));
                initialLogRepository.save(initialLog);

            } catch (Exception ex) {
                log.error("processInventoryBean error",ex);
                initialLog.setStatus(-1);
                initialLog.setErrorMsg(ExceptionUtils.getExceptionAllinformation(ex));
                initialLogRepository.save(initialLog);
            }
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
            InitialLog initialLog = new InitialLog();
            initialLog.setBusinessKey(String.format("%s|%s",k,v));
            initialLog.setStatus(1);
            initialLog.setType(2);
            try {
                log.info("开始 Owner================="+ DateUtil.format(new Date(),DateUtil.NEW_FORMAT));
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
                log.info("更新 Owner -> res :"+ JSON.toJSONString(warehouseResponse));
                log.info("更新 Owner 成功！================="+ DateUtil.format(new Date(),DateUtil.NEW_FORMAT));
                initialLogRepository.save(initialLog);
            } catch (Exception e) {
                log.error("processOwners error",e);
                initialLog.setStatus(-1);
                initialLog.setErrorMsg(ExceptionUtils.getExceptionAllinformation(e));
                initialLogRepository.save(initialLog);
            }
        });
    }
}
