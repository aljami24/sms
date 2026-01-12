package com.smatik.sms.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/attendance/**","/accounting/**","/student/**","/employee/**").hasAnyRole("ADMIN","REGISTER_ADMIN","ACCOUNT_ADMIN")
                        .requestMatchers("/academic/**","/user/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form-> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/",true)
                        .permitAll())
                .logout(logout-> logout
                        .logoutUrl("/logout").logoutSuccessUrl("/login"));
        return http.build();

    }
}
