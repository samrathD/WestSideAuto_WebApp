package com.westside.west_side_auto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import com.westside.west_side_auto.controllers.UserController;
import com.westside.west_side_auto.models.UserRepository;
import com.westside.west_side_auto.models.appointmentRepository;
import com.westside.west_side_auto.service.EmailSenderService;

import jakarta.servlet.http.HttpSession;

@WebMvcTest(UserController.class)
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserRepository userRepo;
	@MockBean
	private appointmentRepository appointmentRepo;
	@MockBean
	private EmailSenderService emailService;
	@Autowired
	private UserController uController = new UserController();
	
	@Test
	void shouldGoToHomePage() throws Exception{
		this.mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("<h1 class=\"display-3\">WEST SIDE AUTOWORKS</h1>")));
	}
	
}
