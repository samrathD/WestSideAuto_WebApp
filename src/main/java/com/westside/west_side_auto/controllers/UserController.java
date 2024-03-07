package com.westside.west_side_auto.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.westside.west_side_auto.models.User;
import com.westside.west_side_auto.models.UserRepository;

import org.springframework.stereotype.Controller;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
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
	
	
	
}
