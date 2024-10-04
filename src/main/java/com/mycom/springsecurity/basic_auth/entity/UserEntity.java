package com.mycom.springsecurity.basic_auth.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "user")
@Getter
// protected로 설정함으로써 무분별한 객체 생성 방지
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이 컬럼 값이 오직 유니크 할 수 있도록
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Builder
    public UserEntity(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
