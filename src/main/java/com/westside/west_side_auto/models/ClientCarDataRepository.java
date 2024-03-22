
package com.westside.west_side_auto.models;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;




public interface ClientCarDataRepository extends JpaRepository<clientCarData, Integer> {
    ArrayList<clientCarData> findByUid(int uid);
    ArrayList<clientCarData> findByTodayDate(String todayDate);


}
