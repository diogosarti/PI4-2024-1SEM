package com.osguri.ongforall.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.osguri.ongforall.entities.User;
import com.osguri.ongforall.entities.dtos.UserDTO;
import com.osguri.ongforall.services.UserService;

@Controller
@RequestMapping(value = "/")
public class HomeResource {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getHome(ModelMap model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.findByEmail(authentication.getName());
            UserDTO userDTO = new UserDTO(user.getName(), user.getEmail());
            model.addAttribute("user", userDTO);
        }
        return "index";
    }

}
