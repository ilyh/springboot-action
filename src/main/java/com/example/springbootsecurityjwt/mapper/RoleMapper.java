package com.example.springbootsecurityjwt.mapper;

import com.example.springbootsecurityjwt.domain.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author xiaonanGuo
* @description 针对表【role】的数据库操作Mapper
* @createDate 2023-11-03 16:38:51
* @Entity com.example.springbootsecurityjwt.domain.Role
*/
@Mapper
public interface RoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectByUid(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

}
