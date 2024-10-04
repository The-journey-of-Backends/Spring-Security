package com.mycom.springsecurity.basic_auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class JoinDTO {
    private String username;
    private String password;
}
