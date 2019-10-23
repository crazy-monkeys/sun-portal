package com.crazy.portal.dao.price;

import com.crazy.portal.bean.price.PriceListVO;
import com.crazy.portal.entity.price.PriceList;
import com.github.pagehelper.Page;

public interface PriceListMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(PriceList record);

    PriceList selectByPrimaryKey(Integer id);

    Page<PriceList> selectWithPage(PriceListVO priceListVO);

    int updateByPrimaryKeySelective(PriceList record);
}