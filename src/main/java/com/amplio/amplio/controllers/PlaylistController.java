package com.amplio.amplio.controllers;

import com.amplio.amplio.forms.EditPlaylistForm;
import com.amplio.amplio.forms.PlaylistForm;
import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.User;
import com.amplio.amplio.service.impl.PlaylistServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/playlist")
public class PlaylistController {
  @Autowired
  private PlaylistServiceImpl playlistService;

  @RequestMapping(path = "/create", method = RequestMethod.POST)
  public ResponseEntity<Playlist> createPlaylist(@RequestBody PlaylistForm playlistForm, HttpSession session){
    HttpStatus status;
    Playlist createdPlaylist = playlistService.createPlaylist(playlistForm, session);

    if(createdPlaylist == null){
      status = HttpStatus.BAD_REQUEST;
    } else{
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Playlist>(createdPlaylist, status);
  }

  @RequestMapping(path = "/edit/{id}", method = RequestMethod.POST)
  public ResponseEntity<Playlist> editPlaylist(@PathVariable String id, @RequestBody EditPlaylistForm editPlaylistForm, HttpSession session){
    Playlist editedPlaylist = null;
    HttpStatus status;
    Integer playlistId;
    try{
      playlistId = Integer.parseInt(id);
    } catch(NumberFormatException numberFormatException){
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Playlist>(editedPlaylist, status);
    }
    editedPlaylist = playlistService.editPlaylist(playlistId, editPlaylistForm, session);

    if(editedPlaylist == null){
      status = HttpStatus.BAD_REQUEST;
    } else{
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Playlist>(editedPlaylist, status);
  }

  @RequestMapping(path = "delete/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Playlist> deletePlaylist(@PathVariable String id, HttpSession session){
    Playlist deletedPlaylist = null;
    HttpStatus status;
    Integer playlistId;
    try{
      playlistId = Integer.parseInt(id);
    }
    catch(NumberFormatException numberFormatException){
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Playlist>(deletedPlaylist, status);
    }
    deletedPlaylist = playlistService.deletePlaylist(playlistId, session);
    if(deletedPlaylist == null){
      status = HttpStatus.BAD_REQUEST;
    }
    else{
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Playlist>(deletedPlaylist, status);
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
    User currentUser = (User) session.getAttribute("user");
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
    return new ResponseEntity<Playlist>(playlistService.generateGenrePlaylist(genreName),HttpStatus.OK);
  }
}
