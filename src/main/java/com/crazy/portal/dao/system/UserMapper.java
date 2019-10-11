package com.crazy.portal.dao.system;

import com.crazy.portal.entity.system.User;
import com.github.pagehelper.Page;

import java.util.List;

public interface UserMapper {

    User selectById(Integer id);

    int insertSelective(User record);

    int updateByPrimaryKeySelective(User record);

    User findByLoginName(String loginName);

    Page<User> selectUserWithPage(User user);
}