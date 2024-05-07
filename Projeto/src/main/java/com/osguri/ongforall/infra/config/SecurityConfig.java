package com.osguri.ongforall.infra.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.osguri.ongforall.entities.CustomUserDetails;
import com.osguri.ongforall.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
                .disable())
                .headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/auth/**", "/assets/**", "/h2-console/**").permitAll()
                        .requestMatchers("/ong/**").hasRole("ONG")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/dashboard/**").hasRole("USER")
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/auth/redirect", true)
                        .failureUrl("/auth/login?error=true")
                        .permitAll())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/auth/redirect"));
        ;
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * public void configure(AuthenticationManagerBuilder auth) throws Exception {
     * auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()
     * );
     * }
     */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
