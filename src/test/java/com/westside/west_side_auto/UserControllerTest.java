package com.westside.west_side_auto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.westside.west_side_auto.controllers.UserController;
import com.westside.west_side_auto.models.ReviewRepository;
import com.westside.west_side_auto.models.User;
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
	@MockBean
	private ReviewRepository reviewRepo;
	
	@Test
	void shouldGoToHomePage() throws Exception{
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("<h1 class=\"display-3\">WEST SIDE AUTOWORKS</h1>")));
	}
	
	@Test
	void shouldAddThisUser() throws Exception{
		mockMvc.perform(post("/users/add")
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(new User("Bob", "abc123@gmail.com", "password")))
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(redirectedUrl("/login"));
	}
	
	@Test
	void adminShouldSeeAllAppointmentsOption() throws Exception{
		HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
		sessionAttr.put("session_user", new User("WestSideAuto","wsa@gmail.com","Wsa123"));
		
		mockMvc.perform(get("/login")
			.sessionAttrs(sessionAttr))
			.andExpect(model().attribute("showAllAppointments", true));
	}
	
	@Test
	void shouldBringThisUserToLoginPageWhenClickingLoginButton() throws Exception{
		mockMvc.perform(get("/login"))
			.andExpect(content().string(containsString("<form class=\"form\" action=\"/login\" method=\"post\">")));
	}
	
	@Test 
	void userEditsTheirProfileSuccessfully() throws Exception{
		HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
		sessionAttr.put("session_user", new User());
		
		mockMvc.perform(post("/users/edit")
				.sessionAttrs(sessionAttr)
				.param("email", "abc123@gmail.com")
				.param("password", "testingadifferentpassword")
				.param("confirm-password", "testingadifferentpassword"))
			.andExpect(content().string(containsString("<h1>Profile Updated</h2>")));
	}	
	
	@Test 
	void userEditsTheirProfileWithWrongPasswords() throws Exception{
		HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
		sessionAttr.put("session_user", new User());
		
		mockMvc.perform(post("/users/edit")
				.sessionAttrs(sessionAttr)
				.param("email", "abc123@gmail.com")
				.param("password", "testingadifferentpassword")
				.param("confirm-password", "notthesamepassword"))
			.andExpect(content().string(containsString("<h1>An Error Has Occured</h2>")));
	}	
	
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
}
