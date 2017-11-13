package com.amplio.amplio.controllers;

import com.amplio.amplio.forms.LoginForm;
import com.amplio.amplio.forms.RegisterForm;
import com.amplio.amplio.models.User;
import com.amplio.amplio.service.impl.SessionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/session")
public class SessionController {
  @Autowired
  private SessionServiceImpl sessionService;

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<User> register(@RequestBody RegisterForm registerForm) {
        HttpStatus status;
        User user = sessionService.registerUser(registerForm);
        if(user == null) {
            status = HttpStatus.FORBIDDEN;
        } else {
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(user, status);
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody LoginForm loginForm) {
        HttpStatus status;
        User user = sessionService.loginUser(loginForm);
        if(user == null) {
            status = HttpStatus.FORBIDDEN;
        } else {
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<User>(user, status);
    }
}
