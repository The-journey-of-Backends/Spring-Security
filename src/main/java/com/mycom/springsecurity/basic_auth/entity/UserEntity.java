package com.mycom.springsecurity.basic_auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이 컬럼 값이 오직 유니크 할 수 있도록
    @Column(unique = true)
    private String username;

    private String password;

    private String role;
}
