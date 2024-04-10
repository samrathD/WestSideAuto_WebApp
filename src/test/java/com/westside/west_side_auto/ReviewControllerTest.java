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
import com.westside.west_side_auto.controllers.reviewController;
import com.westside.west_side_auto.models.EmailStructure;
import com.westside.west_side_auto.models.ReviewRepository;
import com.westside.west_side_auto.models.User;
import com.westside.west_side_auto.models.UserRepository;
import com.westside.west_side_auto.models.appointmentRepository;
import com.westside.west_side_auto.models.review;
import com.westside.west_side_auto.models.userAppointment;
import com.westside.west_side_auto.service.EmailSenderService;

import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebMvcTest(reviewController.class)

public class ReviewControllerTest{
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	@Autowired
	private UserRepository userRepo;

    @MockBean
	@Autowired
	private ReviewRepository reviewRepo;

	@MockBean
	@Autowired
	private appointmentRepository appointmentRepo;

	@MockBean
	@Autowired
	private EmailSenderService emailService;

	@MockBean
    private HttpSession session;

    @Test
    void shouldSubmitReview() throws Exception {
        // Prepare mock review data
        Map<String, String> reviewData = new HashMap<>();
        reviewData.put("username", "John Doe");
        reviewData.put("date", "2024-04-09");
        reviewData.put("rating", "5");
        reviewData.put("review", "Great service!");

        // Perform POST request
        mockMvc.perform(post("/submitReview")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("username", reviewData.get("username"))
            .param("date", reviewData.get("date"))
            .param("rating", reviewData.get("rating"))
            .param("review", reviewData.get("review")))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));
    } 
}
