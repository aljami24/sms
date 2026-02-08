package com.smha.sms.common.config;

import com.smha.sms.user.service.SpringSecurityAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//@EnableMethodSecurity(prePostEnabled = true)
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/","/login", "/css/**","/image/**", "/js/**").permitAll()
                        .requestMatchers("/employee/**", "/student/**", "/reports/**").hasAnyRole("ADMIN","REGISTER","ACCOUNT")
                        .requestMatchers("/user/**", "/role/**").hasRole("ADMIN")
                        .requestMatchers("/income/**","/expense/**").hasAnyRole("ADMIN","ACCOUNT")
                        .requestMatchers("/attendance/**").hasAnyRole("ADMIN","REGISTER")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout").logoutSuccessUrl("/login"));
        return http.build();

    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }
}
