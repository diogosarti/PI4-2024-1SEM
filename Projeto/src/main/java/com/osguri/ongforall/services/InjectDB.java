package com.osguri.ongforall.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.osguri.ongforall.entities.User;
import com.osguri.ongforall.repositories.UserRepository;

@Service
public class InjectDB {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void injectDB(){
        User user1 = new User(null, "Diogo", "diogo@gmail.com", passwordEncoder.encode("12345678"));
        userRepository.saveAll(Arrays.asList(user1));
    }
    
}
