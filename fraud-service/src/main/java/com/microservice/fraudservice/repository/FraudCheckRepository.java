package com.microservice.fraudservice.repository;

import com.microservice.fraudservice.entity.FraudCheckHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FraudCheckRepository extends JpaRepository<FraudCheckHistory,String> {

    FraudCheckHistory findByEmail(String email);

}
