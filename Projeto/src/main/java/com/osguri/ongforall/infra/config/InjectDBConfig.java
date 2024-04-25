package com.osguri.ongforall.infra.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.osguri.ongforall.entities.User;
import com.osguri.ongforall.repositories.UserRepository;

@Configuration
public class InjectDBConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User(null, "diogo", "diogo@diogo.com", passwordEncoder.encode("12345678"));
        userRepository.saveAll(Arrays.asList(user1));
    }

   
    
}
