package com.westside.west_side_auto.models;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<review,Integer>{
    ArrayList<review> findByDate(Date date);
}
