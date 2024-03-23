package com.westside.west_side_auto.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="clientData")
public class clientCarData {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uid;

    private String clientName;
    private String clientEmail;
    private String clientPhone;
    private String clientAddress;
    private String todayDate ;
    private String clientCar ;
    private String clientCarMake;
    private String clientCarModel;
    private String clientCarYear;
    private String clientCarServices;
    private String clientCarPics ;
    private String anyComments ;

    public clientCarData() {
    }
    public clientCarData(String clientName, String clientEmail, String clientPhone, String clientAddress,
            String todayDate, String clientCar, String clientCarMake, String clientCarModel, String clientCarYear,
            String clientCarServices, String clientCarPics, String anyComments) {
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.clientPhone = clientPhone;
        this.clientAddress = clientAddress;
        this.todayDate = todayDate;
        this.clientCar = clientCar;
        this.clientCarMake = clientCarMake;
        this.clientCarModel = clientCarModel;
        this.clientCarYear = clientCarYear;
        this.clientCarServices = clientCarServices;
        this.clientCarPics = clientCarPics;
        this.anyComments = anyComments;
    }
    
    public String getClientName() {
        return clientName;
    }
    public String getTodayDate() {
        return todayDate;
    }
    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    public String getClientEmail() {
        return clientEmail;
    }
    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }
    public String getClientPhone() {
        return clientPhone;
    }
    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }
    public String getClientAddress() {
        return clientAddress;
    }
    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }
    public String getClientCar() {
        return clientCar;
    }
    public void setClientCar(String clientCar) {
        this.clientCar = clientCar;
    }
    public String getClientCarMake() {
        return clientCarMake;
    }
    public void setClientCarMake(String clientCarMake) {
        this.clientCarMake = clientCarMake;
    }
    public String getClientCarModel() {
        return clientCarModel;
    }
    public void setClientCarModel(String clientCarModel) {
        this.clientCarModel = clientCarModel;
    }
    public String getClientCarYear() {
        return clientCarYear;
    }
    public void setClientCarYear(String clientCarYear) {
        this.clientCarYear = clientCarYear;
    }
    public String getClientCarServices() {
        return clientCarServices;
    }
    public void setClientCarServices(String clientCarServices) {
        this.clientCarServices = clientCarServices;
    }
    public String getClientCarPics() {
        return clientCarPics;
    }
    public void setClientCarPics(String clientCarPics) {
        this.clientCarPics = clientCarPics;
    }
    public String getAnyComments() {
        return anyComments;
    }
    public void setAnyComments(String anyComments) {
        this.anyComments = anyComments;
    }

    

}
