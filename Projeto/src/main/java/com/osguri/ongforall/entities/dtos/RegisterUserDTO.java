package com.osguri.ongforall.entities.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterUserDTO {
    private String name;
    private String email;
    private String password;
    private String role;
}
