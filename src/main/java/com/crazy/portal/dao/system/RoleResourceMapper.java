package com.crazy.portal.dao.system;

import com.crazy.portal.entity.system.RoleResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleResourceMapper {
    List<Integer> selectRoleResourceByRoleIds(@Param("roleIds") List<Integer> roleIds,
                                              @Param("isAllRes") boolean isAllRes);

    int deleteByRoleId(Integer roleId);

    int insertBatchByRoleId(List<RoleResource> list);
}