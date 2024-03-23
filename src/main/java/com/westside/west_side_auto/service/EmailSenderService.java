package com.westside.west_side_auto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.westside.west_side_auto.models.EmailStructure;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender emailSender;
    @Value("${spring.mail.username}")
    private String fromMail;

    public void sendEmail(String mail, EmailStructure emailStructure){
      SimpleMailMessage simpleMailMessage  = new SimpleMailMessage();
      simpleMailMessage.setFrom(fromMail);
      simpleMailMessage.setSubject(emailStructure.getSubject());
      simpleMailMessage.setText(emailStructure.getMessage());
      simpleMailMessage.setTo(mail);
      System.out.println("It is working in email sender service");

      emailSender.send(simpleMailMessage);
    }
}
