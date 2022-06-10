package com.example.springbootinventicodingchallenge.repository;

import com.example.springbootinventicodingchallenge.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
