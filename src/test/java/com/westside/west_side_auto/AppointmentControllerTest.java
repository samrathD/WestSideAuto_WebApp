package com.westside.west_side_auto;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
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
	@Autowired
	private UserRepository userRepo;

	@MockBean
	@Autowired
	private appointmentRepository appointmentRepo;

	@MockBean
	@Autowired
	private EmailSenderService emailService;

	@MockBean
    private HttpSession session;
	
	@Test
	void shouldGoToSchedulePage() throws Exception {
		mockMvc.perform(get("/bookAppointment"))
			.andExpect(content().string(containsString("<h2 class=\"title\">Schedule an Appointment</p>")));
	}

	@Test
	void shouldBookAppointment() throws Exception {
		mockMvc.perform(post("/appointments/add")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.param("name", "Sam")
		.param("email", "sda123@sfu.ca")
		.param("description", "hello")
		.param("slots", "2024-04-09")
		.param("time", "10:00")
		.param("service", "Service")
		.param("make", "Make")
		.param("carModel", "Model")
		.param("year", "2023")
		.param("phoneNumber", "1234567890"))
		.andExpect(status().isOk())
		.andExpect(view().name("appointment/appointmentConfirmation"));

//This is my starter code complete it by checking if the email is being sent and if there is an email conflict
	}

	@Test
	void shouldShowAddAppointmentForm() throws Exception {
        // Mocking session user
        User user = new User();
        user.setUsername("testUser");

        mockMvc.perform(get("/appointments/add")
                .sessionAttr("session_user", user))
                .andExpect(status().isOk())
                .andExpect(view().name("appointment/schedule"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().attributeExists("appointment"))
				.andExpect(view().name("appointment/schedule"));

    }
	
	@Test
	void shouldDeleteAppointment() throws Exception {
        // Mocking appointments to delete
        List<userAppointment> appointmentsToDelete = Arrays.asList(new userAppointment(), new userAppointment());

    	when(appointmentRepo.findByUsernameAndEmail(any(), any())).thenReturn(new ArrayList<>(appointmentsToDelete)); // Convert to ArrayList

        // Mocking repository behavior
        mockMvc.perform(post("/appointments/delete")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Taranjot Singh")
                .param("email", "tsa155@sfu.ca"))
				.andExpect(status().isOk())

                .andExpect(view().name("appointment/deleteConfirmation"));

        //Verify that the appointments are deleted and email is sent
        verify(appointmentRepo).deleteAll(appointmentsToDelete);
    }

    @Test
    void shouldReturnNoAppointment() throws Exception {
        // Mocking repository behavior
        mockMvc.perform(post("/appointments/delete")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Test User")
                .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("appointment/NoAppointment"));
    }

	@Test
    void shouldDeleteByUid() throws Exception {
        mockMvc.perform(get("/appointments/delete/{uid}", 123)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/appointments/view"));

        verify(appointmentRepo).deleteById(123);
    }
	
	@Test
	void shouldShowUpdateForm() throws Exception {
		userAppointment appointment = new userAppointment();
			appointment.setUid(123);
			appointment.setUsername("Test User");

			// Mocking repository behavior
			when(appointmentRepo.findByUid(123)).thenReturn(new ArrayList<>(Arrays.asList(appointment))); // Convert List to ArrayList

			mockMvc.perform(get("/appointments/update/{uid}", 123)
					.contentType(MediaType.APPLICATION_FORM_URLENCODED))
					.andExpect(status().isOk())
					.andExpect(view().name("appointment/showAllUpdate"))
					.andExpect(model().attributeExists("appointment"))
					.andExpect(model().attribute("appointment", appointment));
		}

	@Test
	void shouldGetAllAppointmentsSortedByDateAndTime() throws Exception {
        // Prepare mock data
        List<userAppointment> appointments = Arrays.asList(
            new userAppointment(), 
            new userAppointment()
        );

        // Perform GET request
        mockMvc.perform(get("/appointments/view")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(view().name("appointment/showAllAppointments"));
    }

	@Test
	void shouldUpdateAppointment() throws Exception {
		// Prepare mock data
		userAppointment appointment = new userAppointment();
		appointment.setUid(1); // Set the uid of the appointment

		// Mock the session to contain a user
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("session_user", new User());

		// Mock the repository to return the appointment based on uid
		when(appointmentRepo.findByUid(1)).thenReturn(new ArrayList<>(Arrays.asList(appointment))); // Ensure the list contains the appointment

		// Perform POST request
		mockMvc.perform(post("/updateAppointment")
			.session(session)
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("uid", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name("appointment/updateAppointment"))
			.andExpect(model().attribute("user", session.getAttribute("session_user")))
			.andExpect(model().attribute("appt", appointment));
		}

    @Test
    void shouldUpdateSelectedAppointment() throws Exception {
        // Prepare mock data
        userAppointment appointment = new userAppointment();
        appointment.setUid(1); // Set the uid of the appointment

        // Mock appointmentData
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> appointmentData = new HashMap<>();
        appointmentData.put("uid", "1");
        appointmentData.put("name", "John Doe");
        appointmentData.put("email", "john@example.com");
        appointmentData.put("slots", dateFormat.format(new Date()));
        appointmentData.put("description", "Description");
        appointmentData.put("time", "10:00");
        appointmentData.put("service", "Service");
        appointmentData.put("make", "Make");
        appointmentData.put("carModel", "Model");
        appointmentData.put("year", "2023");
        appointmentData.put("phoneNumber", "1234567890");

        // Mock the repository to return the appointment based on uid
        when(appointmentRepo.findByUid(1)).thenReturn(new ArrayList<>(Arrays.asList(appointment))); // Ensure the list contains the appointment

        // Perform POST request
        mockMvc.perform(post("/updateAppointmentInformation")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("uid", appointmentData.get("uid"))
            .param("name", appointmentData.get("name"))
            .param("email", appointmentData.get("email"))
            .param("slots", appointmentData.get("slots"))
            .param("description", appointmentData.get("description"))
            .param("time", appointmentData.get("time"))
            .param("service", appointmentData.get("service"))
            .param("make", appointmentData.get("make"))
            .param("carModel", appointmentData.get("carModel"))
            .param("year", appointmentData.get("year"))
            .param("phoneNumber", appointmentData.get("phoneNumber")))
            .andExpect(view().name("appointment/appointmentConfirmation"));

        // Verify that the appointment is updated
        verify(appointmentRepo).save(appointment);
    }

	@Test
    void shouldShowDeleteForm() throws Exception {
        // Prepare session attribute
        User user = new User();
        when(session.getAttribute("session_user")).thenReturn(user);

        // Perform GET request
        mockMvc.perform(get("/delete"))
            .andExpect(status().isOk())
            .andExpect(view().name("appointment/delete"));
    }
}



