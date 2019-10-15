package com.crazy.portal.dao.maintenance;

import com.crazy.portal.entity.maintenance.ServiceCallDetail;
import com.crazy.portal.entity.maintenance.ServiceCallDetailWithBLOBs;

public interface ServiceCallDetailMapper {
    int deleteByPrimaryKey(Integer callId);

    int insert(ServiceCallDetailWithBLOBs record);

    int insertSelective(ServiceCallDetailWithBLOBs record);

    ServiceCallDetailWithBLOBs selectByPrimaryKey(Integer callId);

    int updateByPrimaryKeySelective(ServiceCallDetailWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ServiceCallDetailWithBLOBs record);

    int updateByPrimaryKey(ServiceCallDetail record);
}