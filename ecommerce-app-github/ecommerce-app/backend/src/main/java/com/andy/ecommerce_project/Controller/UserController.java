package com.andy.ecommerce_project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andy.ecommerce_project.Model.Users;
import com.andy.ecommerce_project.Service.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService service;
    public record RegisterRequest(String username, String password) {} //holds data

   @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request){

           Users user = new Users();
           user.setUsername(request.username());
           user.setPassword(request.password());
           service.register(user);

           return ResponseEntity.status(HttpStatus.CREATED).build();

    }

}
