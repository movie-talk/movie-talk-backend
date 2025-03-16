package com.movie.talk.dao;

import com.movie.talk.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {	
	User getUserById(String id);
	
	User getUserForLogin(String id);
	
	void insertUser (User user) throws Exception;
}
