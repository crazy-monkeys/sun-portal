package com.crazy.portal.controller;

import com.crazy.portal.bean.BaseResponse;
import com.crazy.portal.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName: ProductController
 * @Author: God Man Qiu~
 * @Date: 2019/10/21 19:43
 */
@RestController
@RequestMapping("/product")
public class ProductController extends BaseController{
    @Resource
    private ProductService productService;

    @GetMapping("/info/{serialNumber}")
    public BaseResponse getProduct(@PathVariable String serialNumber){
       return successResult(productService.getProduct(serialNumber));
    }

}
