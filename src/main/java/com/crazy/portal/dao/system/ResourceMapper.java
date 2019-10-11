package com.crazy.portal.dao.system;

import com.crazy.portal.entity.system.Resource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Integer id);

    Resource selectResourceByName(String resName);

    int updateByPrimaryKeySelective(Resource record);

    List<Resource> selectResourceByIds(@Param("ids") List<Integer> ids);

    List<Resource> findActiveList(@Param("roleId") Integer roleId);

    List<Resource> findByParentId(Integer parentId);
    /**
     * 查询所有菜单总合
     * @return
     */
    int maxOrder();

    int getRoleCountByResourceId(Integer resourceId);
}