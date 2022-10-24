package io.security.oauth2.springsecurityoauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SecurityConfig {

    // WebSecurityConfigurer 가 Deprecated되면서 Bean을 생성해서 사용하는 방식으로 변경되었다.
    /*
        SecurityFilterChain은 Prototype으로 등록되기 때문에 여러개의 객체를 만들어서 쓸 수 있다.
        2개의 SecurityFilterChain 을 등록하면 2개 모두 등록된다.
     */
    @Bean
    @Order(0)
    SecurityFilterChain securityFilterChainByBasicAuthentication(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();
        http.httpBasic().authenticationEntryPoint((request, response, authException) -> {
            System.out.println("http Basic EntryPoint");
            response.addHeader("WWW-Authenticate", "Basic realm=localhost");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        });
        // 세션 미사용방식 -> 인증 이후 SecurityContext 활용 불가
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        http.apply(new CustomSecurityConfigurer().setFlag(true));
        return http.build();
    }

//    @Bean
    @Order(1)
    SecurityFilterChain securityFilterChain2(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();
        http.formLogin();
//        http.apply(new CustomSecurityConfigurer().setFlag(true));
        return http.build();
    }

//    @Bean
    @Order(2)
    SecurityFilterChain securityFilterChain3(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();

        // Custom한 EntryPoint 를 등록하면 커스텀한 객체가 우선시 동작하고 이외의 EntryPoint는 무시된다.
        // 커스텀하지 않앗다면 formLogin 방식이 우선 진행된다.
        http.formLogin();
        http.httpBasic();
        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                System.out.println("...custom");
            }
        });

        return http.build();
    }
}
