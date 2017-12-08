package com.amplio.amplio.service;

import com.amplio.amplio.forms.AlbumForm;
import com.amplio.amplio.forms.ArtistForm;
import com.amplio.amplio.models.*;
import com.amplio.amplio.repository.AlbumRepository;
import com.amplio.amplio.repository.ArtistRepository;
import com.amplio.amplio.repository.SongRepository;
import com.amplio.amplio.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdminService{
  @Autowired
  private ArtistRepository artistRepository;
  @Autowired
  private AlbumRepository albumRepository;
  @Autowired
  private SongRepository songRepository;

  public Artist addArtist(ArtistForm artistForm) {
    String name = artistForm.getName();
    String bibliography = artistForm.getBibliography();

    if(name == null) {
      return null;
    }

    Set<Concert> concerts = new HashSet<Concert>();
    Artist artist = new Artist(name, bibliography, concerts, "");
    artistRepository.save(artist);
    return artist;
  }


  public Album addAlbum(AlbumForm albumForm) {
    Artist artist = artistRepository.findByArtistId(albumForm.getArtistID());
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);
    Date date = null;
    try {
      date = dateFormat.parse(albumForm.getDate());
    } catch(ParseException e) {
      System.out.println("Date parse error");
      date = new Date();
    }
    String title = albumForm.getTitle();
    List<Song> songs = albumForm.getSongs();

    if(artist == null || date == null || title == null || songs == null) {
      return null;
    }

    Album album = new Album(artist, date, title, "");

    albumRepository.save(album);

    Set<GenreEnum> genres = new HashSet<GenreEnum>();
    for(String genre : albumForm.getGenres()) {
      genres.add(GenreEnum.valueOf(genre));
    }

    for(Song song : songs) {
      song.setAlbum(album);
      List<Artist> artists = new ArrayList<Artist>();
      artists.add(artist);
      song.setArtists(artists);
      song.setNumberPlays(0);

      song.setGenreEnum(genres);
      songRepository.save(song);
    }

    return album;
  }
}
