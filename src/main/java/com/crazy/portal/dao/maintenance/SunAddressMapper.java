package com.crazy.portal.dao.maintenance;

import com.crazy.portal.entity.maintenance.SunAddress;

public interface SunAddressMapper {
    int deleteByPrimaryKey(Integer addressId);

    int insert(SunAddress record);

    int insertSelective(SunAddress record);

    SunAddress selectByPrimaryKey(Integer addressId);

    int updateByPrimaryKeySelective(SunAddress record);

    int updateByPrimaryKey(SunAddress record);
}