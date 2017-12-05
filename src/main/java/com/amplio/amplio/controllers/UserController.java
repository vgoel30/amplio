package com.amplio.amplio.controllers;

import com.amplio.amplio.models.Follower;
import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.User;
import com.amplio.amplio.service.impl.UserServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
  @Autowired
  private UserServiceImpl userService;

  @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
  public ResponseEntity<Boolean> deleteUser(HttpSession session){
    Boolean deletionSuccess = false;
    HttpStatus status = null;

    return new ResponseEntity<Boolean>(deletionSuccess, status);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<User> getUser(@PathVariable String id) {
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
  public ResponseEntity<List<User>> searchUser(@PathVariable String query) {
    List<User> users = null;
    HttpStatus status;

    users = userService.searchUser(query);

    if(users == null) {
      status = HttpStatus.BAD_REQUEST;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<List<User>>(users, status);
  }

  @RequestMapping(path = "/followers/{id}", method = RequestMethod.GET)
  public ResponseEntity<Set<Follower>> getFollowers(@PathVariable String id) {
    Set<Follower> followers = null;
    HttpStatus status;
    Integer userId;

    try {
      userId = Integer.parseInt(id);
    } catch(NumberFormatException numberFormatException) {
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Set<Follower>>(followers, status);
    }

    followers = userService.getFollowers(userId);

    if(followers == null) {
      status = HttpStatus.NOT_FOUND;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Set<Follower>>(followers, status);
  }

  @RequestMapping(path = "/follow", method = RequestMethod.POST)
  public ResponseEntity<Set<Follower>> addFollowers(@RequestBody User user, HttpSession session) {
    HttpStatus status;
    Set<Follower> following = userService.addFollower(session, user.getUserId());

    if(following == null) {
      status = HttpStatus.UNAUTHORIZED;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Set<Follower>>(following, status);
  }

  @RequestMapping(path = "/playlists", method = RequestMethod.GET)
  public ResponseEntity<List<Playlist>> getPlaylist(HttpSession session) {
    HttpStatus status;
    List<Playlist> userPlaylists = userService.getPlaylists(session);

    if(userPlaylists == null) {
      status = HttpStatus.UNAUTHORIZED;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<List<Playlist>>(userPlaylists, status);
  }
}
