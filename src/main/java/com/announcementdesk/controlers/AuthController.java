package com.announcementdesk.controlers;

import com.announcementdesk.domain.Role;
import com.announcementdesk.domain.User;
import com.announcementdesk.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Map;


@Controller
@RequestMapping("/auth")
public class AuthController {

    private UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository){
        this.userRepository = userRepository;
    }



    @GetMapping("/registration")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") User user, Model model){

        User userFromDb = userRepository.findByName(user.getName());

        if (userFromDb != null) {
            model.addAttribute("message", "User exists!");
            return "register";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:login";
    }



}
