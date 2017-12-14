package com.amplio.amplio.service;

import com.amplio.amplio.forms.LoginForm;
import com.amplio.amplio.forms.RegisterForm;
import com.amplio.amplio.models.Admin;
import com.amplio.amplio.models.Follower;
import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.User;
import com.amplio.amplio.repository.AdminRepository;
import com.amplio.amplio.repository.FollowerRepository;
import com.amplio.amplio.repository.PlaylistRepository;
import com.amplio.amplio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

import static com.amplio.amplio.constants.Constants.SESSION_USER;

@Service
public class SessionService {
  @Autowired
  PlaylistRepository playlistRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private FollowerRepository followerRepository;
  @Autowired
  private AdminRepository adminRepository;

  public User registerUser(RegisterForm registerForm) {
    String userName = registerForm.getUserName();
    User existingUser = userRepository.findByUserName(userName);
    User user = null;

    if(existingUser == null) {
      String firstName = registerForm.getFirstName();
      String lastName = registerForm.getLastName();
      String email = registerForm.getEmail();
      String password = registerForm.getPassword();
      password = passwordEncoder.encode(password);
      Boolean isPremium = false;
      user = new User(firstName, lastName, email, userName, password, isPremium);

      userRepository.save(user);

      Follower userAsFollower = new Follower(user);
      followerRepository.save(userAsFollower);
    }
    return user;
  }


  public User loginUser(LoginForm loginForm, HttpServletRequest request) {
    HttpSession session = request.getSession();
    String userName = loginForm.getUserName();
    String password = loginForm.getPassword();
    User user = userRepository.findByUserName(userName);
    if(user != null) {
      if(passwordEncoder.matches(password, user.getPassword())) {
        Set<Playlist> playlists = playlistRepository.findPlaylistsByOwner(user);
        if(playlists == null) {
          playlists = new HashSet<Playlist>();
        }
        for(Playlist playlist : playlists) {
          playlist.setOwner(null);
        }
        user.setPlaylists(playlists);
        session.setAttribute(SESSION_USER, user);
      } else {
        user = null;
      }
    }
    return user;
  }


  public String logoutUser(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if(auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return "redirect:/login?logout";
  }

  public Admin loginAdmin(LoginForm loginForm, HttpServletRequest request) {
    HttpSession session = request.getSession();
    String username = loginForm.getUserName();
    String password = loginForm.getPassword();
    Admin admin = adminRepository.findByUsername(username);

    if(admin != null) {
      if(passwordEncoder.matches(password, admin.getPassword())) {
        session.setAttribute("admin", admin);
      } else {
        admin = null;
      }
    }
    return admin;
  }

  public Admin registerAdmin(RegisterForm registerForm) {
    String username = registerForm.getUserName();
    Admin existingUser = adminRepository.findByUsername(username);
    Admin admin = null;

    if(existingUser == null) {
      String firstName = registerForm.getFirstName();
      String lastName = registerForm.getLastName();
      String email = registerForm.getEmail();
      String password = registerForm.getPassword();
      password = passwordEncoder.encode(password);
      admin = new Admin(firstName, lastName, email, username, password);

      adminRepository.save(admin);
    }
    return admin;
  }
}
