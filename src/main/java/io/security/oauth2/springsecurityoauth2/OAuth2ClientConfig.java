package io.security.oauth2.springsecurityoauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class OAuth2ClientConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*
            Customizer 를 사용하면 커스터마이징을 하기위한 설정을 할 수 있다.
         */
        http.authorizeRequests(authRequest ->
                authRequest
                        .antMatchers("/loginPage").permitAll()
                        .anyRequest().authenticated()
        );
//        http.oauth2Login(oauth2 -> oauth2.loginPage("/loginPage"));
        http.oauth2Login(Customizer.withDefaults()); // 기본설정으로 변경
        return http.build();
    }
}
