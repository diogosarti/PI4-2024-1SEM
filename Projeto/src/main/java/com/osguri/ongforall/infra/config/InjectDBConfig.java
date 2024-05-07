package com.osguri.ongforall.infra.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.osguri.ongforall.entities.Role;
import com.osguri.ongforall.entities.User;
import com.osguri.ongforall.repositories.RoleRepository;
import com.osguri.ongforall.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Configuration
public class InjectDBConfig implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        User usuarioPadrao = new User("diogo", "user@teste.com", passwordEncoder.encode("12345678"));
        User administrador = new User("admin", "admin@teste.com", passwordEncoder.encode("12345678"));
        User usuarioong = new User("ong", "ong@teste.com", passwordEncoder.encode("12345678"));
        Role adminRole = new Role("ADMIN");
        Role userRole = new Role("USER");
        Role ongRole = new Role("ONG");
        roleRepository.saveAll(Arrays.asList(adminRole, userRole, ongRole));
        usuarioPadrao.getRoles().addAll(Arrays.asList(roleRepository.findByName("USER")));
        administrador.getRoles().addAll(Arrays.asList(roleRepository.findByName("ADMIN")));
        usuarioong.getRoles().addAll(Arrays.asList(roleRepository.findByName("ONG")));

        userRepository.saveAll(Arrays.asList(usuarioPadrao, administrador, usuarioong));
    }

   
    
}
