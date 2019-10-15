package com.crazy.portal.dao.maintenance;

import com.crazy.portal.entity.maintenance.SunMaintenance;

public interface SunMaintenanceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SunMaintenance record);

    int insertSelective(SunMaintenance record);

    SunMaintenance selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SunMaintenance record);

    int updateByPrimaryKeyWithBLOBs(SunMaintenance record);

    int updateByPrimaryKey(SunMaintenance record);
}