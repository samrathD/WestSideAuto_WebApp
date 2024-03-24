package com.westside.west_side_auto.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.westside.west_side_auto.models.EmailStructure;
import com.westside.west_side_auto.service.EmailSenderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/mail")
public class EmailSenderController {

    @Autowired
    private EmailSenderService emailSenderService;

    // @PostMapping("/send/{mail}")
    // public String SendMail(@PathVariable String mail,@RequestBody EmailStructure emailStructure) {
    //     //TODO: process POST request
    //     //EmailStructure emailStructure;
    //     emailSenderService.sendEmail(mail, emailStructure);
    //     return "Successfully sent the email";
    // }
    @PostMapping("/send/{mail}")
    public String SendMail(@PathVariable String mail) {
        //TODO: process POST request
        EmailStructure emailStructure = new EmailStructure ("Test","testing");
        emailSenderService.sendEmail(mail, emailStructure);
        return "/appointment/appointmentConfirmation";
    }
    
}
