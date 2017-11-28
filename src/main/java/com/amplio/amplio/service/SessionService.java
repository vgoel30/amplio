package com.amplio.amplio.service;

import com.amplio.amplio.forms.LoginForm;
import com.amplio.amplio.forms.RegisterForm;
import com.amplio.amplio.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface SessionService {
    public User registerUser(RegisterForm registerForm);
    public User loginUser(LoginForm loginForm, HttpServletRequest request, HttpSession session);
    public String logoutUser(HttpServletRequest request, HttpServletResponse response);
}
