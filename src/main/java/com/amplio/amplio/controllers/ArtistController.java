package com.amplio.amplio.controllers;

import com.amplio.amplio.models.Artist;
import com.amplio.amplio.service.ArtistService;
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
@RequestMapping(value = "/api/artist")
public class ArtistController {
  @Autowired
  ArtistService artistService;

  @RequestMapping(path = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<Artist> getArtist(@PathVariable String id) {
    Artist artist = null;
    HttpStatus status;
    Integer artistId;

    try {
      artistId = Integer.parseInt(id);
    } catch(NumberFormatException e) {
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Artist>(artist, status);
    }

    artist = artistService.getArtist(artistId);

    if(artist == null) {
      status = HttpStatus.NOT_FOUND;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Artist>(artist, status);
  }

  @RequestMapping(path = "/search/{query}", method = RequestMethod.GET)
  public ResponseEntity<List<Artist>> searchArtist(@PathVariable String query, HttpSession session) {
    HttpStatus status;
    List<Artist> artists = artistService.searchArtist(query, session);

    if(artists == null) {
      status = HttpStatus.FORBIDDEN;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<List<Artist>>(artists, status);
  }
}
