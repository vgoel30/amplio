package com.amplio.amplio.service;

import com.amplio.amplio.models.Artist;
import com.amplio.amplio.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {
  @Autowired
  ArtistRepository artistRepository;

  public Artist getArtist(Integer artistId){
    Artist artist = artistRepository.findById(artistId);
    return artist;
  }
}
