package com.crazy.portal.dao.system;

import com.crazy.portal.entity.system.SysSeq;
import org.apache.ibatis.annotations.Param;

public interface SysSeqMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysSeq record);

    int insertSelective(SysSeq record);

    SysSeq selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysSeq record);

    int updateByPrimaryKey(SysSeq record);

    SysSeq selectByDayModel(@Param("day") String day, @Param("model") String model);
}