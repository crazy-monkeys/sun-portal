package com.crazy.portal.controller;

import com.crazy.portal.aop.OperationLog;
import com.crazy.portal.bean.BaseResponse;
import com.crazy.portal.bean.vo.MultipleProduct;
import com.crazy.portal.bean.vo.ProductBean;
import com.crazy.portal.service.ProductService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

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
    @OperationLog
    public BaseResponse getProduct(@PathVariable String serialNumber){
        return successResult(productService.getProduct(serialNumber, 1));
    }

    @GetMapping("/detail/{serialNumber}")
    @OperationLog
    public BaseResponse getProductDetail(@PathVariable String serialNumber){
        return successResult(productService.getProduct(serialNumber, 2));
    }

    @PostMapping("/info/price")
    @OperationLog
    public BaseResponse getListProductPrice(@RequestBody ProductBean bean){
        return successResult(productService.getPrice(bean));
    }

    @PostMapping("/info/list/price")
    @OperationLog
    public BaseResponse getListProductPrice(@RequestBody List<ProductBean> bean){
        return successResult(productService.getListPrice(bean));
    }

    @PostMapping("/multiple/price")
    @OperationLog
    public BaseResponse getListProductPriceInfo(@RequestBody List<MultipleProduct> multipleProduct){
        return successResult(productService.multiplePrice(multipleProduct));
    }
}
