package com.smartcareer.controller;

import com.smartcareer.domain.User;
import com.smartcareer.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    /*
        REGISTER
     */

    @PostMapping("/register")
    public String registerUser(

            @RequestBody User user
    ){

        if(
            userRepository.findByUsername(
                    user.getUsername()
            ) != null
        ){

            return "Username already exists!";
        }

        userRepository.save(user);

        return "Registration Successful";
    }

    /*
        LOGIN
     */

    @PostMapping("/login")
    public String loginUser(

            @RequestBody User user
    ){

        User existingUser =

                userRepository.findByUsername(
                        user.getUsername()
                );

        if(
            existingUser == null
        ){

            return "User not found!";
        }

        if(
            !existingUser.getPassword()
                    .equals(user.getPassword())
        ){

            return "Invalid Password!";
        }

        return "Login Successful";
    }
}