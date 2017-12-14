package com.amplio.amplio.service;

import com.amplio.amplio.constants.Constants;
import com.amplio.amplio.models.Album;
import com.amplio.amplio.models.Artist;
import com.amplio.amplio.models.User;
import com.amplio.amplio.repository.AlbumRepository;
import com.amplio.amplio.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

  public List<Album> searchAlbum(String query, HttpSession session) {
    User currentUser = (User) session.getAttribute(Constants.SESSION_USER);
    List<Album> albums = null;
    if(currentUser != null) {
      albums = albumRepository.findTop10AlbumsByTitleContaining(query);
    }
    return albums;
  }

  public List<Album> findRelatedAlbums(Integer albumId, HttpSession session) {
    User currentUser = (User) session.getAttribute(Constants.SESSION_USER);
    Album currentAlbum = albumRepository.findById(albumId);
    List<Album> albums = null;

    if(currentUser != null) {
      List<Integer> albumIds = albumRepository.findRelatedAlbums(currentAlbum.getGenre().toString(),currentAlbum.getTitle());
      albums = new ArrayList<Album>();
      for(Integer id: albumIds) {
        albums.add(albumRepository.findById(id));
      }
    }
    return albums;
  }
}
