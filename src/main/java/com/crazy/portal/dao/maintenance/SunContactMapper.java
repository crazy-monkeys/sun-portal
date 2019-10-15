package com.crazy.portal.dao.maintenance;

import com.crazy.portal.entity.maintenance.SunContact;

public interface SunContactMapper {
    int deleteByPrimaryKey(Integer contactId);

    int insert(SunContact record);

    int insertSelective(SunContact record);

    SunContact selectByPrimaryKey(Integer contactId);

    int updateByPrimaryKeySelective(SunContact record);

    int updateByPrimaryKey(SunContact record);
}