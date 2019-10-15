package com.crazy.portal.dao.maintenance;

import com.crazy.portal.entity.maintenance.SunProduct;

public interface SunProductMapper {
    int deleteByPrimaryKey(Integer proId);

    int insert(SunProduct record);

    int insertSelective(SunProduct record);

    SunProduct selectByPrimaryKey(Integer proId);

    int updateByPrimaryKeySelective(SunProduct record);

    int updateByPrimaryKey(SunProduct record);
}