package com.mycom.springsecurity.basic_auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// 인가
// 특정한 경로에 요청이 오면 Controller 클래스에 도달하기 전 필터에서 Spring Security가 검증을 함
// 1. 해당 경로의 접근은 누구에게 열려 있는지
// 2. 로그인이 완료된 사용자인지
// 3. 해당되는 role을 가지고 있는지

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 패스워드 암호화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 인가 설정을 진행하는 클래스
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 참고 사항 : 스프링 3.1.x 버전 부터 람다형식 표현 필수
        http
                .authorizeRequests((auth)-> auth // 이 authorizeRequest를 통해서 특정한 경로의 요청을 허용하거나 거부할 수 있습니다.
                        // requestMatchers의 경우 작성한 경로에서 특정한 잡업을 하고 싶다는 설정을 진행할 수 있는 메서드입니다.
                        // 각 메서드를 선언하고 이 메서드 뒤에 특정한 권한을 줄 수 있습니다.
                        /*
                        * permitAll : 모든 사용자에게 로그인을 하지 않아도 접근할 수 있는 메서드
                        * hasRole : 특정한 규칙이 있어야 이 경로에 접근할 수 있습니다.
                        * authenticated : 로그인만 진행하면 모두 접근할 수 있는 것입니다.
                        * denyAll : 모든 사용자의 접근을 막는 메소드
                        * */
                        .requestMatchers("/","login","/loginProc","/join", "/joinProc").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN","USER")

                        // 위에서 처리하지 못한 나머지 경로들
                        .anyRequest().authenticated()
                );

        http
                .csrf((auth) -> auth.disable());

        http
                .formLogin((auth)-> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProcess")
                        .permitAll()
                );

        return http.build();
    }
}
