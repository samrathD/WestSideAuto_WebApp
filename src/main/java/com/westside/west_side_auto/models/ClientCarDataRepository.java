
package com.westside.west_side_auto.models;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;




public interface ClientCarDataRepository extends JpaRepository<clientCarData, Integer> {
    ArrayList<clientCarData> findByUid(int uid);
    ArrayList<clientCarData> findByClientNameAndClientEmail(String clientName,String clientEmail);
    ArrayList<clientCarData> findByClientPhone(String clientPhone);
    ArrayList<clientCarData> deleteByClientPhone(String clientPhone);
}
