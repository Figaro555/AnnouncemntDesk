package com.announcementdesk.controlers;

import com.announcementdesk.domain.Role;
import com.announcementdesk.domain.User;
import com.announcementdesk.repositories.UserRepository;
import com.announcementdesk.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;



@Controller
@RequestMapping("/auth")
public class AuthController {


    private UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
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
