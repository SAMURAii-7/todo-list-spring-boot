package com.shubham.todolist.controller;

import com.shubham.todolist.model.User;
import com.shubham.todolist.payload.response.LoginResponse;
import com.shubham.todolist.payload.response.StandardResponse;
import com.shubham.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<StandardResponse> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<LoginResponse> loginUser(@RequestBody User user) {
        return userService.loginUser(user);
    }

    @PostMapping("/verify")
    @ResponseBody
    public ResponseEntity<StandardResponse> verifyToken(@RequestHeader("Authorization") String token) {
        return userService.verifyToken(token.substring(7));
    }
}
