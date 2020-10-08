package com.infotech.userregloginapp.controller;

import com.infotech.userregloginapp.model.User;
import com.infotech.userregloginapp.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = {"/", "/index", "index.html"})
    public String homePage(){
        return "index";
    }

    @GetMapping(value = "/register")
    public String registerPage(Model model){
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping(value = "/process_register")
    public String processRegistration(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
        return "register_success";
    }

    @GetMapping(value = "/list_users")
    public String viewUsersList(Model model){
        List<User> listOfUsers = userRepository.findAll();
        model.addAttribute("listOfUsers", listOfUsers);
        return "users";
    }
}