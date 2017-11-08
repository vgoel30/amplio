package com.amplio.amplio.service.impl;

import com.amplio.amplio.forms.LoginForm;
import com.amplio.amplio.models.User;
import com.amplio.amplio.repository.SessionRepository;
import com.amplio.amplio.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService{
    @Autowired
    private SessionRepository sessionRepository;


    @Override
    public User loginUser(LoginForm loginForm) {
        return null;
    }
}
