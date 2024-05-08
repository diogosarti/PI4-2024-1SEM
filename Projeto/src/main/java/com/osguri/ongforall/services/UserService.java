package com.osguri.ongforall.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.osguri.ongforall.entities.User;
import com.osguri.ongforall.entities.dtos.RegisterUserDTO;
import com.osguri.ongforall.repositories.RoleRepository;
import com.osguri.ongforall.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User save(RegisterUserDTO userDTO) throws Exception{
        User user = userRepository.findByEmail(userDTO.getEmail());
        if(user != null){
            throw new Exception("User already exists");
        }

        user = new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        String passwordEncoded = passwordEncoder.encode(userDTO.getPassword());
        user.getRoles().add(roleRepository.findByName(userDTO.getRole()));
        user.setPassword(passwordEncoded);

        return userRepository.save(user);
    }

    @Transactional
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

}
