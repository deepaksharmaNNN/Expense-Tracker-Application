package com.deepak.sharma.authservice.service;

import com.deepak.sharma.authservice.dtos.UserInfoDto;
import com.deepak.sharma.authservice.entities.UserInfo;
import com.deepak.sharma.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new CustomUserDetails(user);
    }
    public UserInfo checkIfUserAlreadyExist(UserInfoDto userInfoDto){
        return userRepository.findByUserName(userInfoDto.getUserName());
    }

    public Boolean signupUser(UserInfoDto userInfoDto){
        userInfoDto.setPassword("");
        if(Objects.nonNull(checkIfUserAlreadyExist(userInfoDto))){
            return false;
        }
        UserInfo userInfo = UserInfo.builder()
                .userId(UUID.randomUUID().toString())
                .userName(userInfoDto.getUserName())
                .password(userInfoDto.getPassword())
                .roles(new HashSet<>())
                .build();
        userRepository.save(userInfo);
        return true;
    }
}
