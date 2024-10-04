package com.mycom.springsecurity.basic_auth.repository;

import com.mycom.springsecurity.basic_auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    // 중복 검증
    boolean existsByUsername(String username);

    // 유저 찾기
    UserEntity findByUsername(String username);
}
