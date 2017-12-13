package com.amplio.amplio.service;

import com.amplio.amplio.models.Artist;
import com.amplio.amplio.models.User;
import com.amplio.amplio.repository.ArtistRepository;
import com.amplio.amplio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

import static com.amplio.amplio.constants.Constants.SESSION_USER;

@Service
public class ArtistService {
  @Autowired
  ArtistRepository artistRepository;
  @Autowired
  UserRepository userRepository;

  public Artist getArtist(Integer artistId){
    Artist artist = artistRepository.findById(artistId);
    return artist;
  }

  public List<Artist> searchArtist(String query, HttpSession session) {
    User currentUser = (User) session.getAttribute(SESSION_USER);
    List<Artist> artists = null;
    if(currentUser != null) {
      artists = artistRepository.findTop10ArtistsByNameContaining(query);
    }
    return artists;
  }

  public Set<User> getFollowers(HttpSession session, Integer artistId) {
    User currentUser = (User) session.getAttribute(SESSION_USER);
    Set<Integer> followerIds;
    Set<User> followers = null;
    if(currentUser != null) {
      followerIds = userRepository.findUsersByFollowedArtist(artistId);
      for(Integer followerId: followerIds) {
        User user = userRepository.findUserById(followerId);
        followers.add(user);
      }
    }

    return followers;
  }
}