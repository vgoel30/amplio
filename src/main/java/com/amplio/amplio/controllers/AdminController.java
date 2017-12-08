package com.amplio.amplio.controllers;

import com.amplio.amplio.forms.AlbumForm;
import com.amplio.amplio.forms.ArtistForm;
import com.amplio.amplio.models.Album;
import com.amplio.amplio.models.Artist;
import com.amplio.amplio.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/admin")
public class AdminController {
  @Autowired
  private AdminService adminService;


  @RequestMapping(path = "/uploadArtist", method = RequestMethod.POST)
  public ResponseEntity<Artist> uploadArtist(@RequestBody ArtistForm artistForm) {
    HttpStatus status;
    Artist artist = adminService.addArtist(artistForm);
    if(artist == null) {
      status = HttpStatus.FORBIDDEN;
    } else {
      status = HttpStatus.CREATED;
    }
    return new ResponseEntity<>(artist, status);
  }

  @RequestMapping(path = "/uploadAlbum", method = RequestMethod.POST)
  public ResponseEntity<Album> uploadAlbum(@RequestBody AlbumForm albumForm) {
    HttpStatus status;
    Album album = adminService.addAlbum(albumForm);
    if(album == null) {
      status = HttpStatus.FORBIDDEN;
    } else {
      status = HttpStatus.CREATED;
    }
    return new ResponseEntity<>(album, status);
  }
}
