package com.aakiproject.journalApp.services;

import com.aakiproject.journalApp.entity.User;
import com.aakiproject.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class UserServices {

    private final static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    public boolean saveNewUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            log.info("Saving user to Mongo: {}", user);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public void updateUser(User user) {
        if (user.getPassword() != null && !user.getPassword().startsWith("$2")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void saveAdmin(User user) {
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }


    public User findByUserName (String userName) {
        return userRepository.findByUserName(userName);
    }

    public void promoteToAdmin(String userName) {
        User user = userRepository.findByUserName(userName);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        List<String> roles = user.getRoles();
        if (roles == null) {
            roles = new ArrayList<>();
        }

        if (!roles.contains("ADMIN")) {
            roles.add("ADMIN");
        }

        user.setRoles(roles);
        userRepository.save(user);

    }
}
