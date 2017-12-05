package com.amplio.amplio.controllers;

import com.amplio.amplio.forms.PlaylistForm;
import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.service.impl.PlaylistServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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

}
