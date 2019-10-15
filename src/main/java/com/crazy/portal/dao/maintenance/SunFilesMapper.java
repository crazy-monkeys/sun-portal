package com.crazy.portal.dao.maintenance;

import com.crazy.portal.entity.maintenance.SunFiles;

public interface SunFilesMapper {
    int deleteByPrimaryKey(Integer fileId);

    int insert(SunFiles record);

    int insertSelective(SunFiles record);

    SunFiles selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(SunFiles record);

    int updateByPrimaryKey(SunFiles record);
}