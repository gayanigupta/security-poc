package com.user.security.poc.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.security.poc.exception.SecurityServiceException;
import com.user.security.poc.repository.IUserRepository;
import com.user.security.poc.repository.model.UserModel;

@Service
public class UserManmtService {

	@Autowired
	private IUserRepository userRepository;

	public UserModel createUser(UserModel userModel) {
		validate(userModel);

		return userRepository.save(userModel);
	}

	private void validate(UserModel user) {

		if (user == null) {
			throw new SecurityServiceException("Invalid request object");
		}
		if (user.getUsername() == null) {
			throw new SecurityServiceException("Invalid user name");
		}

	}

	public UserModel findByIdl(long uid) {
		return userRepository.findById(uid).get();
	}

}
