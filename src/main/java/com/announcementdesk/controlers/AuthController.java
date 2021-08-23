package com.announcementdesk.controlers;

import com.announcementdesk.domain.User;
import com.announcementdesk.filters.jwt.JwtManager;
import com.announcementdesk.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtManager jwtManager;

    public AuthController(UserService userService, JwtManager jwtManager) {

        this.userService = userService;
        this.jwtManager = jwtManager;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String enterSystem(@RequestBody AuthRequestDTO authRequestDTO) {
        User userFromDB = userService.findByName(authRequestDTO.getUsername());
        System.out.println(userFromDB);

        if (userFromDB != null && userFromDB.getPassword().equals(authRequestDTO.getPassword())) {
            String token = jwtManager.generateToken(authRequestDTO.getUsername());
            return token;
        }
        return "login";
    }


    @GetMapping("/registration")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (userService.addUser(user)) {
            return "redirect:login";
        }
        model.addAttribute("message", "User exists!");
        return "register";
    }


}
