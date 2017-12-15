package com.amplio.amplio.controllers;

import com.amplio.amplio.forms.EditUserInfoForm;
import com.amplio.amplio.forms.UpgradePremiumForm;
import com.amplio.amplio.models.*;
import com.amplio.amplio.service.UserService;
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
  private UserService userService;

  @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
  public ResponseEntity<Boolean> deleteUser(HttpSession session) {
    HttpStatus status;
    Boolean deletionSuccess = userService.deleteUser(session);

    if(deletionSuccess) {
      status = HttpStatus.OK;
    } else {
      status = HttpStatus.BAD_REQUEST;
    }

    return new ResponseEntity<Boolean>(deletionSuccess, status);
  }

  @RequestMapping(path = "/updateinfo", method = RequestMethod.POST)
  public ResponseEntity<User> updateUser(@RequestBody EditUserInfoForm editUserInfoForm, HttpSession session) {
    HttpStatus status;
    User updatedUser = userService.updateUser(editUserInfoForm, session);

    if(updatedUser == null){
      status = HttpStatus.FORBIDDEN;
    }
    else{
      status = HttpStatus.OK;
    }

    return new ResponseEntity<User>(updatedUser, status);
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
  public ResponseEntity<List<User>> searchUser(@PathVariable String query, HttpSession session) {
    HttpStatus status;
    List<User> users = userService.searchUser(query, session);

    if(users == null) {
      status = HttpStatus.FORBIDDEN;
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
      return new ResponseEntity<Set<Follower>>(following, status);
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
    HttpStatus status;
    Set<Follower> followers = userService.getFollowers(session);

    if(followers == null) {
      status = HttpStatus.NOT_FOUND;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Set<Follower>>(followers, status);
  }

  @RequestMapping(path = "/following", method = RequestMethod.GET)
  public ResponseEntity<Set<Follower>> getFollowing(HttpSession session) {
    HttpStatus status;
    Set<Follower> following = userService.getFollowing(session);

    if(following == null) {
      status = HttpStatus.NOT_FOUND;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Set<Follower>>(following, status);
  }

  @RequestMapping(path = "/unfollow/{followerId}", method = RequestMethod.POST)
  public ResponseEntity<Set<Follower>> unfollow(@PathVariable String followerId, HttpSession session) {
    HttpStatus status;
    Set<Follower> following = null;
    Integer followingToRemoveId;

    try {
      followingToRemoveId = Integer.parseInt(followerId);
      following = userService.unfollow(session, followingToRemoveId);

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

  @RequestMapping(path = "/followartist/{artistId}", method = RequestMethod.POST)
  public ResponseEntity<Set<Artist>> followArtist(@PathVariable String artistId, HttpSession session) {
    HttpStatus status;
    Integer artistToFollowId;
    Set<Artist> followingArtist = null;

    try {
      artistToFollowId = Integer.parseInt(artistId);
      followingArtist = userService.followArtist(session, artistToFollowId);
    } catch(NumberFormatException e) {
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Set<Artist>>(followingArtist, status);
    }

    if(followingArtist == null) {
      status = HttpStatus.UNAUTHORIZED;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Set<Artist>>(followingArtist, status);
  }

  @RequestMapping(path = "/unfollowartist/{artistId}", method = RequestMethod.POST)
  public ResponseEntity<Set<Artist>> unfollowArtist(@PathVariable String artistId, HttpSession session) {
    HttpStatus status;
    Integer artistToUnfollowId;
    Set<Artist> followingArtist = null;

    try {
      artistToUnfollowId = Integer.parseInt(artistId);
      followingArtist = userService.unfollowArtist(session, artistToUnfollowId);
    } catch(NumberFormatException e) {
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Set<Artist>>(followingArtist, status);
    }

    if(followingArtist == null) {
      status = HttpStatus.UNAUTHORIZED;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Set<Artist>>(followingArtist, status);
  }

  @RequestMapping(path = "/followplaylist/{playlistId}", method = RequestMethod.POST)
  public ResponseEntity<Set<Playlist>> followPlaylist(@PathVariable String playlistId, HttpSession session) {
    HttpStatus status;
    Integer playlistToFollowId;
    Set<Playlist> followingPlaylists = null;

    try {
      playlistToFollowId = Integer.parseInt(playlistId);
      followingPlaylists = userService.followPlaylist(session, playlistToFollowId);
    } catch(NumberFormatException e) {
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Set<Playlist>>(followingPlaylists, status);
    }

    if(followingPlaylists == null) {
      status = HttpStatus.UNAUTHORIZED;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Set<Playlist>>(followingPlaylists, status);
  }

  @RequestMapping(path = "/unfollowplaylist/{playlistId}", method = RequestMethod.POST)
  public ResponseEntity<Set<Playlist>> unfollowPlaylist(@PathVariable String playlistId, HttpSession session) {
    HttpStatus status;
    Integer playlistToUnfollowId;
    Set<Playlist> followingPlaylists = null;

    try {
      playlistToUnfollowId = Integer.parseInt(playlistId);
      followingPlaylists = userService.unfollowPlaylist(session, playlistToUnfollowId);
    } catch(NumberFormatException e) {
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Set<Playlist>>(followingPlaylists, status);
    }

    if(followingPlaylists == null) {
      status = HttpStatus.UNAUTHORIZED;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Set<Playlist>>(followingPlaylists, status);
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

  @RequestMapping(path = "/updatepicture", method = RequestMethod.POST)
  public ResponseEntity<Boolean> updateProfilePicture(String imagePath, HttpSession session) {
    HttpStatus status;
    Boolean updatedPicture = userService.updateProfilePicture(imagePath, session);

    if(updatedPicture == false) {
      status = HttpStatus.FORBIDDEN;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Boolean>(updatedPicture, status);
  }

  @RequestMapping(path = "/queue/add", method = RequestMethod.POST)
  public ResponseEntity<Boolean> addSongToQueue(Song songToAdd, HttpSession session) {
    HttpStatus status;
    Boolean songAdded = userService.addSongToQueue(songToAdd, session);

    if(songAdded == false) {
      status = HttpStatus.BAD_REQUEST;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Boolean>(songAdded, status);
  }

  @RequestMapping(path = "/queue/delete", method = RequestMethod.DELETE)
  public ResponseEntity<Boolean> deleteSongFromQueue(Song songToDelete, HttpSession session) {
    HttpStatus status;
    Boolean songDeleted = userService.deleteSongFromQueue(songToDelete, session);

    if(songDeleted == false) {
      status = HttpStatus.BAD_REQUEST;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Boolean>(songDeleted, status);
  }

  @RequestMapping(path = "/upgrade", method = RequestMethod.POST)
  public ResponseEntity<Boolean> upgradeUser(@RequestBody UpgradePremiumForm upgradePremiumForm, HttpSession session) {
    HttpStatus status;
    Boolean upgraded = userService.upgradeUser(upgradePremiumForm, session);

    if(upgraded) {
      status = HttpStatus.OK;
    } else {
      status = HttpStatus.FORBIDDEN;
    }

    return new ResponseEntity<Boolean>(upgraded, status);
  }

  @RequestMapping(path = "/downgrade", method = RequestMethod.POST)
  public ResponseEntity<Boolean> downgradeUser(HttpSession session) {
    HttpStatus status;
    Boolean downgraded = userService.downgradeUser(session);

    if(downgraded) {
      status = HttpStatus.OK;
    } else {
      status = HttpStatus.FORBIDDEN;
    }

    return new ResponseEntity<Boolean>(downgraded, status);
  }

  @RequestMapping(path = "/savesong/{id}", method = RequestMethod.POST)
  public ResponseEntity<Boolean> saveSong(@PathVariable String id, HttpSession session) {
    HttpStatus status;
    Boolean savedSong = false;
    Integer songId;

    try {
      songId = Integer.parseInt(id);
    } catch(NumberFormatException e) {
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Boolean>(savedSong, status);
    }

    savedSong = userService.saveSong(songId, session);

    if(savedSong){
      status = HttpStatus.FORBIDDEN;
    }
    else{
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Boolean>(savedSong, status);
  }

  @RequestMapping(path = "/unsavesong/{id}", method = RequestMethod.POST)
  public ResponseEntity<Boolean> unsaveSong(@PathVariable String id, HttpSession session) {
    HttpStatus status;
    Boolean unsavedSong = false;
    Integer songId;

    try {
      songId = Integer.parseInt(id);
    } catch(NumberFormatException e) {
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Boolean>(unsavedSong, status);
    }

    unsavedSong = userService.unsaveSong(songId, session);

    if(unsavedSong){
      status = HttpStatus.FORBIDDEN;
    }
    else{
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Boolean>(unsavedSong, status);
  }

  @RequestMapping(path = "/savealbum/{id}", method = RequestMethod.POST)
  public ResponseEntity<Set<Album>> saveAlbum(@PathVariable String id, HttpSession session) {
    HttpStatus status;
    Set<Album> savedAlbums = null;
    Integer albumId;

    try {
      albumId = Integer.parseInt(id);
    } catch(NumberFormatException e) {
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Set<Album>>(savedAlbums, status);
    }

    savedAlbums = userService.saveAlbum(albumId, session);

    if(savedAlbums == null){
      status = HttpStatus.FORBIDDEN;
    }
    else{
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Set<Album>>(savedAlbums, status);
  }

  @RequestMapping(path = "/unsavealbum/{id}", method = RequestMethod.POST)
  public ResponseEntity<Boolean> unsaveAlbum(@PathVariable String id, HttpSession session) {
    HttpStatus status;
    Boolean unsavedAlbum = false;
    Integer albumId;

    try {
      albumId = Integer.parseInt(id);
    } catch(NumberFormatException e) {
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Boolean>(unsavedAlbum, status);
    }

    unsavedAlbum = userService.unsaveAlbum(albumId, session);

    if(unsavedAlbum){
      status = HttpStatus.FORBIDDEN;
    }
    else{
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Boolean>(unsavedAlbum, status);
  }

  @RequestMapping(path = "/report/{id}", method = RequestMethod.POST)
  public ResponseEntity<Boolean> reportUser(@PathVariable String id, HttpSession session){
    HttpStatus status;
    Boolean reportedUser = false;
    Integer userToReportId;

    try{
      userToReportId = Integer.parseInt(id);
    }
    catch(NumberFormatException e){
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Boolean>(reportedUser, status);
    }

    reportedUser = userService.reportUser(userToReportId, session);

    if(reportedUser){
      status = HttpStatus.OK;
    }
    else{
      status = HttpStatus.NOT_FOUND;
    }

    return  new ResponseEntity<Boolean>(reportedUser, status);
  }
}
