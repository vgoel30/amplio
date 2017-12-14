package com.amplio.amplio.service;

import com.amplio.amplio.forms.UpgradePremiumForm;
import com.amplio.amplio.models.*;
import com.amplio.amplio.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

import static com.amplio.amplio.constants.Constants.SESSION_USER;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private FollowerRepository followerRepository;
  @Autowired
  private PlaylistRepository playlistRepository;
  @Autowired
  private ArtistRepository artistRepository;
  @Autowired
  private SongRepository songRepository;
  @Autowired
  private AlbumRepository albumRepository;


  public User getUser(Integer userId) {
    User user = userRepository.findUserById(userId);
    Set<Playlist> playlists = playlistRepository.findPlaylistsByOwner(user);
    for(Playlist playlist : playlists) {
      playlist.setOwner(null);
    }
    user.setPlaylists(playlists);
    return user;
  }


  public Set<Playlist> getPlaylists(HttpSession session) {
    User currentUser = (User) session.getAttribute(SESSION_USER);
    if(currentUser == null) {
      return null;
    }
    currentUser = userRepository.findUserById(currentUser.getId());
    return playlistRepository.findPlaylistsByOwner(currentUser);
  }


  public Set<Playlist> getFollowedPlaylists(HttpSession session) {
    User currentUser = (User) session.getAttribute(SESSION_USER);
    if(currentUser == null) {
      return null;
    }
    currentUser = userRepository.findUserById(currentUser.getId());
    return currentUser.getFollowedPlaylists();
  }


  public Set<Playlist> followPlaylist(HttpSession session, Integer playlistId) {
    Playlist playlistToFollow = playlistRepository.getPlaylistById(playlistId);
    User currentUser = (User) session.getAttribute(SESSION_USER);
    if(currentUser == null || playlistToFollow == null) {
      return null;
    }
    currentUser = userRepository.findUserById(currentUser.getId());
    Set<Playlist> followedPlaylists = currentUser.getFollowedPlaylists();
    followedPlaylists.add(playlistToFollow);
    userRepository.save(currentUser);
    return followedPlaylists;
  }


  public Set<Playlist> unfollowPlaylist(HttpSession session, Integer playlistId) {
    Playlist playlistToUnfollow = playlistRepository.getPlaylistById(playlistId);
    User currentUser = (User) session.getAttribute(SESSION_USER);
    if(currentUser == null || playlistToUnfollow == null) {
      return null;
    }
    currentUser = userRepository.findUserById(currentUser.getId());
    Set<Playlist> followedPlaylists = currentUser.getFollowedPlaylists();
    followedPlaylists.remove(playlistToUnfollow);
    userRepository.save(currentUser);
    return followedPlaylists;
  }

  public Set<Artist> followArtist(HttpSession session, Integer artistId) {
    Artist artistToFollow = artistRepository.findById(artistId);
    User currentUser = (User) session.getAttribute(SESSION_USER);

    if(currentUser == null || artistToFollow == null) {
      return null;
    }

    currentUser = userRepository.findUserById(currentUser.getId());
    Set<Artist> followingArtists = currentUser.getFollowedArtists();
    followingArtists.add(artistToFollow);
    userRepository.save(currentUser);
    return followingArtists;
  }

  public Set<Artist> unfollowArtist(HttpSession session, Integer artistId) {
    Artist artistToUnfollow = artistRepository.findById(artistId);
    User currentUser = (User) session.getAttribute(SESSION_USER);

    if(currentUser == null || artistToUnfollow == null) {
      return null;
    }

    currentUser = userRepository.findUserById(currentUser.getId());
    Set<Artist> followingArtists = currentUser.getFollowedArtists();
    followingArtists.remove(artistToUnfollow);
    userRepository.save(currentUser);
    return followingArtists;
  }


  public List<User> searchUser(String query, HttpSession session) {
    User currentUser = (User) session.getAttribute(SESSION_USER);
    List<User> users = null;
    if(currentUser != null) {
      users = userRepository.findTop10UsersByUserNameContainingIgnoreCase(query);
    }
    return users;
  }


  public Set<Follower> getFollowers(HttpSession session) {
    Set<Follower> followers = null;
    User user = (User) session.getAttribute(SESSION_USER);
    if(user != null) {
      user = userRepository.findUserById(user.getId());
      followers = user.getFollowers();
    }
    return followers;
  }


  public Set<Follower> getFollowing(HttpSession session) {
    Set<Follower> following = null;
    User user = (User) session.getAttribute(SESSION_USER);
    if(user != null) {
      user = userRepository.findUserById(user.getId());
      following = user.getFollowing();
    }
    return following;
  }

  public Boolean deleteUser(HttpSession session) {
    Boolean deletionSuccess = false;
    User currentUser = (User) session.getAttribute(SESSION_USER);
    if(currentUser != null) {
      currentUser = userRepository.findUserById(currentUser.getId());
      Integer userToDeleteId = currentUser.getId();

      //1. delete this user from the following list of their followers
      for(Follower follower : currentUser.getFollowers()) {
        Integer followerId = follower.getId();
        User followerAsUser = userRepository.findUserById(followerId);
        Set<Follower> followingSet = followerAsUser.getFollowing();
        for(Follower following : followingSet) {
          if(following.getId().equals(userToDeleteId)) {
            followingSet.remove(following);
            break;
          }
        }
      }

      //2. delete this user from the followers list of the people that they are following
      for(Follower following : currentUser.getFollowing()) {
        Integer followingId = following.getId();
        User followingAsUser = userRepository.findUserById(followingId);
        Set<Follower> followerSet = followingAsUser.getFollowers();
        for(Follower follower : followerSet) {
          if(follower.getId().equals(userToDeleteId)) {
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
    User currentUser = (User) session.getAttribute(SESSION_USER);

    if(currentUser != null) {
      currentUser = userRepository.findUserById(currentUser.getId());
      following = currentUser.getFollowing();
      Follower followerToFollow = followerRepository.findById(userId);
      if(followerToFollow != null) {
        following.add(followerToFollow);
        userRepository.save(currentUser);

        Follower currentFollower = followerRepository.findById(currentUser.getId());
        User userToFollow = userRepository.findUserById(userId);
        userToFollow.getFollowers().add(currentFollower);
        userRepository.save(userToFollow);
      }
    }
    return following;
  }


  public Set<Follower> unfollow(HttpSession session, Integer followingId) {
    Set<Follower> followingSet = null;
    User currentUser = (User) session.getAttribute(SESSION_USER);

    if(currentUser != null) {
      User userToUnFollow = userRepository.findUserById(followingId);
      if(userToUnFollow != null) {
        currentUser = userRepository.findUserById(currentUser.getId());
        followingSet = currentUser.getFollowing();
        for(Follower person : followingSet) {
          if(person.getId().equals(followingId)) {
            followingSet.remove(person);
            break;
          }
        }
        userRepository.save(currentUser);

        Set<Follower> followerSet = userToUnFollow.getFollowers();
        for(Follower follower : followerSet) {
          if(follower.getId().equals(currentUser.getId())) {
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
    User user = (User) session.getAttribute(SESSION_USER);

    if(user != null) {
      user = userRepository.findUserById(user.getId());
      user.getSongQueue().getSongs().add(songToAdd);
      userRepository.save(user);
      songAdded = true;
    }
    return songAdded;
  }

  public Boolean deleteSongFromQueue(Song songToDelete, HttpSession session) {
    Boolean songDeleted = false;
    User user = (User) session.getAttribute(SESSION_USER);

    if(user != null) {
      user = userRepository.findUserById(user.getId());
      songDeleted = user.getSongQueue().getSongs().remove(songToDelete);
      userRepository.save(user);
    }
    return songDeleted;
  }

  public Boolean updateProfilePicture(String imagePath, HttpSession session) {
    Boolean updatedProfilePicture = false;
    User user = (User) session.getAttribute(SESSION_USER);

    if(user != null) {
      user = userRepository.findUserById(user.getId());
      user.setProfilePicture(imagePath);
      userRepository.save(user);
      updatedProfilePicture = true;
    }

    return updatedProfilePicture;
  }

  public Boolean upgradeUser(UpgradePremiumForm upgradePremiumForm, HttpSession session){
    Boolean upgraded = false;
    Integer creditCardNumber;

    try{
      creditCardNumber = Integer.parseInt(upgradePremiumForm.getCreditCardNumber());
    }
    catch(NumberFormatException e){
      return upgraded;
    }

    User currentUser = (User)session.getAttribute(SESSION_USER);
    if(currentUser != null) {
      currentUser = userRepository.findUserById(currentUser.getId());
    }

    if(currentUser != null && !currentUser.getPremium()){
      currentUser.setPremium(true);
      userRepository.save(currentUser);
      upgraded = true;
    }

    return upgraded;
  }

  public Boolean downgradeUser(HttpSession session){
    Boolean downgraded = false;

    User currentUser = (User)session.getAttribute(SESSION_USER);
    if(currentUser != null) {
      currentUser = userRepository.findUserById(currentUser.getId());
    }

    if(currentUser != null && currentUser.getPremium()){
      currentUser.setPremium(false);
      userRepository.save(currentUser);
      downgraded = true;
    }

    return downgraded;
  }

  public Boolean saveSong(Integer songId, HttpSession session){
    Boolean songSaved = false;
    Song songToSave = songRepository.findSongById(songId);

    if(songToSave != null){
      User currentUser = (User)session.getAttribute(SESSION_USER);
      if(currentUser != null) {
        currentUser = userRepository.findUserById(currentUser.getId());
      }

      if(currentUser != null){
        currentUser.getSavedSongs().add(songToSave);
        userRepository.save(currentUser);
        songSaved = true;
      }
    }

    return songSaved;
  }

  public Boolean unsaveSong(Integer songId, HttpSession session){
    Boolean songUnsaved = false;
    Song songToUnsave = songRepository.findSongById(songId);

    if(songToUnsave != null){
      User currentUser = (User)session.getAttribute(SESSION_USER);
      if(currentUser != null) {
        currentUser = userRepository.findUserById(currentUser.getId());
      }

      if(currentUser != null){
        boolean songPresent = false;
        for(Song savedSong : currentUser.getSavedSongs()){
          if(savedSong.getId().equals(songId)){
            currentUser.getSavedSongs().remove(savedSong);
            songPresent = true;
          }
        }
        userRepository.save(currentUser);
        songUnsaved = songPresent;
      }
    }

    return songUnsaved;
  }

  public Boolean saveAlbum(Integer albumId, HttpSession session){
    Boolean albumSaved = false;
    Album albumToSave = albumRepository.findById(albumId);

    if(albumToSave != null){
      User currentUser = (User)session.getAttribute(SESSION_USER);
      if(currentUser != null) {
        currentUser = userRepository.findUserById(currentUser.getId());
      }

      if(currentUser != null){
        currentUser.getSavedAlbums().add(albumToSave);
        userRepository.save(currentUser);
        albumSaved = true;
      }
    }

    return albumSaved;
  }

  public Boolean unsaveAlbum(Integer albumId, HttpSession session){
    Boolean albumUnsaved = false;
    Album albumToUnsave = albumRepository.findById(albumId);

    if(albumToUnsave != null){
      User currentUser = (User)session.getAttribute(SESSION_USER);
      if(currentUser != null) {
        currentUser = userRepository.findUserById(currentUser.getId());
      }

      if(currentUser != null){
        boolean albumPresent = false;
        for(Album savedAlbum : currentUser.getSavedAlbums()){
          if(savedAlbum.getId().equals(albumId)){
            currentUser.getSavedAlbums() .remove(savedAlbum);
            albumPresent = true;
          }
        }
        userRepository.save(currentUser);
        albumUnsaved = albumPresent;
      }
    }

    return albumUnsaved;
  }
}
