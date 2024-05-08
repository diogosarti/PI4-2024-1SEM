package com.osguri.ongforall.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.osguri.ongforall.entities.User;
import com.osguri.ongforall.entities.dtos.LoginUserDTO;
import com.osguri.ongforall.entities.dtos.RegisterUserDTO;
import com.osguri.ongforall.services.CustomUserDetailsService;
import com.osguri.ongforall.services.UserService;

@Controller
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

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
            userDTO.setRole("USER");
            userService.save(userDTO);
            User user = userService.findByEmail(userDTO.getEmail());

            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(user.getEmail(), userDTO.getPassword());
            Authentication auth = authenticationManager.authenticate(authReq);
            SecurityContextHolder.getContext().setAuthentication(auth);

            return "redirect:auth/redirect";
        } catch (Exception exception) {
            System.out.println(exception);
            return "redirect:auth/register?error=true";
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
