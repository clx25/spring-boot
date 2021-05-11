package com.learn.mybatis.mapper;

import com.learn.mybatis.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface UserMapper {
	@Select("select * from user where id=#{id} or id=#{id2}")
	List<User> listUser(int id,int id2);
}
