package com.user.security.poc.service.user;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.user.security.poc.exception.SecurityServiceException;
import com.user.security.poc.repository.model.UserModel;

@SpringBootTest(classes = UserService.class)
@RunWith(SpringRunner.class)
@ComponentScan({ "com.user" })
@ActiveProfiles("postgres")
@ContextConfiguration
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserManmtServiceTest {
	@Autowired
	private UserManmtService userManmtService;
	private static Long userId;

	@Test
	public void orderA1createUserTest() {

		UserModel userModel = new UserModel();
		userModel.setUsername("test");
		userModel = userManmtService.createUser(userModel);
		userId = userModel.getId();
		Assert.assertNotNull(userModel);
		Assert.assertTrue("inavlid user id", userModel.getId() != 0);

	}

	@Test(expected = SecurityServiceException.class)
	public void orderA2userNameValidation() {
		UserModel userModel = new UserModel();
		userModel = userManmtService.createUser(userModel);

	}

	@Test
	public void OrderA3findById() {
		UserModel userModel = userManmtService.findByIdl(userId);
		Assert.assertNotNull("User not found", userModel);
	}

}