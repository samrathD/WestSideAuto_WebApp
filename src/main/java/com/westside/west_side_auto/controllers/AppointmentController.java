package com.westside.west_side_auto.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

import com.westside.west_side_auto.models.EmailStructure;
import com.westside.west_side_auto.models.User;
import com.westside.west_side_auto.models.UserRepository;
import com.westside.west_side_auto.models.appointmentRepository;
import com.westside.west_side_auto.models.userAppointment;
import com.westside.west_side_auto.service.EmailSenderService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
public class AppointmentController {
    
    @Autowired
	private appointmentRepository appointmentRepo;
    private EmailSenderService emailSenderService;

    @Autowired
    public AppointmentController(EmailSenderService emailSenderService){
        this.emailSenderService = emailSenderService;
    }
    
    @GetMapping("/bookAppointment")
    public String appointmentBookingSetup(HttpServletRequest request, Model model, HttpSession session) {
    	User user = (User) session.getAttribute("session_user");
		if(user != null) model.addAttribute("user", user);
		else {
			User emptyUser = new User();
			model.addAttribute(emptyUser);
		}
		List<userAppointment>appointments = appointmentRepo.findAll(Sort.by(Sort.Direction.ASC,"uid"));
        model.addAttribute("appointment",appointments);
    	return "appointment/schedule";
    }

    @PostMapping("/appointments/add")
    public String addAppointment(@RequestParam Map<String,String> appointmentData, Model model) {
        String name = appointmentData.get("name");
        String email = appointmentData.get("email");
        String dateString = appointmentData.get("slots");
        String description = appointmentData.get("description");
        String appointmentTime = appointmentData.get("time");
        String service = appointmentData.get("service");
        Date appointmentDate = null;
        String make = appointmentData.get("make");
        String carModel = appointmentData.get("carModel");
        String year = appointmentData.get("year");
        String phoneNumber = appointmentData.get("phoneNumber");
        System.out.println(appointmentTime);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try {
        appointmentDate = dateFormat.parse(dateString);
        // appointmentTime = LocalTime.parse(dateString.substring(11)); // Extract time part
    } catch (ParseException | DateTimeParseException e) {
        e.printStackTrace(); 
        // Handle parsing exception here
    }

    // Check if there's an existing appointment for the email
    List<userAppointment> existingAppointmentsByEmail = appointmentRepo.findByEmail(email);
    if (!existingAppointmentsByEmail.isEmpty()) {
        model.addAttribute("description", "An appointment already exists for this email.");
        model.addAttribute("existingAppointments", existingAppointmentsByEmail);
        model.addAttribute("name", name); 
        model.addAttribute("email", email);
        model.addAttribute("description", description);
        model.addAttribute("appointmentDate", dateString);
        model.addAttribute("appointmentTime", appointmentTime);
        model.addAttribute("service", service);
        model.addAttribute("make",make);
        model.addAttribute("carModel",carModel);
        model.addAttribute("year",year);
        model.addAttribute("phoneNumber", phoneNumber);

        System.out.println("An appointment already exists for this user and email.");
        return "appointment/appoinmentExistsConfirmation";
    }

    userAppointment appointment = new userAppointment(name, email, description, appointmentDate, service, appointmentTime, make, carModel, year, phoneNumber);
    appointmentRepo.save(appointment);
    
    //email being made
    EmailStructure emailStructure = new EmailStructure("Appointment Confirmation", 
    "Your appointment is confirmed. Appointment Details:\n" +
    "Date: " + dateFormat.format(appointmentDate) + "\n" +
    "Time: " + appointmentTime);

    
    System.out.println("It works here!");
    emailSenderService.sendEmail(email, emailStructure);
    return "appointment/appointmentConfirmation";
    // return "/src/main/resources/templates/appointment/appointmentConfirmation.html";
}

// @PostMapping("/appointments/addConfirmation")
//     public String addAppointmentConfirmation(@RequestParam Map<String,String> formData, Model model) {
        
//         String replaceOption = formData.get("replace");

//         if ("yes".equals(replaceOption)) {
//             String name = formData.get("name");
//             String email = formData.get("email");
//             String dateString = formData.get("appointmentDate");
//             String description = formData.get("description");
//             String service = formData.get("service");
//             Date appointmentDate = null;
// 			String appointmentTime = formData.get("appointmentTime"); //made change here
//             String make = formData.get("make");
//             String carModel = formData.get("carModel");
//             String year = formData.get("year");
//             String phoneNumber = formData.get("phoneNumber");



//             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//             try {
//                 appointmentDate = dateFormat.parse(dateString);
//             } catch (ParseException e) {
//                 e.printStackTrace();
//             }
//             //change here(commented)

// 			// try {
// 			// 	appointmentTime = LocalTime.parse(dateString.substring(11)); 
// 			// } catch (Exception e) {
// 			// 	e.printStackTrace();
// 			// }


//             // Replace the existing appointment
//             List<userAppointment> existingAppointments = appointmentRepo.findByEmail(email);
//             if (!existingAppointments.isEmpty()) {
//                 for (userAppointment appointment : existingAppointments) {
//                     appointment.setUsername(name);
//                     appointment.setDescription(description);
//                     appointment.setAppointmentDate(appointmentDate);
// 					appointment.setAppointmentTime(appointmentTime);
//                     appointment.setService(service);
//                     appointment.setMake(make);
//                     appointment.setCarModel(carModel);
//                     appointment.setYear(year);
//                     appointment.setPhoneNumber(phoneNumber);
//                     appointmentRepo.save(appointment);
//                 }
//             }
//             //email being made
//     EmailStructure emailStructure = new EmailStructure("Appointment Confirmation", 
//     "Your appointment is confirmed. Appointment Details:\n" +
//     "Date: " + dateFormat.format(appointmentDate) + "\n" +
//     "Time: " + appointmentTime +
// 	"\nSincerely,\n"+
// 	"West Side Autoworks Team");
    
//     System.out.println("It works here!");
//     emailSenderService.sendEmail(email, emailStructure);
//             return "appointment/appointmentConfirmation";
//         } else {
//             // // Redirect to the add appointment page after 5 seconds
//             // model.addAttribute("redirectDelay", 5000); // 5 seconds delay
//             return "redirect:/appointments/add";
//         }
//     }

	@GetMapping("/appointments/add")
    public String showAddAppointmentForm(HttpServletRequest request, Model model, HttpSession session) {
        // Return the view name for the add appointment form
        User user = (User) session.getAttribute("session_user");
		if(user != null) model.addAttribute("user", user);
		else {
			User emptyUser = new User();
			model.addAttribute(emptyUser);
		}
		List<userAppointment>appointments = appointmentRepo.findAll(Sort.by(Sort.Direction.ASC,"uid"));
        model.addAttribute("appointment",appointments);
        return "appointment/schedule"; // Adjust the view name as per your application
    }

	@Transactional
    @PostMapping("/appointments/delete")
    public String deleteAppointment(@RequestParam String name, @RequestParam String email) {
        List<userAppointment> appointmentsToDelete = appointmentRepo.findByUsernameAndEmail(name.trim(), email.trim());
        if (!appointmentsToDelete.isEmpty()) {
        //email being made
            EmailStructure emailStructure = new EmailStructure("Cancel Confirmation", 
            "Your appointment has been successfully cancelled. Deleted Appointment Details:\n");
            appointmentRepo.deleteAll(appointmentsToDelete); // Delete the appointments
            System.out.println("It works here!");
            emailSenderService.sendEmail(email, emailStructure);
            return "appointment/deleteConfirmation";
        } else {
            return "appointment/NoAppointment";
        }
    }
	
	@Transactional
    @PostMapping("/appointments/deleteById")
    public String deleteAppointment(@RequestParam String uid) {
        List<userAppointment> appointmentsToDelete = appointmentRepo.findByUid(Integer.parseInt(uid));
        if (!appointmentsToDelete.isEmpty()) {
        //email being made
        EmailStructure emailStructure = new EmailStructure("Cancel Confirmation", 
        "Your appointment has been successfully cancelled.\n");
            appointmentRepo.deleteAll(appointmentsToDelete); // Delete the appointments
            System.out.println("It works here!");
            emailSenderService.sendEmail(appointmentsToDelete.get(0).getEmail(), emailStructure);
            return "appointment/deleteConfirmation";
        } else {
            return "appointment/NoAppointment";
        }
    }

//     @Transactional
//     @PostMapping("/appointments/deleteFromList")
//     public ResponseEntity<String> deleteAppointmentFromList( @RequestParam String name, @RequestParam String email) {
//         List<userAppointment> appointmentOptional = appointmentRepo.findByUsernameAndEmail(name, email);
//         if (!appointmentOptional.isEmpty()) {
//             for (userAppointment appointment : appointmentOptional) {
//             if (appointment.getUsername().equals(name) && appointment.getEmail().equals(email)) {
//                 appointmentRepo.delete(appointment);
//                 // Send confirmation email
//                 EmailStructure emailStructure = new EmailStructure("Cancel Confirmation",
//                     "Your appointment has been successfully cancelled. Deleted Appointment Details:\n");
//                 emailSenderService.sendEmail(email, emailStructure);
//                 return ResponseEntity.ok().build();
//             }
//         }
        
//     }
//     return ResponseEntity.notFound().build();
// }

@GetMapping("/appointments/delete/{uid}")
public String deleteAppointment(@PathVariable Integer uid) {
    appointmentRepo.deleteById(uid);
    //return "appointment/showAllAppointments"; // Redirect to the showAllAppointments page after deletion
    return "redirect:/appointments/view";
}
	
@GetMapping("/appointments/update/{uid}")
    public String showUpdateForm(@PathVariable Integer uid, Model model) {
        // Logic to retrieve appointment by ID
        List<userAppointment> optionalAppointment = appointmentRepo.findByUid(uid);
        if (!optionalAppointment.isEmpty()) {
            userAppointment appointment = optionalAppointment.get(0);
            model.addAttribute("appointment",appointment);
            return "appointment/showAllUpdate"; // Return the name of the HTML file for the update form
        } else {
            // Handle case where appointment with the given ID is not found
            return "errorPage"; // Return the name of the HTML file for error page
        }
    }

    @PostMapping("/appointments/update/{uid}")
    public String updateShowAppointment(@RequestParam Map<String,String> appointmentData, HttpServletResponse response) {
		int uid = Integer.parseInt(appointmentData.get("uid"));
		String name = appointmentData.get("name");
        String email = appointmentData.get("email");
        String dateString = appointmentData.get("slots");
        String description = appointmentData.get("description");
        String appointmentTime = appointmentData.get("time");
        Date appointmentDate = null;
        String service = appointmentData.get("service");
        String make = appointmentData.get("make");
        String carModel = appointmentData.get("carModel");
        String year = appointmentData.get("year");
        String phoneNumber = appointmentData.get("phoneNumber");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try {
        appointmentDate = dateFormat.parse(dateString);
        // appointmentTime = LocalTime.parse(dateString.substring(11)); // Extract time part
    } catch (ParseException | DateTimeParseException e) {
        e.printStackTrace(); 
        // Handle parsing exception here
    }    
    
    	userAppointment editAppointment = appointmentRepo.findByUid(uid).get(0);
    	editAppointment.setUsername(name);
    	editAppointment.setEmail(email);
    	editAppointment.setAppointmentDate(appointmentDate);
    	editAppointment.setDescription(description);
    	editAppointment.setAppointmentTime(appointmentTime);
    	editAppointment.setService(service);
    	editAppointment.setMake(make);
    	editAppointment.setCarModel(carModel);
    	editAppointment.setYear(year);
    	editAppointment.setPhoneNumber(phoneNumber);
    	
    	appointmentRepo.save(editAppointment);
    	response.setStatus(201);
		return "redirect:/appointments/view";
	}
    
	


	@GetMapping("/appointments/view")
	public String getMethodName(Model model) {
		// List<userAppointment> appointments = appointmentRepo.findAll();
		List<userAppointment> appointments = appointmentRepo.findAll(Sort.by(Sort.Direction.ASC, "appointmentDate", "appointmentTime"));
		// List<userAppointment> appointments = appointmentRepo.findAll(Sort.by(Sort.Direction.ASC, "appointment_date"));
		model.addAttribute("appointments", appointments);
        System.out.println("view page show");

		return"appointment/showAllAppointments";
	}
	
	@PostMapping("/updateAppointment")
	public String updateAppointment(Model model, HttpSession session, @RequestParam String uid) {
		User user = (User) session.getAttribute("session_user");
		if(user == null) return "users/login";
		else {
			model.addAttribute("user", user);
			List<userAppointment> selectedAppointment = appointmentRepo.findByUid(Integer.parseInt(uid));
			model.addAttribute("appt", selectedAppointment.get(0));
			System.out.println(selectedAppointment.get(0).getMake());
		}
		
		List<userAppointment>appointments = appointmentRepo.findAll(Sort.by(Sort.Direction.ASC,"uid"));
        model.addAttribute("appointment",appointments);
		
		return "appointment/updateAppointment";
	}
	
	@PostMapping("/updateAppointmentInformation")
	public String updateSelectedAppointment(@RequestParam Map<String,String> appointmentData, HttpServletResponse response) {
		int uid = Integer.parseInt(appointmentData.get("uid"));
		String name = appointmentData.get("name");
        String email = appointmentData.get("email");
        String dateString = appointmentData.get("slots");
        String description = appointmentData.get("description");
        String appointmentTime = appointmentData.get("time");
        Date appointmentDate = null;
        String service = appointmentData.get("service");
        String make = appointmentData.get("make");
        String carModel = appointmentData.get("carModel");
        String year = appointmentData.get("year");
        String phoneNumber = appointmentData.get("phoneNumber");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try {
        appointmentDate = dateFormat.parse(dateString);
        // appointmentTime = LocalTime.parse(dateString.substring(11)); // Extract time part
    } catch (ParseException | DateTimeParseException e) {
        e.printStackTrace(); 
        // Handle parsing exception here
    }    
    
    	userAppointment editAppointment = appointmentRepo.findByUid(uid).get(0);
    	editAppointment.setUsername(name);
    	editAppointment.setEmail(email);
    	editAppointment.setAppointmentDate(appointmentDate);
    	editAppointment.setDescription(description);
    	editAppointment.setAppointmentTime(appointmentTime);
    	editAppointment.setService(service);
    	editAppointment.setMake(make);
    	editAppointment.setCarModel(carModel);
    	editAppointment.setYear(year);
    	editAppointment.setPhoneNumber(phoneNumber);
    	
    	appointmentRepo.save(editAppointment);
    	response.setStatus(201);
		return "appointment/appointmentConfirmation";
	}

    @GetMapping("/delete")
    public String showDeleteForm(Model model, HttpSession session) {
    User user = (User) session.getAttribute("session_user");
    if (user != null) {
        model.addAttribute("user", user);
    }
    return "appointment/delete"; // Return the name of the HTML file for the delete form
    }



}
