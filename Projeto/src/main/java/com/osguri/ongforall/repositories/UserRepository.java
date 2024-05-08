package com.osguri.ongforall.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.osguri.ongforall.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
}
