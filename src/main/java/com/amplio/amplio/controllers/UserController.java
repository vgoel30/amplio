package com.amplio.amplio.controllers;

import com.amplio.amplio.models.Follower;
import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.Song;
import com.amplio.amplio.models.User;
import com.amplio.amplio.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
  @Autowired
  private UserServiceImpl userService;

  @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
  public ResponseEntity<Boolean> deleteUser(HttpSession session) {
    Boolean deletionSuccess = false;
    HttpStatus status;

    deletionSuccess = userService.deleteUser(session);

    if(deletionSuccess) {
      status = HttpStatus.OK;
    } else {
      status = HttpStatus.BAD_REQUEST;
    }

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

  @RequestMapping(path = "/follow/{userId}", method = RequestMethod.POST)
  public ResponseEntity<Set<Follower>> follow(@PathVariable String userId, HttpSession session) {
    HttpStatus status;
    Integer followId;
    Set<Follower> following = null;

    try {
      followId = Integer.parseInt(userId);
      following = userService.follow(session, followId);
    } catch(NumberFormatException numberFormatException) {
      status = HttpStatus.BAD_REQUEST;
    }

    if(following == null) {
      status = HttpStatus.UNAUTHORIZED;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Set<Follower>>(following, status);
  }

  @RequestMapping(path = "/followers", method = RequestMethod.GET)
  public ResponseEntity<Set<Follower>> getFollowers(HttpSession session) {
    Set<Follower> followers = null;
    HttpStatus status;

    followers = userService.getFollowers(session);

    if(followers == null) {
      status = HttpStatus.NOT_FOUND;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Set<Follower>>(followers, status);
  }

  @RequestMapping(path = "/following", method = RequestMethod.GET)
  public ResponseEntity<Set<Follower>> getFollowing(HttpSession session) {
    Set<Follower> following = null;

    HttpStatus status;

    following = userService.getFollowing(session);

    if(following == null) {
      status = HttpStatus.NOT_FOUND;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Set<Follower>>(following, status);
  }

  @RequestMapping(path = "/unfollow/{followerId}", method = RequestMethod.POST)
  public ResponseEntity<Set<Follower>> unFollow(@PathVariable String followerId, HttpSession session) {
    HttpStatus status;
    Set<Follower> following = null;
    Integer followingtoRemoveId;

    try {
      followingtoRemoveId = Integer.parseInt(followerId);
      following = userService.unFollow(session, followingtoRemoveId);

      if(following == null) {
        status = HttpStatus.NOT_FOUND;
      } else {
        status = HttpStatus.OK;
      }

    } catch(NumberFormatException numberFormatException) {
      status = HttpStatus.BAD_REQUEST;
    }

    return new ResponseEntity<Set<Follower>>(following, status);
  }

  @RequestMapping(path = "/playlists", method = RequestMethod.GET)
  public ResponseEntity<Set<Playlist>> getPlaylists(HttpSession session) {
    HttpStatus status;
    Set<Playlist> userPlaylists = userService.getPlaylists(session);

    if(userPlaylists == null) {
      status = HttpStatus.UNAUTHORIZED;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Set<Playlist>>(userPlaylists, status);
  }

  @RequestMapping(path = "/followedplaylists", method = RequestMethod.GET)
  public ResponseEntity<Set<Playlist>> getFollowedPlaylists(HttpSession session) {
    HttpStatus status;
    Set<Playlist> followedPlaylists = userService.getFollowedPlaylists(session);

    if(followedPlaylists == null) {
      status = HttpStatus.UNAUTHORIZED;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Set<Playlist>>(followedPlaylists, status);
  }

  @RequestMapping(path = "/queue/add", method = RequestMethod.POST)
  public ResponseEntity<Boolean> addSongToQueue(Song songToAdd, HttpSession session){
    HttpStatus status;
    Boolean songAdded = userService.addSongToQueue(songToAdd, session);

    if(songAdded == false){
      status = HttpStatus.BAD_REQUEST;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Boolean>(songAdded, status);
  }

  @RequestMapping(path = "/queue/delete", method = RequestMethod.DELETE)
  public ResponseEntity<Boolean> deleteSongFromQueue(Song songToDelete, HttpSession session){
    HttpStatus status;
    Boolean songDeleted = userService.deleteSongFromQueue(songToDelete, session);

    if(songDeleted == false){
      status = HttpStatus.BAD_REQUEST;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Boolean>(songDeleted, status);
  }
}
