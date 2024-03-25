package com.westside.west_side_auto.models;


import java.time.LocalTime;
import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name="appointment")
public class userAppointment{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uid;
	
	private String username;
	private String email;
	private String description;
    private Date appointmentDate;
    //private LocalTime appointmentTime;
    private String appointmentTime;
    public userAppointment() {
    }
    
    public userAppointment(String username, String email, String description, Date appointmentDate,
            String appointmentTime) {
        this.username = username;
        this.email = email;
        this.description = description;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
    }
    public int getUid() {
        return uid;
    }
    public String getAppointmentTime() {
        return appointmentTime;
    }
    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getAppointmentDate() {
        return appointmentDate;
    }
    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
    

    
}