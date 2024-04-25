package com.osguri.ongforall.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf
        .ignoringRequestMatchers("/h2-console/**")
        .disable())
        .headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/", "/auth/**", "/assets/**", "/h2-console/**").permitAll()
            .anyRequest().authenticated())
            .formLogin(formLogin -> formLogin
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .defaultSuccessUrl("/auth/regirect", true)
                .failureUrl("/auth/login?error=true")
                .permitAll())
            ;
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}