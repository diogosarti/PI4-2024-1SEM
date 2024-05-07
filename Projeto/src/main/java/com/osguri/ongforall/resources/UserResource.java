package com.osguri.ongforall.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.osguri.ongforall.entities.User;
import com.osguri.ongforall.entities.UserDTO;
import com.osguri.ongforall.services.UserService;


@Controller
@RequestMapping(path = "/dashboard")
public class UserResource {

    @Autowired
    private UserService userService;
    
    @GetMapping()
    public String returnUserDashboard(Model model, Authentication authentication) {
        User user =  userService.findByEmail(authentication.getName());
        UserDTO userDTO = new UserDTO(user.getName(), user.getEmail());
        model.addAttribute("user", userDTO);
        
        return "userDashboard/index";
    }
    

}
