package com.westside.west_side_auto.controllers;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.westside.west_side_auto.models.ClientCarDataRepository;
import com.westside.west_side_auto.models.clientCarData;

@Controller
public class clientCarDataController<clientDataRepository> {
    
    @Autowired
    private ClientCarDataRepository clientDataRepo; 
    
    @PostMapping("/clientCarDataSubmit")
    public String submitClientCarDataForm(@RequestParam Map<String,String> appointmentData, Model model) {
    
        String name = appointmentData.get("clientName");
        String email = appointmentData.get("clientEmail");
        String phone = appointmentData.get("   clientPhone");
        String address = appointmentData.get("clientAddress");
        String todayDate = appointmentData.get("todayDate");
        String car = appointmentData.get("clientCar");
        String carMake = appointmentData.get("clientCarMake");
        String carModel = appointmentData.get("clientCarModel");
        String carYear = appointmentData.get("clientCarYear");
        String carServices = appointmentData.get("clientCarServices");
        String carPics = appointmentData.get("clientCarPics");
        String comments = appointmentData.get("anyComments");

        clientCarData newClientData = new clientCarData(name, email, phone, address,todayDate, car, carMake, carModel, carYear, carServices, carPics, comments);

        clientDataRepo.save(newClientData);
        return "/clientCardata/clientCarDataConfirmation";
    }


    @GetMapping("/viewByDate")
    public String viewByDate(@RequestParam("todayDate") String todayDate, Model model) {
        System.out.println("It reached here");
        List<clientCarData> data = clientDataRepo.findByTodayDate(todayDate);
        model.addAttribute("data", data);
        
        return "clientCarData/viewFormsByDate"; 
    }
}
