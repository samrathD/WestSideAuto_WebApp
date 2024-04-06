package com.westside.west_side_auto.controllers;

import com.westside.west_side_auto.models.ReviewRepository;
import com.westside.west_side_auto.models.review;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class reviewController{

    @Autowired
    private ReviewRepository reviewRepository;

    // @PostMapping("/submitReview")
    // public String submitReview(@RequestParam Map<String,String> clientReview, Model model){
    //     String username = clientReview.get("username");
    //     String dateString = clientReview.get("todayDate");
    //     int rating = Integer.parseInt(clientReview.get("rating"));
    //     String review = clientReview.get("review");

    //     Date todayDate = null;
    //     try {
    //         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //         todayDate = dateFormat.parse(dateString);
    //     } catch (ParseException e) {
    //         e.printStackTrace();
    //     }

    //     review newReview = new review(username, todayDate, rating, review);
    //     reviewRepository.save(newReview);
    //     return "/reviews/reviewSubmitted";
    // }

    @PostMapping("/submitReview")
public String submitReview(@RequestParam Map<String,String> clientReview, Model model){
    String username = clientReview.get("username");
    String dateString = clientReview.get("todayDate");
    String ratingString = clientReview.get("rating"); 


    if(username == null || username.isEmpty()){
        return "/reviews/reviewForm";
    }

    Date todayDate = null;
    if (dateString != null && !dateString.isEmpty()) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            todayDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            
        }
    }


    int rating = 0; 
    if (ratingString != null && !ratingString.isEmpty()) {
        rating = Integer.parseInt(ratingString); 
    }

    String review = clientReview.get("review");

    review newReview = new review(username, todayDate, rating, review);
    reviewRepository.save(newReview);
    return "/reviews/reviewSubmitted";
}





}
