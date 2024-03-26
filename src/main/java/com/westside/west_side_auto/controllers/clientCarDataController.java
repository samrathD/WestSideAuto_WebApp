package com.westside.west_side_auto.controllers;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.westside.west_side_auto.models.ClientCarDataRepository;
import com.westside.west_side_auto.models.clientCarData;

@Controller
public class clientCarDataController<clientDataRepository> {
    
    @Autowired
    private ClientCarDataRepository clientDataRepo; 
    
    @PostMapping("/clientCarDataSubmit")
    public String submitClientCarDataForm(@RequestParam Map<String,String> carData, Model model) throws ParseException {
    
        String name = carData.get("clientName");
        String email = carData.get("clientEmail");
        String phone = carData.get("clientPhone");
        String address = carData.get("clientAddress");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = dateFormat.parse(carData.get("todayDate"));
        String car = carData.get("clientCar");
        String carMake = carData.get("clientCarMake");
        String carModel = carData.get("clientCarModel");
        String carYear = carData.get("clientCarYear");
        Date carServices = dateFormat.parse(carData.get("clientCarServices"));
        String carPics = carData.get("clientCarPics");
        String comments = carData.get("anyComments");

        clientCarData newClientData = new clientCarData(name, email, phone, address, todayDate, car, carMake, carModel, carYear, carServices, carPics, comments);

        clientDataRepo.save(newClientData);
        return "/clientCardata/clientCarDataConfirmation";
    }


    @GetMapping("/viewByClientPhone")
    public String viewByPhone(@RequestParam("clientPhone") String clientPhone, Model model) {
        List<clientCarData> data = clientDataRepo.findByClientPhone(clientPhone);
        model.addAttribute("data", data);
        
        return "clientCarData/viewFormsByPhone"; 
    }

    @Transactional
    @PostMapping("/deleteEntry")
    public ResponseEntity<String> deleteEntryByPhone(@RequestParam("clientPhone") String clientPhone) {
    ArrayList<clientCarData> deletedEntry = clientDataRepo.deleteByClientPhone(clientPhone);
    if(deletedEntry.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entry not found");
    }
    else {
        return ResponseEntity.ok("Entry deleted successfully");
    }
}

}
