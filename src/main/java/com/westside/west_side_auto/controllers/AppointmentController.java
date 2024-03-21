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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.westside.west_side_auto.models.EmailStructure;
import com.westside.west_side_auto.models.appointmentRepository;
import com.westside.west_side_auto.models.userAppointment;
import com.westside.west_side_auto.service.EmailSenderService;

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

    @PostMapping("/appointments/add")
    public String addAppointment(@RequestParam Map<String,String> appointmentData, Model model) {
    String name = appointmentData.get("name");
    String email = appointmentData.get("email");
    String dateString = appointmentData.get("slots");
    String description = appointmentData.get("description");
    
    Date appointmentDate = null;
    LocalTime appointmentTime = null;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try {
        appointmentDate = dateFormat.parse(dateString);
        appointmentTime = LocalTime.parse(dateString.substring(11)); // Extract time part
    } catch (ParseException | DateTimeParseException e) {
        e.printStackTrace(); 
        // Handle parsing exception here
    }

    // Check if there's an existing appointment for the same date and time
    List<userAppointment> existingAppointments = appointmentRepo.findByAppointmentDateAndAppointmentTime(appointmentDate, appointmentTime);
    if (!existingAppointments.isEmpty()) {
        model.addAttribute("description", "An appointment already exists for this date and time.");
        model.addAttribute("existingAppointments", existingAppointments);
        model.addAttribute("name", name); 
        model.addAttribute("email", email);
        model.addAttribute("description", description);
        model.addAttribute("appointmentDate", dateString);
        model.addAttribute("appointmentTime", appointmentTime);
        System.out.println("An appointment already exists for this date and time.");
        return "/appointment/appointmentDateBooked";
    }

    // Check if there's an existing appointment for the user and email
    List<userAppointment> existingAppointmentsByNameAndEmail = appointmentRepo.findByUsernameAndEmail(name, email);
    if (!existingAppointmentsByNameAndEmail.isEmpty()) {
        model.addAttribute("description", "An appointment already exists for this user and email.");
        model.addAttribute("existingAppointments", existingAppointmentsByNameAndEmail);
        model.addAttribute("name", name); 
        model.addAttribute("email", email);
        model.addAttribute("description", description);
        model.addAttribute("appointmentDate", dateString);
        model.addAttribute("appointmentTime", appointmentTime);
        System.out.println("An appointment already exists for this user and email.");
        return "/appointment/appoinmentExistsConfirmation";
    }

    userAppointment appointment = new userAppointment(name, email, description, appointmentDate, appointmentTime);
    appointmentRepo.save(appointment);
    
    //email being made
    EmailStructure emailStructure = new EmailStructure("Appointment Confirmation", "Your appointment is confirmed for...");
    
    System.out.println("It works here!");
    emailSenderService.sendEmail("samrath2004@gmail.com", emailStructure);
    return "/appointment/appointmentConfirmation";
}



@PostMapping("/appointments/addConfirmation")
    public String addAppointmentConfirmation(@RequestParam Map<String,String> formData, Model model) {
        String replaceOption = formData.get("replace");


        if ("yes".equals(replaceOption)) {
            String name = formData.get("name");
            String email = formData.get("email");
            String dateString = formData.get("appointmentDate");
            String description = formData.get("description");
            Date appointmentDate = null;
			LocalTime appointmentTime = null;


            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                appointmentDate = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

			try {
				appointmentTime = LocalTime.parse(dateString.substring(11)); // Extract time part
			} catch (Exception e) {
				e.printStackTrace();
			}


            // Replace the existing appointment
            List<userAppointment> existingAppointments = appointmentRepo.findByUsernameAndEmail(name, email);
            if (!existingAppointments.isEmpty()) {
                for (userAppointment appointment : existingAppointments) {
                    appointment.setDescription(description);
                    appointment.setAppointmentDate(appointmentDate);
					appointment.setAppointmentTime(appointmentTime);
                    appointmentRepo.save(appointment);
                }
            }
            return "/appointment/appointmentConfirmation";
        } else {
            // Redirect to the add appointment page after 5 seconds
            model.addAttribute("redirectDelay", 5000); // 5 seconds delay
            return "redirect:/appointments/add";
        }
    }

	@GetMapping("/appointments/add")
    public String showAddAppointmentForm() {
        // Return the view name for the add appointment form
        return "redirect:/html/schedule.html"; // Adjust the view name as per your application
    }

	@Transactional
    @PostMapping("/appointments/delete")
    public String deleteAppointment(@RequestParam String name, @RequestParam String email) {
        List<userAppointment> appointmentsToDelete = appointmentRepo.findByUsernameAndEmail(name, email);
        if (!appointmentsToDelete.isEmpty()) {
            appointmentRepo.deleteAll(appointmentsToDelete);
            return "/appointment/deleteConfirmation";
        } else {
            return "/appointment/NoAppointment";
        }
    }

	@GetMapping("/appointments/view")
	public String getMethodName(Model model) {
		// List<userAppointment> appointments = appointmentRepo.findAll();
		List<userAppointment> appointments = appointmentRepo.findAll(Sort.by(Sort.Direction.ASC, "appointmentDate", "appointmentTime"));


		// List<userAppointment> appointments = appointmentRepo.findAll(Sort.by(Sort.Direction.ASC, "appointment_date"));


		model.addAttribute("appointments", appointments);

		return"/appointment/showAllAppointments";
	}
}
