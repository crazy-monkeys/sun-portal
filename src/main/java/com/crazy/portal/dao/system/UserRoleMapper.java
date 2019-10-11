package com.crazy.portal.dao.system;

import com.crazy.portal.entity.system.UserRole;

import java.util.List;

public interface UserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByUserId(Integer userId);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);


    Integer countByRoleId(Integer roleId);
    /**
     * 查询用户角色关系
     * @param userId 用户ID
     * @return List<String>  roleId集合
     */
    List<Integer> selectUserRoleByUserId(Integer userId);
}