package com.amplio.amplio.controllers;

import com.amplio.amplio.models.Follower;
import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.User;
import com.amplio.amplio.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
  @Autowired
  private UserServiceImpl userService;

  @RequestMapping(path = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<User> getUser(@PathVariable String id){
    User user = null;
    HttpStatus status;
    Integer userId;

    try {
      userId = Integer.parseInt(id);
    } catch(NumberFormatException numberFormatException) {
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<User>(user, status);
    }

    user = userService.getUser(userId);

    if(user == null) {
      status = HttpStatus.NOT_FOUND;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<User>(user, status);
  }

  @RequestMapping(path = "/search/{query}", method = RequestMethod.GET)
  public ResponseEntity<List<User>> searchUser(@PathVariable String query){
    List<User> users = null;
    HttpStatus status;

    users = userService.searchUser(query);

    if(users == null){
      status = HttpStatus.BAD_REQUEST;
    }
    else{
      status = HttpStatus.OK;
    }

    return new ResponseEntity<List<User>>(users, status);
  }

  @RequestMapping(path = "/followers/{id}", method = RequestMethod.GET)
  public ResponseEntity<Set<User>> getFollowers(@PathVariable String id){
    Set<User> followers = null;
    HttpStatus status;
    Integer userId;

    try {
      userId = Integer.parseInt(id);
    } catch(NumberFormatException numberFormatException) {
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Set<User>>(followers, status);
    }

    followers = userService.getFollowers(userId);

    if(followers == null) {
      status = HttpStatus.NOT_FOUND;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Set<User>>(followers, status);
  }

  @RequestMapping(path = "/playlists", method = RequestMethod.GET)
  public ResponseEntity<List<Playlist>> getPlaylist(HttpSession session){
    HttpStatus status;
    List<Playlist> userPlaylists = userService.getPlaylists(session);

    if(userPlaylists == null){
      status = HttpStatus.UNAUTHORIZED;
    }
    else{
      status = HttpStatus.OK;
    }

    return new ResponseEntity<List<Playlist>>(userPlaylists, status);
  }
}
