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
    private String phoneNumber;
	private String email;
	private String description;
    private Date appointmentDate;
    private String service;
    //private LocalTime appointmentTime;
    private String appointmentTime;
    private String make;
    private String carModel;
    private String year;

    public userAppointment() {
    }
    
    // public userAppointment(String username, String email, String description, Date appointmentDate,
    //         String appointmentTime, String service) {
    //     this.username = username;
    //     this.email = email;
    //     this.description = description;
    //     this.appointmentDate = appointmentDate;
    //     this.appointmentTime = appointmentTime;
    //     this.service = service;
    // }

    public userAppointment(String username, String email, String description, Date appointmentDate,
            String service, String appointmentTime, String make, String carModel, String year, String phoneNumber) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.description = description;
        this.appointmentDate = appointmentDate;
        this.service = service;
        this.appointmentTime = appointmentTime;
        this.make = make;
        this.carModel = carModel;
        this.year = year;
        this.phoneNumber = phoneNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    

    
}