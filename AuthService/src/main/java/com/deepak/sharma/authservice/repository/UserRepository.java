package com.deepak.sharma.authservice.repository;

import com.deepak.sharma.authservice.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, Long> {
    UserInfo findByUserName(String userName);
}
