package com.westside.west_side_auto.models;
import java.util.ArrayList;
import java.time.LocalTime;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;


public interface appointmentRepository extends JpaRepository<userAppointment,Integer>{
	ArrayList<userAppointment> findByUid(int uid);
	ArrayList<userAppointment> findByUsernameAndEmail(String name, String email);
	ArrayList<userAppointment> findByAppointmentDateAndAppointmentTime(Date appointmentDate, LocalTime appointmentTime);
}
