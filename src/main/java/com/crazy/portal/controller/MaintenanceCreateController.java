package com.crazy.portal.controller;

import com.crazy.portal.bean.BaseResponse;
import com.crazy.portal.bean.maintenance.MaintenanceBean;
import com.crazy.portal.service.MaintenanceCreateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * @ClassName: MaintenanceCreateController
 * @Author: God Man Qiu~
 * @Date: 2019/10/21 19:39
 */
@RestController
@RequestMapping("/maintenance")
public class MaintenanceCreateController extends BaseController{
    @Resource
    private MaintenanceCreateService maintenanceCreateService;

    @PostMapping("/create")
    public BaseResponse maintenanceCreate(@RequestBody MaintenanceBean bean){
        maintenanceCreateService.saveMaintenance(bean);
        return successResult();
    }
}
