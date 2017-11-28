package com.amplio.amplio.service.impl;

import com.amplio.amplio.models.Follower;
import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.User;
import com.amplio.amplio.repository.UserRepository;
import com.amplio.amplio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepository;

  public User getUser(Integer userId) {
    User user = userRepository.findUserByUserId(userId);
    return user;
  }

  public List<Playlist> getPlaylists(HttpSession session) {
    List<Playlist> userPlaylist = null;
    User currentUser = (User) session.getAttribute("user");
    if(currentUser != null) {
      List<Playlist> playlists = currentUser.getPlaylists();
      if(playlists == null) {
        userPlaylist = new ArrayList<Playlist>();
      } else {
        userPlaylist = playlists;
      }
    }
    return userPlaylist;
  }

  @Override
  public List<User> searchUser(String query) {
    List<User> users = userRepository.findTop10ByUserNameContainingIgnoreCase(query);
    return users;
  }

  @Override
  public Set<Follower> getFollowers(Integer userId) {
    Set<Follower> followers = null;
    User user = userRepository.findUserByUserId(userId);
    if(user != null) {
      followers = user.getFollowers();
    }
    return followers;
  }

  @Override
  public Set<Follower> addFollower(HttpSession session, Integer userId) {
    Set<Follower> followers = null;
    User currentUser = (User) session.getAttribute("user");
    if(currentUser != null) {
      User user = userRepository.findUserByUserId(userId);
      if(user != null) {
        followers = currentUser.follow(user);
        userRepository.save(currentUser);
      }
    }
    return followers;
  }
}
