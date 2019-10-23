package com.crazy.portal.controller;

import com.crazy.portal.bean.price.PriceListVO;
import com.crazy.portal.entity.price.PriceList;
import com.crazy.portal.service.PriceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 00:49 2019-10-24
 * @Modified by:
 */
@Controller
@RequestMapping("/price")
public class PriceController {

    @Resource
    private PriceService priceService;


    /**
     * 获取价格列表
     * @param priceListVO
     * @return
     */
    @RequestMapping("/query")
    public List<PriceList> query(@RequestBody PriceListVO priceListVO){
        return priceService.priceLists(priceListVO);
    }
}
