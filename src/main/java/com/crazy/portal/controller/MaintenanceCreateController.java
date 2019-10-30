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
import javax.validation.Valid;

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
    public BaseResponse maintenanceCreate(@Valid MTRegistBean bean){
        return successResult(maintenanceCreateService.mtRegister(bean));
    }

    @PostMapping("/service/call")
    public BaseResponse serviceCall(@Valid MaintenanceBean bean){
        return successResult(maintenanceCreateService.serviceCall(bean));
    }

    @PostMapping("/ei/register")
    public BaseResponse EIRegist(@Valid EIRegisterBean bean){
        return successResult(maintenanceCreateService.eiRegister(bean));
    }
}
