package com.crazy.portal.controller;

import com.alibaba.fastjson.JSON;
import com.crazy.portal.aop.OperationLog;
import com.crazy.portal.bean.BaseResponse;
import com.crazy.portal.bean.price.PriceListVO;
import com.crazy.portal.entity.PriceList;
import com.crazy.portal.repository.OperationLogRepository;
import com.crazy.portal.repository.PriceListRepository;
import com.crazy.portal.service.ApiService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PriceController extends BaseController{

    @Resource
    private PriceListRepository priceListRepository;

    /**
     * 获取价格列表
     * @return
     */
    @GetMapping("/query")
    public BaseResponse query(){
        return successResult(priceListRepository.findAll());
    }

    @GetMapping("/init")
    public BaseResponse init(){
        String jsonStr = "[{\"active\":1,\"createTime\":1572289381000,\"id\":1,\"model\":\"SG2K-S*\",\"partEarlyBirdDiscount\":69.00000,\"partStandard\":109.00000,\"standardEarlyBirdDiscount\":119.00000,\"standardStandard\":189.00000},{\"active\":1,\"createTime\":1572590349000,\"id\":2,\"model\":\"SG2K5-S*\",\"partEarlyBirdDiscount\":79.00000,\"partStandard\":119.00000,\"standardEarlyBirdDiscount\":129.00000,\"standardStandard\":209.00000},{\"active\":1,\"id\":3,\"model\":\"SG3K-S*\",\"partEarlyBirdDiscount\":79.00000,\"partStandard\":119.00000,\"standardEarlyBirdDiscount\":129.00000,\"standardStandard\":209.00000},{\"active\":1,\"id\":4,\"model\":\"SG3K-D*\",\"partEarlyBirdDiscount\":89.00000,\"partStandard\":129.00000,\"standardEarlyBirdDiscount\":139.00000,\"standardStandard\":219.00000},{\"active\":1,\"id\":5,\"model\":\"SG5K-D*\",\"partEarlyBirdDiscount\":89.00000,\"partStandard\":149.00000,\"standardEarlyBirdDiscount\":159.00000,\"standardStandard\":259.00000},{\"active\":1,\"id\":6,\"model\":\"SG8K-D*\",\"partEarlyBirdDiscount\":139.00000,\"partStandard\":239.00000,\"standardEarlyBirdDiscount\":269.00000,\"standardStandard\":419.00000},{\"active\":1,\"id\":7,\"model\":\"SH5K-20\",\"partEarlyBirdDiscount\":209.00000,\"partStandard\":349.00000,\"standardEarlyBirdDiscount\":359.00000,\"standardStandard\":589.00000},{\"active\":1,\"id\":8,\"model\":\"SG5KTL-MT\",\"partEarlyBirdDiscount\":169.00000,\"partStandard\":239.00000,\"standardEarlyBirdDiscount\":239.00000,\"standardStandard\":349.00000},{\"active\":1,\"id\":9,\"model\":\"SG10KTL-MT\",\"partEarlyBirdDiscount\":249.00000,\"partStandard\":359.00000,\"standardEarlyBirdDiscount\":369.00000,\"standardStandard\":519.00000},{\"active\":1,\"id\":10,\"model\":\"SG15KTL-M\",\"partEarlyBirdDiscount\":329.00000,\"partStandard\":469.00000,\"standardEarlyBirdDiscount\":419.00000,\"standardStandard\":599.00000},{\"active\":1,\"id\":11,\"model\":\"SG20KTL-M\",\"partEarlyBirdDiscount\":399.00000,\"partStandard\":569.00000,\"standardEarlyBirdDiscount\":509.00000,\"standardStandard\":729.00000},{\"active\":1,\"id\":12,\"model\":\"SG30CX\",\"partEarlyBirdDiscount\":459.00000,\"partStandard\":619.00000,\"standardEarlyBirdDiscount\":799.00000,\"standardStandard\":1029.00000},{\"active\":1,\"id\":13,\"model\":\"SG50CX\",\"partEarlyBirdDiscount\":559.00000,\"partStandard\":739.00000,\"standardEarlyBirdDiscount\":929.00000,\"standardStandard\":1239.00000},{\"active\":1,\"id\":14,\"model\":\"SG110CX\",\"partEarlyBirdDiscount\":1189.00000,\"partStandard\":1489.00000,\"standardEarlyBirdDiscount\":1649.00000,\"standardStandard\":2059.00000},{\"active\":1,\"id\":15,\"model\":\"SG250HX\",\"partEarlyBirdDiscount\":2369.00000,\"partStandard\":2969.00000,\"standardEarlyBirdDiscount\":3299.00000,\"standardStandard\":4119.00000},{\"active\":1,\"id\":16,\"model\":\"SG2KTL-S\",\"partEarlyBirdDiscount\":69.00000,\"partStandard\":139.00000,\"standardEarlyBirdDiscount\":109.00000,\"standardStandard\":239.00000},{\"active\":1,\"id\":17,\"model\":\"SG3KTL-S\",\"partEarlyBirdDiscount\":79.00000,\"partStandard\":149.00000,\"standardEarlyBirdDiscount\":129.00000,\"standardStandard\":259.00000},{\"active\":1,\"id\":18,\"model\":\"SG3KTL-D\",\"partEarlyBirdDiscount\":89.00000,\"partStandard\":159.00000,\"standardEarlyBirdDiscount\":139.00000,\"standardStandard\":269.00000},{\"active\":1,\"id\":19,\"model\":\"SG5KTL-S\",\"partEarlyBirdDiscount\":99.00000,\"partStandard\":179.00000,\"standardEarlyBirdDiscount\":149.00000,\"standardStandard\":309.00000},{\"active\":1,\"id\":20,\"model\":\"SH5K\",\"partEarlyBirdDiscount\":299.00000,\"partStandard\":449.00000,\"standardEarlyBirdDiscount\":469.00000,\"standardStandard\":769.00000}]";
        Iterable<PriceList> result = priceListRepository.findAll();
        if(!result.iterator().hasNext()){
            List<PriceList> lists = JSON.parseArray(jsonStr, PriceList.class);
            lists.forEach(e->{
                priceListRepository.save(e);
            });
        }
        return successResult();
    }
}
