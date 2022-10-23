package io.security.oauth2.springsecurityoauth2;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


/*
    AbstractHttpConfigurer 를 상속받는다.
    이 클래스가 실제 SecurityConfigurer 내 클래스를 상속받고 있다.
    우리는 여기서 우리가 만든 필터클래스나 인증인가에 필요한 여러가지 설정들을 할 수 있다.
 */
public class CustomSecurityConfigurer extends AbstractHttpConfigurer<CustomSecurityConfigurer, HttpSecurity> {

    private boolean isSecure;

    @Override
    public void init(HttpSecurity builder) throws Exception {
        super.init(builder);
        /*
            우리는 초기화 과정때 우리가 만든 설정이 진행되는가를 보기위한 작업임.
         */
        System.out.println("init method started...");
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        super.configure(builder);
        System.out.println("configure method started...");
        if(isSecure) {
            System.out.println("https is required...");
        } else {
            System.out.println("https is optional...");
        }
    }

    // isSecure를 외부에서 받도록 한다.
    public CustomSecurityConfigurer setFlag(boolean isSecure) {
        this.isSecure = isSecure;
        // 객체를 생성하고 나서 값을 변경한 후에 즉시 설정에서 적용하기위 해서 this로 반환했다.
        // 그리고 builder 형식처럼 넘겨줄 수 있다. new CustomSecurityConfigurer().setFlag(true).setFlag(false)
        return this;
    }
}










