package com.shubham.todolist.service;

import com.shubham.todolist.dao.UserDao;
import com.shubham.todolist.model.User;
import com.shubham.todolist.payload.response.LoginResponse;
import com.shubham.todolist.payload.response.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    public ResponseEntity<StandardResponse> registerUser(User user) {
        User newUser = new User();
        UUID uuid = UUID.randomUUID();
        newUser.setUserId(uuid);
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(newUser);
        try {
            return new ResponseEntity<>(new StandardResponse(true, "User registered successfully!"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new StandardResponse(false, "Error registering user!"), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<LoginResponse> loginUser(User user) {
        User newUser = userDao.findByEmail(user.getEmail());
        if(newUser == null) {
            return ResponseEntity.badRequest().body(new LoginResponse(false, "User not found!", null, null));
        }
        if(!passwordEncoder.matches(user.getPassword(), newUser.getPassword())) {
            return new ResponseEntity<>(new LoginResponse(false, "Incorrect Password", null, null),
                    HttpStatus.BAD_REQUEST);
        }
        try {

            Authentication authenticate =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),
                    user.getPassword()));
            if(authenticate.isAuthenticated()) {
                return new ResponseEntity<>(new LoginResponse(true, "User logged in successfully!",
                        jwtService.generateToken(newUser.getEmail()), newUser),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new LoginResponse(false, "Error logging in!", null, null),
                        HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new LoginResponse(false, "Error logging in!", null, null), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<StandardResponse> verifyToken(String token) {
        if(!jwtService.isTokenExpired(token)) {
            return new ResponseEntity<>(new StandardResponse(true, "Valid Token"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new StandardResponse(false, "Invalid token!"), HttpStatus.BAD_REQUEST);
        }
    }
}
