package com.announcementdesk.controlers;

import com.announcementdesk.domain.User;
import com.announcementdesk.filters.jwt.JwtManager;
import com.announcementdesk.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@Controller
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;
    private JwtManager jwtManager;

    public AuthController(UserService userService, JwtManager jwtManager){

        this.userService = userService;
        this.jwtManager = jwtManager;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String enterSystem( @RequestParam("username") String name,
                                @RequestParam("password") String password){
        User userFromDB = userService.findByName(name);
        if(userFromDB != null && userFromDB.getPassword().equals(password) ){
            String token = jwtManager.generateToken(name);
            return token;
        }
        return "login";
    }



    @GetMapping("/registration")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult,
                          Model model){
        if(bindingResult.hasErrors()){
            return "register";
        }
        if (userService.addUser(user)) {
            return "redirect:login";
        }
        model.addAttribute("message", "User exists!");
        return "register";
    }



}
