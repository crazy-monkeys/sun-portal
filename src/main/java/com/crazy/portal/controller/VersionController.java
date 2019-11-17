package com.crazy.portal.controller;

import com.crazy.portal.bean.BaseResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 03:23 2019-11-18
 * @Modified by:
 */
@RestController
public class VersionController extends BaseController{


    @Resource
    private ApplicationContext applicationContext;

    @GetMapping("/version")
    public BaseResponse getVersion(){
        return super.successResult(applicationContext.getEnvironment().getActiveProfiles()[0]);
    }
}
