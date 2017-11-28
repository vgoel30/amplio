package com.amplio.amplio.service.impl;

import com.amplio.amplio.forms.AlbumForm;
import com.amplio.amplio.forms.ArtistForm;
import com.amplio.amplio.models.Album;
import com.amplio.amplio.models.Artist;
import com.amplio.amplio.models.Concert;
import com.amplio.amplio.models.Song;
import com.amplio.amplio.repository.AlbumRepository;
import com.amplio.amplio.repository.ArtistRepository;
import com.amplio.amplio.repository.SongRepository;
import com.amplio.amplio.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminServiceImpl implements AdminService {
  @Autowired
  private ArtistRepository artistRepository;
  @Autowired
  private AlbumRepository albumRepository;
  @Autowired
  private SongRepository songRepository;

  @Override
  public Artist addArtist(ArtistForm artistForm) {
    String name = artistForm.getName();
    String bibliography = artistForm.getBibliography();

    if(name == null) {
      return null;
    }

    Set<Album> albums = new HashSet<Album>();
    Set<Concert> concerts = new HashSet<Concert>();
    Artist artist = new Artist(name, bibliography, albums, concerts);
    artistRepository.save(artist);
    return artist;
  }

  @Override
  public Album addAlbum(AlbumForm albumForm) {
    Artist artist = artistRepository.getArtistByArtistID(albumForm.getArtistID());
    SimpleDateFormat date = new SimpleDateFormat(albumForm.getDate());
    String title = albumForm.getTitle();
    List<Song> songs = albumForm.getSongs();

    if(artist == null || date == null || title == null || songs == null) {
      return null;
    }

    Album album = new Album(artist, date, songs, title);

    for(Song song : songs) {
      //TODO: Figure out artists references
      song.setAlbum(album);
      songRepository.save(song);
    }

    artist.getAlbums().add(album);
    artistRepository.save(artist);

    albumRepository.save(album);
    return album;
  }
}
