package com.aakiproject.journalApp.controller;

import com.aakiproject.journalApp.entity.User;
import com.aakiproject.journalApp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserServices userServices;

    @PostMapping("/create_user")
    public void createUser(@RequestBody User user) {
        userServices.saveNewUser(user);
    }

    @GetMapping("/health-check")
    public String healthCheck(){
        return "ok";
    }
}

