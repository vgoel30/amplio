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
  Set<Playlist> getFollowedPlaylists(HttpSession session);
  List<User> searchUser(String query);
  Set<Follower> follow(HttpSession session, Integer userId);
  Follower unFollow(Integer followerId, HttpSession session);
  Set<Follower> getFollowers(HttpSession session);
  Set<Follower> getFollowing(HttpSession session);
  Boolean deleteUser(HttpSession session);
}
