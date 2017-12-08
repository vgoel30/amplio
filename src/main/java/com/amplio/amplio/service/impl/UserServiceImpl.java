package com.amplio.amplio.service.impl;

import com.amplio.amplio.models.Follower;
import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.Song;
import com.amplio.amplio.models.User;
import com.amplio.amplio.repository.FollowerRepository;
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
  private FollowerRepository followerRepository;

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
      following = currentUser.getFollowing();
      Follower followerToFollow = followerRepository.findByUserId(userId);
      if(followerToFollow != null) {
        following.add(followerToFollow);
        userRepository.save(currentUser);

        Follower currentFollower = followerRepository.findByUserId(currentUser.getUserId());
        User userToFollow = userRepository.findUserByUserId(userId);
        userToFollow.getFollowers().add(currentFollower);
        userRepository.save(userToFollow);
      }
    }
    return following;
  }

  @Override
  public Set<Follower> unFollow(HttpSession session, Integer followingId) {
    Set<Follower> followingSet = null;
    User currentUser = (User) session.getAttribute("user");

    if(currentUser != null) {
      Follower followerToUnFollow = followerRepository.findByUserId(followingId);
      if(followerToUnFollow != null) {
        followingSet = currentUser.getFollowing();
        for(Follower person : followingSet){
          if(person.getUserId().equals(followingId)){
            followingSet.remove(person);
            break;
          }
        }
        userRepository.save(currentUser);

        User userToUnFollow = userRepository.findUserByUserId(followingId);
        Set<Follower> followerSet = userToUnFollow.getFollowers();
        for(Follower follower : followerSet){
          if(follower.getUserId().equals(currentUser.getUserId())){
            userToUnFollow.getFollowers().remove(follower);
            break;
          }
        }
        userRepository.save(userToUnFollow);
      }
    }
    return followingSet;
  }

  @Override
  public Boolean addSongToQueue(Song songToAdd, HttpSession session) {
    Boolean songAdded = false;
    User user = (User)session.getAttribute("user");

    if(user != null){
      user.getSongQueue().getSongs().add(songToAdd);
      userRepository.save(user);
      songAdded = true;
    }

    return songAdded;
  }

  @Override
  public Boolean deleteSongFromQueue(Song songToDelete, HttpSession session){
    Boolean songDeleted = false;
    User user = (User)session.getAttribute("user");

    if(user != null){
      songDeleted = user.getSongQueue().getSongs().remove(songToDelete);
      userRepository.save(user);
    }

    return songDeleted;

  }
}
