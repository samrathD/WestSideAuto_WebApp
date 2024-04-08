package com.westside.west_side_auto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.westside.west_side_auto.controllers.AppointmentController;
import com.westside.west_side_auto.controllers.UserController;

@SpringBootTest
class WestSideAutoApplicationTests {

	@Autowired
	private UserController uController;
	
	@Autowired
	private AppointmentController aController;
	
	@Test
	void contextLoadsUserController() throws Exception {
		assertThat(uController).isNotNull();
	}
	
	@Test
	void contextLoadsAppointmentController() throws Exception {
		assertThat(aController).isNotNull();
	}
	
	

}
