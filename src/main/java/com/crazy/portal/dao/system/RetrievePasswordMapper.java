package com.crazy.portal.dao.system;

import com.crazy.portal.entity.system.RetrievePassword;
import org.apache.ibatis.annotations.Param;

public interface RetrievePasswordMapper {
    int insertSelective(RetrievePassword record);

    RetrievePassword selectByRandomCodeAndUserId(
            @Param("userId") Integer userId,
            @Param("randomCode") String randomCode);

    int updateByPrimaryKeySelective(RetrievePassword record);
}