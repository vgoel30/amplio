package com.amplio.amplio.service;

import com.amplio.amplio.models.Follower;
import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.User;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;


public interface UserService {
  User getUser(Integer userId);

  Set<Playlist> getPlaylists(HttpSession session);
  List<User> searchUser(String query);
  Set<Follower> addFollower(HttpSession session, Integer userId);
  Set<Follower> getFollowers(HttpSession session);
  Follower deleteFollower(Integer followerId, HttpSession session);
  Boolean deleteUser(HttpSession session);
}
