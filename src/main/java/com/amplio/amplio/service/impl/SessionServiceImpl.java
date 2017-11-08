package com.amplio.amplio.service.impl;

import com.amplio.amplio.forms.LoginForm;
import com.amplio.amplio.forms.RegisterForm;
import com.amplio.amplio.models.User;
import com.amplio.amplio.repository.UserRepository;
import com.amplio.amplio.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService{
    @Autowired
    private UserRepository userRepository;


    @Override
    public User registerUser(RegisterForm registerForm) {
        String username = registerForm.getUserName();
        if(userRepository.getUserByUsername(username) != null){
            return null;
        }
        String firstName = registerForm.getFirstName();
        String lastName = registerForm.getLastName();
        String email = registerForm.getEmail();
        String password = registerForm.getPassword();
        Boolean isPremium = false;
        User user = new User(firstName, lastName, email, username, password, isPremium);
        userRepository.save(user);
        return user;
    }

    @Override
    public User loginUser(LoginForm loginForm){
        String userName = loginForm.getUserName();
        String password = loginForm.getPassword();
        User user = userRepository.getUserByUsername(userName);
        if (user == null){
            return null;
        }
        if(user.getPassword().equals(password)){
            return user;
        }
        return null;
    }


}
