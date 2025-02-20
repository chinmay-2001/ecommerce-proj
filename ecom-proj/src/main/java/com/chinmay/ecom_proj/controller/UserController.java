package com.chinmay.ecom_proj.controller;

import com.chinmay.ecom_proj.model.Users;
import com.chinmay.ecom_proj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> requestBody ){
        String email = requestBody.get("email");
        String password = requestBody.get("password");
        try {
            Users user=userService.verifyUser(email, password);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/signUp")
    public ResponseEntity<?> signup(@RequestBody Users users) {
        try {
            Users user = userService.createUser(users);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
