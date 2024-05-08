package com.osguri.ongforall.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.osguri.ongforall.entities.User;
import com.osguri.ongforall.entities.dtos.UserDTO;
import com.osguri.ongforall.services.UserService;
import org.springframework.web.bind.annotation.RequestParam;

/* ATENÇÃO */
/* 
 * DASHBOARD DO USUÁRIO É UMA SPA (SINGLE PAGE APPLICATION)
 * DEVEM SER PASSADOS APENAS O CONTEUDO DA PÁGINA, SEM O HEADER E O FOOTER
 * 
 */

@Controller
@RequestMapping(path = "/dashboard")
public class UserResource {

    @Autowired
    private UserService userService;
    
    @GetMapping()
    public String returnUserDashboard(Model model, Authentication authentication) {
        PassUserToModel(model, authentication);
        
        return "userDashboard/index";
    }


    /* APENAS PARTIALS */

    @GetMapping("/ongs")
    public String listOngs(Model model, Authentication authentication) {
        PassUserToModel(model, authentication);
        return "userDashboard/partials/InitialONGs";
    }
    
    
    @GetMapping(path = "/minhas-ongs")
    public String listMyOngs(Model model, Authentication authentication) {
        PassUserToModel(model, authentication);
        return "userDashboard/partials/myOngs";
    }
    
    public void PassUserToModel(Model model, Authentication authentication){
        User user =  userService.findByEmail(authentication.getName());
        UserDTO userDTO = new UserDTO(user.getName(), user.getEmail());
        model.addAttribute("user", userDTO);
    }

}
