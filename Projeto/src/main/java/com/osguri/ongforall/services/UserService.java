package com.osguri.ongforall.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.osguri.ongforall.entities.User;
import com.osguri.ongforall.entities.dtos.RegisterUserDTO;
import com.osguri.ongforall.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User save(RegisterUserDTO userDTO) throws Exception{
        User user = userRepository.findByEmail(userDTO.email());
        if(user != null){
            throw new Exception("User already exists");
        }
        System.out.println("passei no serviço 2");

        user = new User();
        user.setEmail(userDTO.email());
        user.setName(userDTO.name());
        String passwordEncoded = passwordEncoder.encode(userDTO.password());
        user.setPassword(passwordEncoded);

        return userRepository.save(user);
    }

}
