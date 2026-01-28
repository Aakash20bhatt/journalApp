package com.aakiproject.journalApp.controller;

import com.aakiproject.journalApp.cache.AppCache;
import com.aakiproject.journalApp.entity.User;
import com.aakiproject.journalApp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserServices userServices;

    @Autowired
    private AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUser(){
        List<User> all = userServices.getAll();
        if(all!=null && !all.isEmpty()){
            return  new ResponseEntity<>(all, HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody User user){
        userServices.saveAdmin(user);
    }

    @PutMapping("/update-role/{username}")
    public ResponseEntity<String> promoteUser(@PathVariable String username) {
        userServices.promoteToAdmin(username);

        return ResponseEntity.ok("Successfully promoted to ADMIN");
    }

    @GetMapping("clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }
}
