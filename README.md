# Spring-Basic-Security
스프링 시큐리티 6 프레임워크를 활용하여 인증/인가 시스템을 구현하려고 합니다.  
계속해서 기능들을 추가해서 최종적으로 Oauth의 기능까지 활용하도록 있도록 제작하는게 최종 목표입니다.

각 페키지에서는 다음과 같은 내용을 다루고 있습니다.

- basic_auth : 기본 인증/인가 구현
- jwt_auth : jwt방식으로 인증을 진행 적용, jwt 발급, 인증 인가 구현
- jwt_deepen : jwt 발급을 넘어 보안을 위한 여러가지 심화적인 구현
- basic_oauth : OAuth2 소셜 로그인 구현
- jwt_ouauth_session : 소셜 로그인인 인증후 세션을 생성하는 시큐리티 구현
- jwt_ouauth_session : 소셜 로그인인 인증후 jwt를 발급하는 시큐리티 구현

## 시큐리티 버전별 특성
스프링은 버전에 따라 구현 방식이 변경되는데 시큐리티의 경우 특히 세부 버전별로 구현 방법이 다르기 때문에 버전 마다 구현  
특징은 확인해야 합니다.

새로운 버전이 출시될 때마다 GitHub의 Spring 레포지토리에서 Security의 Release 항목을 통해 변경된 점을 확인할 수 있습니다.  
[스프링 시큐리티 GitHub 릴리즈 노트 바로가기](https://github.com/spring-projects/spring-security/releases)


## Security Config 주요 버전별 구현

- 스프링 부트 2.X.X ~ 2.6.X (스프링 5.X.X ~ 5.6.X)
```java
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/").authenticated()
                .anyRequest().permitAll();

    }
}
```

- 스프링 부트 2.7.X ~ 3.0.X (스프링 5.7.X M2 ~ 6.0.X)
```java
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests()
                .requestMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated();

        return http.build();
    }
}
```

- 스프링 부트 3.1.X ~ (스프링 6.1.X)
```java
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests((auth) -> auth
                  .requestMatchers("/login", "/join").permitAll()
                  .anyRequest().authenticated()
        );

        return http.build();
    }
}
```

3.1.X 버전 부터 람다형식 표현 필수

## DB 연결
데이터베이스의 경우 정말 민감한 정보이기 때문에 코드에서 분리하여 관리하였습니다.
하지만 이곳은 로컬에서 계속해서 작업할 것이기 때문에 이번에는 `.env` 파일을 사용해서 관리하도록 하겠습니다.

`dotenv`을 사용하면 설정을 `.env`에서 관리할 수 있습니다.

```java
implementation 'me.paulschwarz:spring-dotenv:4.0.0'
```

.env
```etc
DB_URL=jdbc:mariadb://DBURL적으세요:포트번호/DB명
DB_USERNAME=이름
DB_PASSWORD=비밀번호
```

.yml
```
  config:
    import: optional:file:.env[.properties]
```
저는 application.properties 파일과 동일한 위치에 두었습니다. **위치를 바꾸고 싶다면?**  
spring.config.import=optional:file:.env[.properties] 수정 -> ./.env 이부분의 경로를 수정하세요