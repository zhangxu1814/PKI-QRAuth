package io.tomahawkd.simpleserver.dao.base;

import io.tomahawkd.simpleserver.model.base.UserPasswordModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserPasswordDao {
    //用户登录
    @Select("select username,password from user_info where username=#{username}")
    @Results({
            @Result(property = "username",column = "username"),
            @Result(property = "password",column = "password")
    })
    UserPasswordModel getUser(String username);

    //添加新用户
    @Insert("insert into user_info(username,password) values (#{model.username},#{model.password})")
    @Options(keyProperty = "index",useGeneratedKeys = true)
    int addUser(@Param("model") UserPasswordModel model);

    //用户修改密码
    @Update("update user_info set password=#{new_password} where username=#{model.username} and password=#{model.password}")
    int updateUser(@Param("model") UserPasswordModel model,String new_password);
}