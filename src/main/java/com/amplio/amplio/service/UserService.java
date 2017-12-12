package com.amplio.amplio.service;

import com.amplio.amplio.constants.Constants;
import com.amplio.amplio.models.Follower;
import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.Song;
import com.amplio.amplio.models.User;
import com.amplio.amplio.repository.FollowerRepository;
import com.amplio.amplio.repository.PlaylistRepository;
import com.amplio.amplio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private FollowerRepository followerRepository;
  @Autowired
  private PlaylistRepository playlistRepository;


  public User getUser(Integer userId) {
    User user = userRepository.findUserByUserId(userId);
    return user;
  }


  public Set<Playlist> getPlaylists(HttpSession session) {
    User currentUser = (User) session.getAttribute(Constants.SESSION_USER);
    if(currentUser == null) {
      return null;
    }
    currentUser = userRepository.findUserByUserId(currentUser.getUserId());
    return playlistRepository.findPlaylistsByOwner(currentUser);
  }


  public Set<Playlist> getFollowedPlaylists(HttpSession session) {
    User currentUser = (User) session.getAttribute(Constants.SESSION_USER);
    if(currentUser == null) {
      return null;
    }
    currentUser = userRepository.findUserByUserId(currentUser.getUserId());
    return currentUser.getFollowedPlaylists();
  }


  public Set<Playlist> followPlaylist(HttpSession session, Integer playlistId) {
    Playlist playlistToFollow = playlistRepository.getPlaylistByPlaylistId(playlistId);
    User currentUser = (User) session.getAttribute(Constants.SESSION_USER);
    if(currentUser == null || playlistToFollow == null) {
      return null;
    }
    currentUser = userRepository.findUserByUserId(currentUser.getUserId());
    Set<Playlist> followedPlaylists = currentUser.getFollowedPlaylists();
    followedPlaylists.add(playlistToFollow);
    return followedPlaylists;
  }


  public Set<Playlist> unfollowPlaylist(HttpSession session, Integer playlistId) {
    Playlist playlistToUnFollow = playlistRepository.getPlaylistByPlaylistId(playlistId);
    User currentUser = (User) session.getAttribute(Constants.SESSION_USER);
    if(currentUser == null || playlistToUnFollow == null) {
      return null;
    }
    currentUser = userRepository.findUserByUserId(currentUser.getUserId());
    Set<Playlist> followedPlaylists = currentUser.getFollowedPlaylists();
    followedPlaylists.remove(playlistToUnFollow);
    return followedPlaylists;
  }


  public List<User> searchUser(String query) {
    List<User> users = userRepository.findTop10ByUserNameContainingIgnoreCase(query);
    return users;
  }


  public Set<Follower> getFollowers(HttpSession session) {
    Set<Follower> followers = null;
    User user = (User) session.getAttribute(Constants.SESSION_USER);
    if(user != null) {
      user = userRepository.findUserByUserId(user.getUserId());
      followers = user.getFollowers();
    }
    return followers;
  }


  public Set<Follower> getFollowing(HttpSession session) {
    Set<Follower> following = null;
    User user = (User) session.getAttribute(Constants.SESSION_USER);
    if(user != null) {
      user = userRepository.findUserByUserId(user.getUserId());
      following = user.getFollowing();
    }
    return following;
  }


  public Boolean deleteUser(HttpSession session) {
    Boolean deletionSuccess = false;
    User currentUser = (User) session.getAttribute(Constants.SESSION_USER);
    if(currentUser != null) {
      currentUser = userRepository.findUserByUserId(currentUser.getUserId());
      Integer userToDeleteId = currentUser.getUserId();

      //1. delete this user from the following list of their followers
      for(Follower follower : currentUser.getFollowers()) {
        Integer followerId = follower.getUserId();
        User followerAsUser = userRepository.findUserByUserId(followerId);
        Set<Follower> followingSet = followerAsUser.getFollowing();
        for(Follower following : followingSet) {
          if(following.getUserId().equals(userToDeleteId)) {
            followingSet.remove(following);
            break;
          }
        }
      }

      //2. delete this user from the followers list of the people that they are following
      for(Follower following : currentUser.getFollowing()) {
        Integer followingId = following.getUserId();
        User followingAsUser = userRepository.findUserByUserId(followingId);
        Set<Follower> followerSet = followingAsUser.getFollowers();
        for(Follower follower : followerSet) {
          if(follower.getUserId().equals(userToDeleteId)) {
            followerSet.remove(follower);
            break;
          }
        }
      }

      //3: Delete the playlists for this user.
      Set<Playlist> userPlaylists = playlistRepository.findPlaylistsByOwner(currentUser);
      for(Playlist userPlaylist : userPlaylists) {
        playlistRepository.delete(userPlaylist);
      }

      userRepository.delete(currentUser);
      deletionSuccess = true;
    }
    return deletionSuccess;
  }


  public Set<Follower> follow(HttpSession session, Integer userId) {
    Set<Follower> following = null;
    User currentUser = (User) session.getAttribute(Constants.SESSION_USER);

    if(currentUser != null) {
      currentUser = userRepository.findUserByUserId(currentUser.getUserId());
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


  public Set<Follower> unfollow(HttpSession session, Integer followingId) {
    Set<Follower> followingSet = null;
    User currentUser = (User) session.getAttribute(Constants.SESSION_USER);

    if(currentUser != null) {
      User userToUnFollow = userRepository.findUserByUserId(followingId);
      if(userToUnFollow != null) {
        currentUser = userRepository.findUserByUserId(currentUser.getUserId());
        followingSet = currentUser.getFollowing();
        for(Follower person : followingSet) {
          if(person.getUserId().equals(followingId)) {
            followingSet.remove(person);
            break;
          }
        }
        userRepository.save(currentUser);

        Set<Follower> followerSet = userToUnFollow.getFollowers();
        for(Follower follower : followerSet) {
          if(follower.getUserId().equals(currentUser.getUserId())) {
            userToUnFollow.getFollowers().remove(follower);
            break;
          }
        }
        userRepository.save(userToUnFollow);
      }
    }
    return followingSet;
  }


  public Boolean addSongToQueue(Song songToAdd, HttpSession session) {
    Boolean songAdded = false;
    User user = (User) session.getAttribute(Constants.SESSION_USER);

    if(user != null) {
      user = userRepository.findUserByUserId(user.getUserId());
      user.getSongQueue().getSongs().add(songToAdd);
      userRepository.save(user);
      songAdded = true;
    }
    return songAdded;
  }


  public Boolean deleteSongFromQueue(Song songToDelete, HttpSession session) {
    Boolean songDeleted = false;
    User user = (User) session.getAttribute(Constants.SESSION_USER);

    if(user != null) {
      user = userRepository.findUserByUserId(user.getUserId());
      songDeleted = user.getSongQueue().getSongs().remove(songToDelete);
      userRepository.save(user);
    }
    return songDeleted;
  }
}
