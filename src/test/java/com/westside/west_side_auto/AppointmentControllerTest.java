package com.westside.west_side_auto;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.westside.west_side_auto.controllers.AppointmentController;
import com.westside.west_side_auto.controllers.UserController;
import com.westside.west_side_auto.models.EmailStructure;
import com.westside.west_side_auto.models.User;
import com.westside.west_side_auto.models.UserRepository;
import com.westside.west_side_auto.models.appointmentRepository;
import com.westside.west_side_auto.models.userAppointment;
import com.westside.west_side_auto.service.EmailSenderService;

import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserRepository userRepo;
	@MockBean
	private appointmentRepository appointmentRepo;
	@MockBean
	private EmailSenderService emailService;
	
	@Test
	void shouldGoToSchedulePage() throws Exception {
		mockMvc.perform(get("/bookAppointment"))
			.andExpect(content().string(containsString("<h2 class=\"title\">Schedule an Appointment</p>")));
	}
	
}
