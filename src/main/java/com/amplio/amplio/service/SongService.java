package com.amplio.amplio.service;

import com.amplio.amplio.constants.Constants;
import com.amplio.amplio.models.*;
import com.amplio.amplio.repository.AlbumRepository;
import com.amplio.amplio.repository.ArtistRepository;
import com.amplio.amplio.repository.SongRepository;
import com.amplio.amplio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static com.amplio.amplio.constants.Constants.SESSION_USER;

@Service
public class SongService {
  @Autowired
  SongRepository songRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  ArtistRepository artistRepository;
  @Autowired
  AlbumRepository albumRepository;

  public Boolean playSong(HttpSession session, Integer songId) {
    Boolean queuesUpdated = false;
    User user = (User) session.getAttribute(Constants.SESSION_USER);
    user = userRepository.findUserById(user.getId());
    Song song = songRepository.findSongById(songId);

    if(user != null && song != null) {
      user.getSongHistory().add(song);
      userRepository.save(user);
      song.incrementNumPlays();
      songRepository.save(song);
      queuesUpdated = true;
    }

    return queuesUpdated;
  }

  public List<Song> getTopSongsByArtist(HttpSession session, Integer artistId) {
    User currentUser = (User) session.getAttribute(Constants.SESSION_USER);
    Artist artist = artistRepository.findById(artistId);
    List<Song> songsByArtist = null;

    if(currentUser != null && artist != null) {
      songsByArtist = songRepository.findTop25SongsByArtistOrderByNumberPlaysDesc(artist);
    }

    return songsByArtist;
  }

  public List<Song> getSongsByAlbum(HttpSession session, Integer albumId) {
    User currentUser = (User) session.getAttribute(Constants.SESSION_USER);
    Album album = albumRepository.findById(albumId);
    List<Song> albumSongs = null;

    if(currentUser != null && album != null) {
      albumSongs = songRepository.findSongsByAlbum(album);
    }

    return albumSongs;
  }

  public List<Song> searchSong(String query, HttpSession session) {
    User currentUser = (User) session.getAttribute(SESSION_USER);
    List<Song> songs = null;
    if(currentUser != null) {
      songs = songRepository.findTop10SongsBySongNameContainingOrderByNumberPlaysDesc(query);
    }
    return songs;
  }

  public List<Song> getRelatedSongs(HttpSession session, Integer songId) {
    User currentUser = (User) session.getAttribute(SESSION_USER);
    Song song = songRepository.findSongById(songId);
    List<Song> relatedSongs = null;

    if(currentUser != null) {
      Object[] genreEnums = song.getGenreEnum().toArray();
      GenreEnum genre = (GenreEnum) genreEnums[0];
      List<Integer> relatedIds = songRepository.findRelatedSongs(genre.toString(),song.getSongName());

      relatedSongs = new ArrayList<Song>();
      for(Integer id: relatedIds) {
        relatedSongs.add(songRepository.findSongById(id));
      }
    }

    return relatedSongs;
  }
}

