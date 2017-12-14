package com.amplio.amplio.controllers;

import com.amplio.amplio.forms.AlbumForm;
import com.amplio.amplio.forms.ArtistForm;
import com.amplio.amplio.models.Album;
import com.amplio.amplio.models.Artist;
import com.amplio.amplio.service.AdminService;
import com.amplio.amplio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping(value = "/api/admin")
public class AdminController {
  @Autowired
  private AdminService adminService;
  @Autowired
  private UserService userService;


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

  @RequestMapping(path = "/toggleban/{id}", method = RequestMethod.POST)
  public ResponseEntity<Boolean> banUser(@PathVariable String id, HttpSession session){
    HttpStatus status;
    Integer userId;
    Boolean userBannedToggle = false;

    try{
      userId = Integer.parseInt(id);
    }
    catch(NumberFormatException e){
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Boolean>(userBannedToggle, status);
    }

    userBannedToggle = adminService.toggleBan(userId, session);

    if(userBannedToggle){
      status = HttpStatus.OK;
    }
    else{
      status = HttpStatus.NOT_FOUND;
    }

    return new ResponseEntity<Boolean>(userBannedToggle, status);
  }

  @RequestMapping(path = "/user/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Boolean> deleteUser(@PathVariable String id){
    HttpStatus status;
    Integer userId;
    Boolean userDeleted = true;

    try{
      userId = Integer.parseInt(id);
    }
    catch(NumberFormatException e){
      status = HttpStatus.BAD_REQUEST;
      return new ResponseEntity<Boolean>(userDeleted, status);
    }

    userDeleted = userService.deleteUser(userId);

    if(userDeleted){
      status = HttpStatus.OK;
    }
    else{
      status = HttpStatus.NOT_FOUND;
    }

    return new ResponseEntity<Boolean>(userDeleted, status);
  }
}
