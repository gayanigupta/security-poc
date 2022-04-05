package com.user.secirity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.user.security.poc.dto.UserTo;
import com.user.security.poc.exception.SecurityServiceErrorCode;
import com.user.security.poc.exception.SecurityServiceException;
import com.user.security.poc.service.user.UserService;

@SpringBootTest(classes = UserService.class)
@RunWith(SpringRunner.class)
@ComponentScan({"com.user"})
@ActiveProfiles("postgres")
@ContextConfiguration
@AutoConfigureMockMvc
public class SecurityServiceTest {

	
	@Autowired
	private UserService userService;
	
	@Test(expected = SecurityServiceException.class)
	public void createUserTest() {
		
		try {
		UserTo userTo = userService.createUser(null);
		}catch (SecurityServiceException e) {
			// TODO: handle exception
			Assert.assertEquals(SecurityServiceErrorCode.MISSING_FIELD, e.getCode());
			Assert.assertTrue(e.getMessage().contains("userTo"));
			throw e;
			
		}
		
		
	}
	
	private UserTo getUser() {
		return null;
	}
}


	