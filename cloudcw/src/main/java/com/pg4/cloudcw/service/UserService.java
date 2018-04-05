package com.pg4.cloudcw.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.pg4.cloudcw.dao.UserRepository;
import com.pg4.cloudcw.entity.User;

@Service
@Transactional
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User createNewUser(User user) {
		return userRepository.save(user);
	}

	public User getUserById(int id) {
		return userRepository.findUserById(id);
	}

	public User getUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}
}
