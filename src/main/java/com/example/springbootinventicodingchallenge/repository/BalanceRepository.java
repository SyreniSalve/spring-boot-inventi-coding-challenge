package com.example.springbootinventicodingchallenge.repository;

import com.example.springbootinventicodingchallenge.entity.BalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BalanceRepository extends JpaRepository<BalanceEntity, Long> {

    List<BalanceEntity> findAllByDate(LocalDate date);


}
