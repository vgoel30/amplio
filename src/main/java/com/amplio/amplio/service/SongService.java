package com.amplio.amplio.service;

import com.amplio.amplio.constants.Constants;
import com.amplio.amplio.models.Album;
import com.amplio.amplio.models.Artist;
import com.amplio.amplio.models.Song;
import com.amplio.amplio.models.User;
import com.amplio.amplio.repository.AlbumRepository;
import com.amplio.amplio.repository.ArtistRepository;
import com.amplio.amplio.repository.SongRepository;
import com.amplio.amplio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

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

  public Boolean playSong(HttpSession session, Integer songId){
    Boolean queuesUpdated = false;
    User user = (User)session.getAttribute(Constants.SESSION_USER);
    user = userRepository.findUserByUserId(user.getUserId());
    Song song = songRepository.findSongBySongId(songId);

    if(user != null && song != null){
      user.getSongHistory().add(song);
      userRepository.save(user);
      song.incrementNumPlays();
      songRepository.save(song);
      queuesUpdated = true;
    }

    return  queuesUpdated;
  }

  public List<Song> getTopSongsByArtist(HttpSession session, Integer artistId) {
    User currentUser = (User)session.getAttribute(Constants.SESSION_USER);
    Artist artist = artistRepository.findByArtistId(artistId);
    List<Song> songsByArtist = null;

    if(currentUser != null && artist != null) {
      songsByArtist = songRepository.findTop25SongsByArtistOrderByNumberPlaysDesc(artist);
    }

    return songsByArtist;
  }

  public List<Song> getSongsByAlbum(HttpSession session, Integer albumId) {
    User currentUser = (User)session.getAttribute(Constants.SESSION_USER);
    Album album = albumRepository.findByAlbumId(albumId);
    List<Song> albumSongs = null;

    if(currentUser != null && album != null) {
      albumSongs = songRepository.findSongsByAlbum(album);
    }

    return albumSongs;
  }
}

