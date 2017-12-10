package com.amplio.amplio.service;

import com.amplio.amplio.constants.Constants;
import com.amplio.amplio.models.Artist;
import com.amplio.amplio.models.Song;
import com.amplio.amplio.models.User;
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

  public Boolean playSong(HttpSession session, Integer songId){
    Boolean queuesUpdated = false;
    User user = (User)session.getAttribute(Constants.SESSION_USER);
    user = userRepository.findUserByUserId(user.getUserId());
    Song song = songRepository.findSongBySongId(songId);

    if(user != null && song != null){
      user.getSongHistory().add(song);
      song.setNumberPlays(song.getNumberPlays() + 1);
      queuesUpdated = true;
    }

    return  queuesUpdated;
  }

  public List<Song> getTopSongsByArtist(HttpSession session, Integer artistId) {
    User currentUser = (User)session.getAttribute(Constants.SESSION_USER);
    Artist artist = artistRepository.findByArtistId(artistId);
    List<Song> songsByArtist = null;

    if(currentUser != null) {
      songsByArtist = songRepository.findTop25SongsByArtistOrderByNumberPlaysDesc(artist);
    }

    return songsByArtist;
  }
}

