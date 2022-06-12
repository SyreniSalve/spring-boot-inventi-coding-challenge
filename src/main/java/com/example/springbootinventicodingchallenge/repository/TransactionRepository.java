package com.example.springbootinventicodingchallenge.repository;

import com.example.springbootinventicodingchallenge.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    List<TransactionEntity> findAllByOperationDateBetween(@Param("from") LocalDate dateFrom,
                                                          @Param("to") LocalDate dateTo);

    List<TransactionEntity> findAll();

}
