package com.westside.west_side_auto.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PriceEstimateController {

    @PostMapping("/calculatePrice")
    public String calculatePrice(@RequestParam("headliner") int headlinerPrice,
    @RequestParam("ambientLighting") int ambientLightingPrice,
                                @RequestParam(value = "newHeadliner", required = false) Integer newHeadlinerPrice,
                                @RequestParam("stereo") int stereoPrice,
                                Model model) {
        // Calculate total price
        int totalPrice = headlinerPrice + ambientLightingPrice + stereoPrice;
        if (newHeadlinerPrice != null) {
            totalPrice += newHeadlinerPrice;
        }

        // Add total price to the model
        model.addAttribute("totalPrice", totalPrice);

        // Return the view to display the total price
        return "/priceEstimate/price-result";
    }
}

