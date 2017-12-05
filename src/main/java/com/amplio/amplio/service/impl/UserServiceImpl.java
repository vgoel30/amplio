package com.amplio.amplio.service.impl;

import com.amplio.amplio.models.Follower;
import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.User;
import com.amplio.amplio.repository.PlaylistRepository;
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

  @Autowired
  private PlaylistRepository playlistRepository;

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
  public Boolean deleteUser(HttpSession session) {
    Boolean deletionSuccess = false;
    User currentUser = (User) session.getAttribute("user");
    if(currentUser != null){
      Integer userToDeleteId = currentUser.getUserId();

      //1. delete this user from the following list of their followers
      for(Follower follower : currentUser.getFollowers()){
        Integer followerId = follower.getUserId();
        User followerAsUser = userRepository.findUserByUserId(followerId);
        Set<Follower> followingSet = followerAsUser.getFollowing();
        for(Follower following: followingSet){
          if(following.getUserId().equals(userToDeleteId)){
            followingSet.remove(following);
            break;
          }
        }
      }

      //2. delete this user from the followers list of the people that they are following
      for(Follower following : currentUser.getFollowing()){
        Integer followingId = following.getUserId();
        User followingAsUser = userRepository.findUserByUserId(followingId);
        Set<Follower> followerSet = followingAsUser.getFollowers();
        for(Follower follower : followerSet){
          if(follower.getUserId().equals(userToDeleteId)){
            followerSet.remove(follower);
            break;
          }
        }
      }

      userRepository.delete(currentUser);
      deletionSuccess = true;

      //TODO: Delete the playlists for this user.
    }
    return deletionSuccess;
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
