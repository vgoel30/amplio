package com.amplio.amplio.controllers;

import com.amplio.amplio.forms.ArtistForm;
import com.amplio.amplio.models.Artist;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminController {

  @RequestMapping(path = "/uploadArtist", method = RequestMethod.POST)
  public ResponseEntity<Artist> uploadArtist(
      @RequestBody
          ArtistForm artistForm) {

  }
}
