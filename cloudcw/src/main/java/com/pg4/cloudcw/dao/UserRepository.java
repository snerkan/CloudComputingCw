package com.pg4.cloudcw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pg4.cloudcw.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	
	 User findUserByEmail(String email);
	 User findUserById(int id);
}

