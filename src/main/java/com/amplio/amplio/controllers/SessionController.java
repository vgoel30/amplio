package com.amplio.amplio.controllers;

import com.amplio.amplio.forms.LoginForm;
import com.amplio.amplio.forms.RegisterForm;
import com.amplio.amplio.models.Admin;
import com.amplio.amplio.models.User;
import com.amplio.amplio.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/api/session")
public class SessionController {
  @Autowired
  private SessionService sessionService;

  @RequestMapping(path = "/register", method = RequestMethod.POST)
  public ResponseEntity<User> register(@RequestBody RegisterForm registerForm) {
    HttpStatus status;
    User user = sessionService.registerUser(registerForm);
    if(user == null) {
      status = HttpStatus.CONFLICT;
    } else {
      status = HttpStatus.CREATED;
    }
    return new ResponseEntity<>(user, status);
  }

  @RequestMapping(path = "/login", method = RequestMethod.POST)
  public ResponseEntity<User> login(@RequestBody LoginForm loginForm, HttpServletRequest request, HttpSession session) {
    session.invalidate();
    HttpStatus status;
    User user = sessionService.loginUser(loginForm, request);
    if(user == null) {
      status = HttpStatus.FORBIDDEN;
    } else {
      status = HttpStatus.CREATED;
    }
    return new ResponseEntity<User>(user, status);
  }

  @RequestMapping(path = "/logout", method = RequestMethod.POST)
  public String logout(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession();
    return sessionService.logoutUser(request, response);
  }

  @RequestMapping(path = "/admin/login", method = RequestMethod.POST)
  public ResponseEntity<Admin> adminLogin(@RequestBody LoginForm loginForm, HttpServletRequest request,
                                          HttpSession session) {
    session.invalidate();
    HttpStatus status;
    Admin admin = sessionService.loginAdmin(loginForm, request);
    if(admin == null) {
      status = HttpStatus.FORBIDDEN;
    } else {
      status = HttpStatus.CREATED;
    }
    return new ResponseEntity<Admin>(admin, status);
  }

  @RequestMapping(path = "/admin/register", method = RequestMethod.POST)
  public ResponseEntity<Admin> adminRegister(@RequestBody RegisterForm registerForm) {
    HttpStatus status;
    Admin admin = sessionService.registerAdmin(registerForm);
    if(admin == null) {
      status = HttpStatus.CONFLICT;
    } else {
      status = HttpStatus.CREATED;
    }
    return new ResponseEntity<Admin>(admin, status);
  }
}
