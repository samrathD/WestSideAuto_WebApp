package com.westside.west_side_auto.models;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Integer>{
	ArrayList<User> findByUid(int uid);
}
