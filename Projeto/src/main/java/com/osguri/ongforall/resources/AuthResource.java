package com.osguri.ongforall.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
    public String loginPahe(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:redirect";
        }
        model.addAttribute("user", LoginUserDTO.class);
        return "auth/login";
    }

    @GetMapping(value = "/register")
    public String getMethodName(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:redirect";
        }
        model.addAttribute("user", RegisterUserDTO.class);
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegisterUserDTO userDTO, Authentication authentication)
            throws Exception {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:redirect";
        }
        try {
            System.out.println("passwei no try");
            userService.save(userDTO);
            return "auth/login";
        } catch (Exception exception) {
            return "auth/register?error=true";
        }
    }

    @GetMapping("/redirect")
    public String rediretToDashboar(Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        for (GrantedAuthority authority : user.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                return "redirect:/admin/dashboard";
            } else if (authority.getAuthority().equals("ROLE_USER")) {
                return "redirect:/dashboard";
            } else if (authority.getAuthority().equals("ROLE_ONG")) {
                return "redirect:/ong/dashboard";
            }
        }
        return "redirect:/auth/login";
    }

}
