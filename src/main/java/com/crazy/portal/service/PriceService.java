package com.crazy.portal.service;

import com.crazy.portal.entity.PriceList;
import com.crazy.portal.repository.PriceListRepository;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 00:44 2019-10-24
 * @Modified by:
 */
@Service
public class PriceService {

    @Resource
    private PriceListRepository priceListRepository;

    public PriceList getModelPrice(String model){
        return priceListRepository.findByModel(model);
    }

    public List<PriceList> priceLists(){
        return priceListRepository.findAllByActive("1");
    }
}
