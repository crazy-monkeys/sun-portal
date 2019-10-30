package com.crazy.portal.dao.price;

import com.crazy.portal.bean.price.PriceListVO;
import com.crazy.portal.entity.price.PriceList;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

public interface PriceListMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(PriceList record);

    PriceList selectByPrimaryKey(Integer id);

    Page<PriceList> selectWithPage(PriceListVO priceListVO);

    int updateByPrimaryKeySelective(PriceList record);

    PriceList selectPriceByType(@Param("productModel")String productModel);
}