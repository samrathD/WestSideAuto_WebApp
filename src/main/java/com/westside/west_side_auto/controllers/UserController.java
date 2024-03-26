package com.westside.west_side_auto.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

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
	
	@GetMapping("/")
	public RedirectView process() {
		return new RedirectView("/html/home.html");
	}
	
	@PostMapping("/users/add")
	public String addUser(@RequestParam Map<String,String> newUser, HttpServletResponse response) {
		System.out.println("Adding new user");
		String newName = newUser.get("name");
		String newEmail = newUser.get("email");
		//TODO HASH PASSWORD SO ITS NOT JUST PLAIN TEXT
		String newPassword = newUser.get("password");
		
		if(userRepo.findByEmail(newEmail).size()==0) {
			userRepo.save(new User(newName, newEmail, newPassword));
			response.setStatus(201);
			return "redirect:/login";
		}
		else {
			return "redirect:/signup.html";
		}
		
		
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
			
			List<userAppointment> userAppointments = appointmentRepo.findByEmail(user.getEmail());
			model.addAttribute("appointments", userAppointments);
			
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
			
			List<userAppointment> userAppointments = appointmentRepo.findByEmail(user.getEmail());
			model.addAttribute("appointments", userAppointments);
			
			return "users/account";
		}
	}
	
	@GetMapping("/logout")
	public String logoutUser(HttpServletRequest request) {
		request.getSession().invalidate();
		return "users/login";
	}
	
	@PostMapping("/users/edit")
	public String editUser(@RequestParam Map<String,String> editUser, HttpServletResponse response, HttpSession session) {
		User user = (User) session.getAttribute("session_user");
		
		if(user == null) return "/users/login";
		else {
			String password = editUser.get("password");
			String confirmPassword = editUser.get("confirm-password");
			
			if(password.equals(confirmPassword) && !password.equals("")) {
				System.out.println("User updated successfully");
				user.setUsername(editUser.get("username"));
				user.setEmail(editUser.get("email"));
				user.setPassword(password);
				
				userRepo.save(user);
				response.setStatus(201);
				return "/users/updateProfile";
			}
			System.out.println("Passwords did not match");
			return "/users/updateError";
		}
	}
	
}





