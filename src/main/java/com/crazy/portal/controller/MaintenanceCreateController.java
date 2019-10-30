package com.crazy.portal.controller;

import com.crazy.portal.bean.BaseResponse;
import com.crazy.portal.bean.vo.EIRegisterBean;
import com.crazy.portal.bean.vo.MTRegistBean;
import com.crazy.portal.bean.vo.MaintenanceBean;
import com.crazy.portal.service.MaintenanceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName: MaintenanceCreateController
 * @Author: God Man Qiu~
 * @Date: 2019/10/21 19:39
 */
@RestController
@RequestMapping("/service")
public class MaintenanceCreateController extends BaseController{
    @Resource
    private MaintenanceService maintenanceCreateService;

    @PostMapping("/mt/regist")
    public BaseResponse maintenanceCreate(MTRegistBean bean){
        maintenanceCreateService.mtRegister(bean);
        return successResult();
    }

    @PostMapping("/service/call")
    public BaseResponse serviceCall(MaintenanceBean bean){
        maintenanceCreateService.serviceCall(bean);
        return successResult();
    }

    @PostMapping("/ei/register")
    public BaseResponse EIRegist(EIRegisterBean bean){
        maintenanceCreateService.eiRegister(bean);
        return successResult();
    }
}
