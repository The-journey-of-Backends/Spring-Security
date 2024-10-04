package com.mycom.springsecurity.basic_auth.service;

import com.mycom.springsecurity.basic_auth.dto.CustomUserDetails;
import com.mycom.springsecurity.basic_auth.entity.UserEntity;
import com.mycom.springsecurity.basic_auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userDatas = userRepository.findByUsername(username);

        if(userDatas != null) {
            return new CustomUserDetails(userDatas);
        }

        return null;
    }
}
