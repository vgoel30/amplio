package com.amplio.amplio.controllers;

import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.User;
import com.amplio.amplio.service.impl.PlaylistServiceImpl;
import com.amplio.amplio.service.impl.SessionServiceImpl;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/playlist")
public class PlaylistController {
  @Autowired
  private PlaylistServiceImpl playlistService;

  @RequestMapping(path = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<Playlist> getPlaylist(@PathVariable String id){
    Playlist playlist = null
    HttpStatus status;
    Integer playlistId;

    try{
      playlistId = Integer.parseInt(id);
    }
    catch(NumberFormatException numberFormatException){
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Playlist>(playlist, status);
    }

    playlist = playlistService.getPlaylist(playlistId);

    if(playlist == null){
      status = HttpStatus.NOT_FOUND;
    }
    else{
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Playlist>(playlist, status);
  }

}
