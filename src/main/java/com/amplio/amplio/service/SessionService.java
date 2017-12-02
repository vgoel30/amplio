package com.amplio.amplio.service;

import com.amplio.amplio.forms.LoginForm;
import com.amplio.amplio.forms.RegisterForm;
import com.amplio.amplio.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SessionService {
  User registerUser(RegisterForm registerForm);

  User loginUser(LoginForm loginForm, HttpServletRequest request);

  String logoutUser(HttpServletRequest request, HttpServletResponse response);
}
