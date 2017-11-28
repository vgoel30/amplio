package com.amplio.amplio.service.impl;

import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.User;
import com.amplio.amplio.service.UserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
  public List<Playlist> getPlaylists(HttpSession session){
    List<Playlist> userPlaylist = null;
    User currentUser = (User) session.getAttribute("user");
    if(currentUser != null) {
      List<Playlist> playlists = currentUser.getPlaylists();
      if(playlists == null){
        userPlaylist = new ArrayList<Playlist>();
      }
      else{
        userPlaylist = playlists;
      }
    }
    return userPlaylist;
  }
}
