package com.amplio.amplio.service;

import com.amplio.amplio.forms.LoginForm;
import com.amplio.amplio.forms.RegisterForm;
import com.amplio.amplio.models.User;

public interface SessionService {
    public User registerUser(RegisterForm registerForm);
    public User loginUser(LoginForm loginForm);
}
