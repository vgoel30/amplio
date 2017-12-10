package com.amplio.amplio.controllers;

import com.amplio.amplio.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/api/song")
public class SongController {
  @Autowired
  private SongService songService;

  @RequestMapping(path = "/play/{id}", method = RequestMethod.POST)
  public ResponseEntity<Boolean> playSong(HttpSession session, String id) {
    HttpStatus status;
    Boolean queuesUpdated = false;
    Integer songId;

    try {
      songId = Integer.parseInt(id);
    } catch(NumberFormatException e) {
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Boolean>(queuesUpdated, status);
    }

    queuesUpdated = songService.playSong(session, songId);

    if(queuesUpdated == true) {
      status = HttpStatus.OK;
    } else {
      status = HttpStatus.NOT_FOUND;
    }

    return new ResponseEntity<Boolean>(queuesUpdated, status);
  }
}
