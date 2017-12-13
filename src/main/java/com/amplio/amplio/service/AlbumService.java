package com.amplio.amplio.service;

import com.amplio.amplio.models.Album;
import com.amplio.amplio.models.Artist;
import com.amplio.amplio.repository.AlbumRepository;
import com.amplio.amplio.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {
  @Autowired
  private AlbumRepository albumRepository;
  @Autowired
  private ArtistRepository artistRepository;

  public Album getAlbum(Integer albumId) {
    return albumRepository.findById(albumId);
  }

  public List<Album> getArtistAlbums(Integer artistId) {
    List<Album> artistAlbums = null;
    Artist artist = artistRepository.findById(artistId);
    if(artist != null) {
      artistAlbums = albumRepository.findAlbumsByArtist(artist);
    }
    return artistAlbums;
  }

}
