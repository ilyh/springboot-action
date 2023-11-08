package com.example.springbootsecurityjwt.mapper;

import com.example.springbootsecurityjwt.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xiaonanGuo
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-11-03 16:39:46
* @Entity com.example.springbootsecurityjwt.domain.User
*/
@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUserName(String username);

}
