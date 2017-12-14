package com.amplio.amplio.service;

import com.amplio.amplio.forms.AlbumForm;
import com.amplio.amplio.forms.ArtistForm;
import com.amplio.amplio.models.*;
import com.amplio.amplio.repository.AlbumRepository;
import com.amplio.amplio.repository.ArtistRepository;
import com.amplio.amplio.repository.SongRepository;
import com.amplio.amplio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdminService {
  @Autowired
  private ArtistRepository artistRepository;
  @Autowired
  private AlbumRepository albumRepository;
  @Autowired
  private SongRepository songRepository;
  @Autowired
  private UserRepository userRepository;

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
    Artist artist = artistRepository.findById(albumForm.getArtistID());
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
      song.setArtist(artist);
      song.setNumberPlays(0);

      song.setGenreEnum(genres);
      songRepository.save(song);
    }

    return album;
  }

  public boolean toggleBan(Integer userId){
    boolean toggledUserBan = false;
    User currentUser = userRepository.findUserById(userId);
    Integer userToToggleId = currentUser.getId();

    if(currentUser != null){
      currentUser.setReported(false);
      currentUser.setBanned(!currentUser.getBanned());

      //ban the user
      if(currentUser.getBanned()){
        //1. delete this user from the following list of their followers
        for(Follower follower : currentUser.getFollowers()) {
          Integer followerId = follower.getId();
          User followerAsUser = userRepository.findUserById(followerId);
          Set<Follower> followingSet = followerAsUser.getFollowing();
          for(Follower following : followingSet) {
            if(following.getId().equals(userToToggleId)) {
              followingSet.remove(following);
              break;
            }
          }
        }

        //2. delete this user from the followers list of the people that they are following
        for(Follower following : currentUser.getFollowing()) {
          Integer followingId = following.getId();
          User followingAsUser = userRepository.findUserById(followingId);
          Set<Follower> followerSet = followingAsUser.getFollowers();
          for(Follower follower : followerSet) {
            if(follower.getId().equals(userToToggleId)) {
              followerSet.remove(follower);
              break;
            }
          }
        }
      }
      userRepository.save(currentUser);
      toggledUserBan = true;
    }

    return toggledUserBan;
  }
}
