package com.aakiproject.journalApp.controller;

import com.aakiproject.journalApp.entity.User;
import com.aakiproject.journalApp.services.UserServices;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserServices userServices;

    @PostMapping("/create_user")
    public boolean createUser(@Valid @RequestBody User user) {
        log.info("createUser {}", user);
        return userServices.saveNewUser(user);
    }

    @GetMapping("/health-check")
    public String healthCheck(){
        return "ok";
    }
}

