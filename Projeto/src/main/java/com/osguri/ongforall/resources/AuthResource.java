package com.osguri.ongforall.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.osguri.ongforall.entities.User;
import com.osguri.ongforall.entities.dtos.LoginUserDTO;
import com.osguri.ongforall.entities.dtos.RegisterUserDTO;
import com.osguri.ongforall.services.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private UserService userService;
    
    @GetMapping(value = "/login")
    public String loginPahe(Model model) {
        model.addAttribute("user", LoginUserDTO.class);
        return "auth/login";
    }

    @GetMapping(value = "/register")
    public String getMethodName(Model model) {
        model.addAttribute("user", RegisterUserDTO.class);
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegisterUserDTO userDTO) throws Exception {
        try{
            System.out.println("passwei no try");
            userService.save(userDTO);
            return "auth/login";
        } catch(Exception exception){
            return "auth/register?error=true";
        }
    }
    
    
}
