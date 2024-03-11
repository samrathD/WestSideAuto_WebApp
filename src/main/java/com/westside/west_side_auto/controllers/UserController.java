package com.westside.west_side_auto.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.westside.west_side_auto.models.User;
import com.westside.west_side_auto.models.UserRepository;
import com.westside.west_side_auto.models.appointmentRepository;
import com.westside.west_side_auto.models.userAppointment;

import org.springframework.stereotype.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private appointmentRepository appointmentRepo;
		
	@PostMapping("/users/add")
	public String addUser(@RequestParam Map<String,String> newUser, HttpServletResponse response) {
		System.out.println("Adding new user");
		String newName = newUser.get("name");
		String newEmail = newUser.get("email");
		//TODO HASH PASSWORD SO ITS NOT JUST PLAIN TEXT
		String newPassword = newUser.get("password");
		
		userRepo.save(new User(newName, newEmail, newPassword));
		response.setStatus(201);
		return "users/showAll";
	}
	
	@GetMapping("/users/viewAllUsers")
	public String getAllUsers(Model model) {
		List<User> users = userRepo.findAll();
		
		model.addAttribute("usrs", users);
		return "/users/showAll";
	}
	
	@GetMapping("/login")
	public String getLogin(Model model, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("session_user");
		if(user == null) return "users/login";
		else {
			model.addAttribute("user", user);
			return "users/account";
		}
	}
	
	@PostMapping("/login")
	public String loginUser(@RequestParam Map<String,String> formData, Model model, HttpServletRequest request, HttpSession session) {
		String email = formData.get("email");
		String password = formData.get("password");
		List<User> userList = userRepo.findByEmailAndPassword(email, password);
		if(userList.isEmpty()) return "users/login";
		else {
			User user = userList.get(0);
			request.getSession().setAttribute("session_user", user);
			model.addAttribute("user", user);
			return "users/account";
		}
	}
	
	@GetMapping("/logout")
	public String logoutUser(HttpServletRequest request) {
		request.getSession().invalidate();
		return "/users/login";
	}
	
	@PostMapping("/appointments/add")
	public String addAppointment(@RequestParam Map<String,String> appointmentData, Model model) {
		String name = appointmentData.get("name");
		String email = appointmentData.get("email");
		String dateString = appointmentData.get("appointmentDate");
		String message = appointmentData.get("message");

				Date appointmentDate = null;
		    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    	try{
		        	appointmentDate = dateFormat.parse(dateString);
		    	}catch (ParseException e) {
		        e.printStackTrace(); 
		    	}
				
				 // Check if there's an existing appointment for the user and email
				List<userAppointment> existingAppointments = appointmentRepo.findByUsernameAndEmail(name, email);
				if (!existingAppointments.isEmpty()) {
					model.addAttribute("message", "An appointment already exists for this user and email.");
					model.addAttribute("existingAppointments", existingAppointments);
					model.addAttribute("name", name); 
					model.addAttribute("email", email);
					model.addAttribute("message", message);
					model.addAttribute("appointmentDate", dateString);
					System.out.println("An appointment already exists for this user and email.");
					return "/appointment/appointmentExistsConfirmation"; 
				}
				userAppointment appointment = new userAppointment(name, email, message, appointmentDate);
				appointmentRepo.save(appointment);
		
		return "/appointment/appointmentConfirmation";
	}

	@PostMapping("/appointments/addConfirmation")
	public String addAppointmentConfirmation(@RequestParam Map<String,String> formData, Model model) {
	String confirmation = formData.get("confirmation");
	if ("yes".equals(confirmation)) {
		String name = formData.get("name");
		String email = formData.get("email");
		String dateString = formData.get("appointmentDate");
		String message = formData.get("message");
		Date appointmentDate = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			appointmentDate = dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace(); 
		}
		userAppointment appointment = new userAppointment(name, email, message, appointmentDate);
		appointmentRepo.save(appointment);
		return "/appointmentConfirmation";
	}
	else {
		return "redirect:/appointments/add"; 
	}
}	

}
