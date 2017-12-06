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
import java.util.HashSet;
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

  public Set<Playlist> getPlaylists(HttpSession session) {
    Set<Playlist> userPlaylist = null;
    User currentUser = (User) session.getAttribute("user");
    if(currentUser != null) {
      Set<Playlist> playlists = currentUser.getPlaylists();
      if(playlists == null) {
        userPlaylist = new HashSet<Playlist>();
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
  public Set<Follower> getFollowers(HttpSession session) {
    Set<Follower> followers = null;
    User user = (User)session.getAttribute("user");
    if(user != null) {
      followers = user.getFollowers();
    }
    return followers;
  }

  @Override
  public Follower deleteFollower(Integer followerId, HttpSession session){
    Follower followerToRemove = null;
    User user = (User)session.getAttribute("user");
    if(user != null){
      Set<Follower> followersSet = user.getFollowers();
      for(Follower follower : followersSet){
        if(follower.getUserId().equals(followerId)){
          followerToRemove = follower;
          break;
        }
      }
      if(followerToRemove != null){
        followersSet.remove(followerToRemove);
      }
    }
    return followerToRemove;
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

      //3: Delete the playlists for this user.
      Set<Playlist> userPlaylists = currentUser.getPlaylists();
      for(Playlist userPlaylist : userPlaylists){
        playlistRepository.delete(userPlaylist);
      }

      userRepository.delete(currentUser);
      deletionSuccess = true;
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
