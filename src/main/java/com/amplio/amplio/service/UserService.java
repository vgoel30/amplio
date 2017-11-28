package com.amplio.amplio.service;

import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;


public interface UserService {
  User getUser(Integer userId);
  List<Playlist> getPlaylists(HttpSession session);
  List<User> searchUser(String query);
  Set<User> getFollowers(Integer userId);
}
