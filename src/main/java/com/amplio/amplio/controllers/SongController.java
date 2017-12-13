package com.amplio.amplio.controllers;

import com.amplio.amplio.models.Song;
import com.amplio.amplio.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/api/song")
public class SongController {
  @Autowired
  private SongService songService;

  @RequestMapping(path = "/play/{id}", method = RequestMethod.POST)
  public ResponseEntity<Boolean> playSong(HttpSession session,@PathVariable String id) {
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

  @RequestMapping(path = "/artist/{id}", method = RequestMethod.GET)
  public ResponseEntity<List<Song>> getSongsByArtist(HttpSession session, @PathVariable String id) {
    HttpStatus status;
    List<Song> songsByArtist = null;
    Integer artistId;

    try {
      artistId = Integer.parseInt(id);
      songsByArtist = songService.getTopSongsByArtist(session,artistId);
      if(songsByArtist != null) {
        status = HttpStatus.OK;
      } else {
        status = HttpStatus.NOT_FOUND;
      }
    } catch(NumberFormatException e) {
      status = HttpStatus.BAD_REQUEST;
    }

    return  new ResponseEntity<List<Song>>(songsByArtist,status);
  }

  @RequestMapping(path = "/album/{id}", method = RequestMethod.GET)
  public ResponseEntity<List<Song>> getSongsByAlbum(HttpSession session, @PathVariable String id) {
    HttpStatus status;
    List<Song> albumSongs = null;
    Integer albumId;

    try {
      albumId = Integer.parseInt(id);
      albumSongs = songService.getSongsByAlbum(session,albumId);
      if(albumSongs != null) {
        status = HttpStatus.OK;
      } else {
        status = HttpStatus.NOT_FOUND;
      }
    } catch(NumberFormatException e) {
      status = HttpStatus.BAD_REQUEST;
    }

    return  new ResponseEntity<List<Song>>(albumSongs,status);
  }

  @RequestMapping(path = "/search/{query}", method = RequestMethod.GET)
  public ResponseEntity<List<Song>> searchSong(@PathVariable String query, HttpSession session) {
    HttpStatus status;
    List<Song> songs = songService.searchSong(query, session);

    if(songs == null) {
      status = HttpStatus.FORBIDDEN;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<List<Song>>(songs, status);
  }
}
