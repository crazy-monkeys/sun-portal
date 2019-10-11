package com.crazy.portal.dao.system;

import com.crazy.portal.entity.system.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Role record);

    int updateByPrimaryKeySelective(Role record);

    Role findById(Integer id);

    /**
     * 查询角色列表
     * @return
     */
    List<Role> queryRoleList(@Param("roleCode") String roleCode);


    Role findRoleByCode(String roleCode);

    Role findByName(String roleName);

    List<Role> findAllRoles();

    List<Role> findRoleByType(Integer roleType);
}