package com.amplio.amplio.service.impl;

import com.amplio.amplio.forms.LoginForm;
import com.amplio.amplio.forms.RegisterForm;
import com.amplio.amplio.models.User;
import com.amplio.amplio.repository.UserRepository;
import com.amplio.amplio.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class SessionServiceImpl implements SessionService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(RegisterForm registerForm) {
        String userName = registerForm.getUserName();
        if(userRepository.getUserByUsername(userName) != null){
            return null;
        }
        String firstName = registerForm.getFirstName();
        String lastName = registerForm.getLastName();
        String email = registerForm.getEmail();
        String password = registerForm.getPassword();
        password = passwordEncoder.encode(password);
        Boolean isPremium = false;
        User user = new User(firstName, lastName, email, userName, password, isPremium);
        System.out.println(user);
        userRepository.save(user);
        return user;
    }

  @Override
  public User loginUser(LoginForm loginForm, HttpServletRequest request, HttpSession session) {
    session.invalidate();
    HttpSession newSession = request.getSession();
    String userName = loginForm.getUsername();
    String password = loginForm.getPassword();
    User user = userRepository.getUserByUsername(userName);
    if(user == null) {
      return null;
    }
    if(user.getPassword().equals(password)) {
      newSession.setAttribute("user", user);
      return user;
    }
    return null;
  }

  @Override
  public String logoutUser(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if(auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return "redirect:/login?logout";
  }


}
