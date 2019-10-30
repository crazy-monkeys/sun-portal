package com.crazy.portal.service;

import com.crazy.portal.bean.price.PriceListVO;
import com.crazy.portal.dao.price.PriceListMapper;
import com.crazy.portal.entity.price.PriceList;
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
    private PriceListMapper priceListMapper;

    public PriceList getModelPrice(String type){
        return priceListMapper.selectPriceByType(type);
    }

    public List<PriceList> priceLists(PriceListVO priceListVO){
        return priceListMapper.selectWithPage(priceListVO);
    }
}
