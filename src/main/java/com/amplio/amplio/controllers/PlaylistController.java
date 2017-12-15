package com.amplio.amplio.controllers;

import com.amplio.amplio.constants.Constants;
import com.amplio.amplio.forms.PlaylistForm;
import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.Song;
import com.amplio.amplio.models.User;
import com.amplio.amplio.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/playlist")
public class PlaylistController {
  @Autowired
  private PlaylistService playlistService;

  @RequestMapping(path = "/create", method = RequestMethod.POST)
  public ResponseEntity<Playlist> createPlaylist(@RequestBody PlaylistForm playlistForm, HttpSession session) {
    HttpStatus status;
    Playlist createdPlaylist = playlistService.createPlaylist(playlistForm, session);

    if(createdPlaylist == null) {
      status = HttpStatus.BAD_REQUEST;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Playlist>(createdPlaylist, status);
  }

  @RequestMapping(path = "/addsong/{playlistId}", method = RequestMethod.POST)
  public ResponseEntity<Song> addSongToPlaylist(@PathVariable String playlistId, @RequestBody String songId, HttpSession session) {
    HttpStatus status;
    Song addedSong = null;
    Integer playlistIntegerId;
    Integer songIntegerId;

    try{
      playlistIntegerId = Integer.parseInt(playlistId);
      songIntegerId = Integer.parseInt(songId);
    }
    catch(NumberFormatException e){
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Song>(addedSong, status);
    }

    addedSong = playlistService.addSongToPlaylist(playlistIntegerId, songIntegerId, session);

    if(addedSong != null){
      status = HttpStatus.OK;
    }
    else{
      status = HttpStatus.FORBIDDEN;
    }

    return new ResponseEntity<Song>(addedSong, status);
  }

  @RequestMapping(path = "/addsong/{playlistId}", method = RequestMethod.POST)
  public ResponseEntity<Song> removeSongFromPlaylist(@PathVariable String playlistId, @RequestBody String songId, HttpSession session) {
    HttpStatus status;
    Song deletedSong = null;
    Integer playlistIntegerId;
    Integer songIntegerId;

    try{
      playlistIntegerId = Integer.parseInt(playlistId);
      songIntegerId = Integer.parseInt(songId);
    }
    catch(NumberFormatException e){
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Song>(deletedSong, status);
    }

    deletedSong = playlistService.removeSongFromPlaylist(playlistIntegerId, songIntegerId, session);

    if(deletedSong != null){
      status = HttpStatus.OK;
    }
    else{
      status = HttpStatus.FORBIDDEN;
    }

    return new ResponseEntity<Song>(deletedSong, status);
  }

  @RequestMapping(path = "/edit/{id}", method = RequestMethod.POST)
  public ResponseEntity<Playlist> editPlaylist(@PathVariable String id, @RequestBody PlaylistForm editPlaylistForm,
                                               HttpSession session) {
    Playlist editedPlaylist = null;
    HttpStatus status;
    Integer playlistId;
    try {
      playlistId = Integer.parseInt(id);
    } catch(NumberFormatException numberFormatException) {
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Playlist>(editedPlaylist, status);
    }
    editedPlaylist = playlistService.editPlaylist(playlistId, editPlaylistForm, session);

    if(editedPlaylist == null) {
      status = HttpStatus.BAD_REQUEST;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Playlist>(editedPlaylist, status);
  }

  @RequestMapping(path = "delete/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Boolean> deletePlaylist(@PathVariable String id, HttpSession session) {
    Boolean deleted = false;
    HttpStatus status;
    Integer playlistId;
    try {
      playlistId = Integer.parseInt(id);
    } catch(NumberFormatException numberFormatException) {
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Boolean>(deleted, status);
    }
    deleted = playlistService.deletePlaylist(playlistId, session);
    if(!deleted) {
      status = HttpStatus.BAD_REQUEST;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Boolean>(deleted, status);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<Playlist> getPlaylist(@PathVariable String id) {
    Playlist playlist = null;
    HttpStatus status;
    Integer playlistId;

    try {
      playlistId = Integer.parseInt(id);
    } catch(NumberFormatException numberFormatException) {
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Playlist>(playlist, status);
    }

    playlist = playlistService.getPlaylist(playlistId);

    if(playlist == null) {
      status = HttpStatus.NOT_FOUND;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Playlist>(playlist, status);
  }

  @RequestMapping(path = "/generated", method = RequestMethod.GET)
  public ResponseEntity<Set<Playlist>> getGeneratedPLaylists(HttpSession session) {
    User currentUser = (User) session.getAttribute(Constants.SESSION_USER);
    Set<Playlist> generatedPlaylists = null;
    HttpStatus status;

    if(currentUser == null) {
      status = HttpStatus.FORBIDDEN;
    } else {
      generatedPlaylists = playlistService.getGeneratedPlaylists();
      if(generatedPlaylists == null) {
        status = HttpStatus.NO_CONTENT;
      } else {
        status = HttpStatus.OK;
      }
    }
    return new ResponseEntity<Set<Playlist>>(generatedPlaylists, status);
  }

  @RequestMapping(path = "/genre/{genreName}", method = RequestMethod.POST)
  public ResponseEntity<Playlist> generateGenrePlaylist(@PathVariable String genreName) {
    return new ResponseEntity<Playlist>(playlistService.generateGenrePlaylist(genreName), HttpStatus.OK);
  }

  @RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
  public ResponseEntity<Set<Playlist>> getPlaylistsByUser(@PathVariable String id, HttpSession session) {
    HttpStatus status;
    Set<Playlist> playlists = null;
    Integer userId;

    try {
      userId = Integer.parseInt(id);

      playlists = playlistService.getPlaylistsByUser(userId, session);

      if(playlists == null) {
        status = HttpStatus.FORBIDDEN;
      } else {
        status = HttpStatus.OK;
      }
    } catch(NumberFormatException numberFormatException) {
      status = HttpStatus.BAD_REQUEST;
    }

    return new ResponseEntity<Set<Playlist>>(playlists, status);
  }

  @RequestMapping(path = "/search/{query}", method = RequestMethod.GET)
  public ResponseEntity<List<Playlist>> searchPlaylist(@PathVariable String query, HttpSession session) {
    HttpStatus status;
    List<Playlist> playlists = playlistService.searchPlaylist(query, session);

    if(playlists == null) {
      status = HttpStatus.FORBIDDEN;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<List<Playlist>>(playlists, status);
  }

  @RequestMapping(path = "/toggleprivate/{id}", method = RequestMethod.POST)
  public ResponseEntity<Boolean> togglePrivatePlaylist(@PathVariable String id, HttpSession session){
    HttpStatus status;
    Boolean toggledPrivate = false;
    Integer playlistId;

    try{
      playlistId = Integer.parseInt(id);
    }
    catch(Exception e){
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Boolean>(toggledPrivate, status);
    }

    toggledPrivate = playlistService.togglePrivatePlaylist(playlistId, session);

    if(toggledPrivate){
      status = HttpStatus.OK;
    }
    else{
      status = HttpStatus.FORBIDDEN;
    }

    return new ResponseEntity<Boolean>(toggledPrivate, status);
  }

  @RequestMapping(path = "/topcharts", method = RequestMethod.GET)
  public ResponseEntity<Playlist> getTopCharts(HttpSession session) {
    HttpStatus status;
    Playlist topCharts = playlistService.getTopCharts(session);

    if(topCharts == null) {
      status = HttpStatus.FORBIDDEN;
    } else {
      status= HttpStatus.OK;
    }

    return new ResponseEntity<Playlist>(topCharts,status);
  }
}
