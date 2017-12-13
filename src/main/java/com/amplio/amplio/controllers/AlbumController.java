package com.amplio.amplio.controllers;

import com.amplio.amplio.models.Album;
import com.amplio.amplio.service.AlbumService;
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
@RequestMapping(value = "/api/album")
public class AlbumController {
  @Autowired
  AlbumService albumService;

  @RequestMapping(path = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<Album> getAlbum(@PathVariable String id) {
    Album album = null;
    HttpStatus status;
    Integer albumId;

    try {
      albumId = Integer.parseInt(id);
    } catch(NumberFormatException e) {
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Album>(album, status);
    }
    album = albumService.getAlbum(albumId);

    if(album == null) {
      status = HttpStatus.NOT_FOUND;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<Album>(album, status);
  }

  @RequestMapping(path = "artist/{id}", method = RequestMethod.GET)
  public ResponseEntity<List<Album>> getArtistAlbums(@PathVariable String id) {
    List<Album> artistAlbums = null;
    HttpStatus status;
    Integer artistId;

    try {
      artistId = Integer.parseInt(id);
    } catch(NumberFormatException e) {
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<>(artistAlbums, status);
    }

    artistAlbums = albumService.getArtistAlbums(artistId);

    if(artistAlbums == null) {
      status = HttpStatus.NOT_FOUND;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<List<Album>>(artistAlbums, status);
  }

  @RequestMapping(path = "/search/{query}", method = RequestMethod.GET)
  public ResponseEntity<List<Album>> searchAlbum(@PathVariable String query, HttpSession session) {
    HttpStatus status;
    List<Album> albums = albumService.searchAlbum(query, session);

    if(albums == null) {
      status = HttpStatus.FORBIDDEN;
    } else {
      status = HttpStatus.OK;
    }

    return new ResponseEntity<List<Album>>(albums, status);
  }

}
