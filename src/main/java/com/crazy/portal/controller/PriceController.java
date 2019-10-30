package com.crazy.portal.controller;

import com.crazy.portal.bean.BaseResponse;
import com.crazy.portal.bean.price.PriceListVO;
import com.crazy.portal.entity.price.PriceList;
import com.crazy.portal.service.PriceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 00:49 2019-10-24
 * @Modified by:
 */
@RestController
@RequestMapping("/price")
public class PriceController extends BaseController{

    @Resource
    private PriceService priceService;


    /**
     * 获取价格列表
     * @param priceListVO
     * @return
     */
    @GetMapping("/query")
    public BaseResponse query(@RequestBody(required = false) PriceListVO priceListVO){
        return successResult(priceService.priceLists(priceListVO));
    }
}
