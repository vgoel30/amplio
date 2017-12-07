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
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PlaylistRepository playlistRepository;

  @Override
  public User getUser(Integer userId) {
    User user = userRepository.findUserByUserId(userId);
    return user;
  }

  @Override
  public Set<Playlist> getPlaylists(HttpSession session) {
    User currentUser = (User) session.getAttribute("user");
    if(currentUser == null) {
      return null;
    }
    return playlistRepository.getPlaylistsByOwner(currentUser);
  }

  @Override
  public Set<Playlist> getFollowedPlaylists(HttpSession session) {
    User currentUser = (User) session.getAttribute("user");
    if(currentUser == null) {
      return null;
    }
    return currentUser.getFollowedPlaylists();
  }

  @Override
  public Set<Playlist> followPlaylist(HttpSession session, Integer playlistId) {
    Playlist playlistToFollow = playlistRepository.getPlaylistByPlaylistId(playlistId);
    User currentUser = (User) session.getAttribute("user");
    if(currentUser == null || playlistToFollow == null) {
      return null;
    }
    Set<Playlist> followedPlaylists = currentUser.getFollowedPlaylists();
    followedPlaylists.add(playlistToFollow);
    return followedPlaylists;
  }

  @Override
  public Set<Playlist> unFollowPlaylist(HttpSession session, Integer playlistId) {
    Playlist playlistToUnFollow = playlistRepository.getPlaylistByPlaylistId(playlistId);
    User currentUser = (User) session.getAttribute("user");
    if(currentUser == null || playlistToUnFollow == null) {
      return null;
    }
    Set<Playlist> followedPlaylists = currentUser.getFollowedPlaylists();
    followedPlaylists.remove(playlistToUnFollow);
    return followedPlaylists;
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
  public Set<Follower> getFollowing(HttpSession session) {
    Set<Follower> following = null;
    User user = (User)session.getAttribute("user");
    if(user != null) {
      following = user.getFollowing();
    }
    return following;
  }

  @Override
  public Follower unFollow(Integer followingId, HttpSession session){
    Follower followerToRemove = null;
    User user = (User)session.getAttribute("user");
    User following = userRepository.findUserByUserId(followingId);
    if(user != null){
      user.unfollow(following);
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
      Set<Playlist> userPlaylists = playlistRepository.getPlaylistsByOwner(currentUser);
      for(Playlist userPlaylist : userPlaylists){
        playlistRepository.delete(userPlaylist);
      }

      userRepository.delete(currentUser);
      deletionSuccess = true;
    }
    return deletionSuccess;
  }


  @Override
  public Set<Follower> follow(HttpSession session, Integer userId) {
    Set<Follower> following = null;
    User currentUser = (User) session.getAttribute("user");
    if(currentUser != null) {
      User user = userRepository.findUserByUserId(userId);
      if(user != null) {
        following = currentUser.follow(user);
        userRepository.save(currentUser);
      }
    }
    return following;
  }


}
