package com.deepak.sharma.userservice.repository;

import com.deepak.sharma.userservice.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, String> {
    Optional<UserInfo> findByEmail(String email);
    Optional<UserInfo> findByUserId(String userId);
}
