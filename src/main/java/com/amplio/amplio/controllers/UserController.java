package com.amplio.amplio.controllers;

import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.User;
import com.amplio.amplio.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
  @Autowired
  private UserServiceImpl userService;

  @RequestMapping(path = "/playlists", method = RequestMethod.GET)
  public ResponseEntity<List<Playlist>> getPlaylist(HttpSession session){
    HttpStatus status;
    List<Playlist> userPlaylists = userService.getPlaylists(session);

    if(userPlaylists == null){
      status = HttpStatus.UNAUTHORIZED;
    }
    else{
      status = HttpStatus.OK;
    }

    return new ResponseEntity<List<Playlist>>(userPlaylists, status);
  }
}
