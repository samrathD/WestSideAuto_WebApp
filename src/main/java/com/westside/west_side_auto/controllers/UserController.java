package com.westside.west_side_auto.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.westside.west_side_auto.models.EmailStructure;
import com.westside.west_side_auto.models.ReviewRepository;
import com.westside.west_side_auto.models.User;
import com.westside.west_side_auto.models.UserRepository;
import com.westside.west_side_auto.models.appointmentRepository;
import com.westside.west_side_auto.models.review;
import com.westside.west_side_auto.models.userAppointment;
import com.westside.west_side_auto.service.EmailSenderService;

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
	
	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private ReviewRepository reviewRepository;

	
    public UserController(){

    }
	
	// @GetMapping("/")
	// public RedirectView process() {
	// 	return new RedirectView("/html/home.html");
	// }

	// @GetMapping("/")
	// public String showHomePage() {
    // return "home"; 
	// }

	@GetMapping("/")
	public String showHomePage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("session_user");
		if (user != null) {
			model.addAttribute("user", user);
		}
		List<review> reviews = reviewRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));
		model.addAttribute("reviews", reviews);
        System.out.println("view page show");	
		return "home";
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
			
			//email being made
			EmailStructure emailStructure = new EmailStructure("Account Confirmation", 
			"Your account has been made successfully made at West Side Autoworks.\n"+
			"Account details - \n" +
			"Username: " + newName + "\n" +
			"Email: " + newEmail +
			"\nSincerely,\n"+
			"West Side Autoworks Team");
			emailSenderService.sendEmail(newEmail, emailStructure);
			System.out.println("It works here!");
			return "redirect:/login";
		}

		else {
			return "redirect:/signup";
		}
		
		
	}
	
	@GetMapping("/users/viewAllUsers")
	public String getAllUsers(Model model) {
		List<User> users = userRepo.findAll();
		
		model.addAttribute("usrs", users);
		return "/users/showAll";
	}
	
	// @GetMapping("/login")
	// public String getLogin(Model model, HttpServletRequest request, HttpSession session) {
	// 	User user = (User) session.getAttribute("session_user");
	// 	if(user == null) return "users/login";
	// 	else {
	// 		model.addAttribute("user", user);
			
	// 		List<userAppointment> userAppointments = appointmentRepo.findByEmail(user.getEmail());
	// 		model.addAttribute("appointments", userAppointments);
			
	// 		return "users/account";
	// 	}
	// }

	@GetMapping("/login")
	public String getLogin(Model model, HttpServletRequest request, HttpSession session) {
    User user = (User) session.getAttribute("session_user");
    if(user == null) {
		System.out.println("hello it works here!");
        return "users/login";
    } else {

		// Debug statement to print out the email address
		System.out.println("User email: " + user.getEmail());

        // Check if user email matches 'wsa@gmail.com' before adding to the model
        if(user.getEmail().equals("wsa@gmail.com")) {
            model.addAttribute("showAllAppointments", true);
			System.out.println("hi");
        }
        model.addAttribute("user", user);
        
        List<userAppointment> userAppointments = appointmentRepo.findByEmail(user.getEmail());
        model.addAttribute("appointments", userAppointments);
        // Debug statement to print out the email address
		System.out.println("User email Try 2: " + user.getEmail());

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

	@GetMapping("/signup")
	public String showSignupForm(Model model, HttpSession session) {
    // Check if there's an active session and retrieve user information if available
    User user = (User) session.getAttribute("session_user");
    if (user != null) {
        model.addAttribute("user", user);
    }

    return "signup";
}

}





