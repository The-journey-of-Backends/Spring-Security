package com.mycom.springsecurity.basic_auth.service;

import com.mycom.springsecurity.basic_auth.common.exception.UsernameAlreadyExistsException;
import com.mycom.springsecurity.basic_auth.dto.JoinDTO;
import com.mycom.springsecurity.basic_auth.entity.UserEntity;
import com.mycom.springsecurity.basic_auth.entity.UserRole;
import com.mycom.springsecurity.basic_auth.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional // 메소드 레벨의 트랜잭션을 보장
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    public void joinProcess(JoinDTO joinDTO) {
        // db에 이미 동일한 username을 가진 회원이 존재하는지
        if(userRepository.existsByUsername(joinDTO.getUsername())) {
            throw new UsernameAlreadyExistsException("이미 동일한 username이 있습니다. : "+joinDTO.getUsername());
        }
        UserRole role = determineUserRole(joinDTO.getUsername());

        UserEntity user = UserEntity.builder()
                .username(joinDTO.getUsername())
                .password(bCryptPasswordEncoder.encode(joinDTO.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);
    }

    private UserRole determineUserRole(String username) {
        return "admin".equals(username) ? UserRole.ROLE_ADMIN : UserRole.ROLE_USER;
    }
}
