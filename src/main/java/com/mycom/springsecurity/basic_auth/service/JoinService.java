package com.mycom.springsecurity.basic_auth.service;

import com.mycom.springsecurity.basic_auth.dto.JoinDTO;
import com.mycom.springsecurity.basic_auth.entity.UserEntity;
import com.mycom.springsecurity.basic_auth.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    public void joinProcess(JoinDTO joinDTO) {
        // db에 이미 동일한 username을 가진 회원이 존재하는지

        UserEntity data = new UserEntity();

        data.setUsername(joinDTO.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        data.setRole("ROLE_USER");

        userRepository.save(data);
    }
}
