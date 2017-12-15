package com.amplio.amplio.service;

import com.amplio.amplio.constants.Constants;
import com.amplio.amplio.forms.PlaylistForm;
import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.Song;
import com.amplio.amplio.models.User;
import com.amplio.amplio.repository.PlaylistRepository;
import com.amplio.amplio.repository.SongRepository;
import com.amplio.amplio.repository.UserRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

import static com.amplio.amplio.constants.Constants.SESSION_USER;

@Service
public class PlaylistService {
  @Autowired
  private PlaylistRepository playlistRepository;
  @Autowired
  private SongRepository songRepository;
  @Autowired
  private UserRepository userRepository;

  public List<Playlist> searchPlaylist(String query, HttpSession session) {
    List<Playlist> playlists = null;
    User currentUser = (User) session.getAttribute(Constants.SESSION_USER);

    if(currentUser != null) {
      playlists = playlistRepository.findTop10PlaylistsByTitleContainingAndPublic(query);
    }

    return playlists;
  }

  public Playlist createPlaylist(PlaylistForm playlistForm, HttpSession session) {
    Playlist newPlaylist = null;
    String playlistTitle = playlistForm.getTitle();
    String description = playlistForm.getDescription();
    String image = playlistForm.getImage();
    User playlistOwner = (User) session.getAttribute(SESSION_USER);

    if(playlistOwner != null) {
      newPlaylist = new Playlist(playlistTitle, description, image, playlistOwner);
      playlistRepository.save(newPlaylist);
    }
    return newPlaylist;
  }


  public Playlist editPlaylist(Integer playlistId, PlaylistForm editPlaylistForm, HttpSession session) {
    Playlist playlistToEdit = playlistRepository.getPlaylistById(playlistId);

    if(playlistToEdit != null) {
      User playlistToEditOwner = playlistToEdit.getOwner();
      User sessionUser = (User) session.getAttribute(SESSION_USER);
      if(!playlistToEditOwner.getId().equals(sessionUser.getId())) {
        playlistToEdit = null;
      } else {
        String title = editPlaylistForm.getTitle();
        String description = editPlaylistForm.getDescription();
        playlistToEdit.setTitle(title);
        playlistToEdit.setDescription(description);
        playlistRepository.save(playlistToEdit);
      }
    }

    return playlistToEdit;
  }

  public Song addSongToPlaylist(Integer playlistId, Integer songId, HttpSession session){
    Song songAdded = null;
    User user = (User)session.getAttribute(SESSION_USER);
    if(user != null){
      user = userRepository.findUserById(user.getId());
      Playlist playlist = playlistRepository.getPlaylistById(playlistId);
      Song song = songRepository.findSongById(songId);

      if(song != null && playlist != null && playlist.getOwner().equals(user)){
        playlist.getSongs().add(song);
        userRepository.save(user);
        playlistRepository.save(playlist);
        songAdded = song;
      }
    }

    return songAdded;
  }

  public Playlist removeSongFromPlaylist(Integer playlistId, Integer songId, HttpSession session){
    Playlist updatedPlaylist = null;
    User user = (User)session.getAttribute(SESSION_USER);

    if(user != null){
      user = userRepository.findUserById(user.getId());
      updatedPlaylist = playlistRepository.getPlaylistById(playlistId);
      Song song = songRepository.findSongById(songId);

      if(song != null && updatedPlaylist != null && updatedPlaylist.getOwner().equals(user)){
        for(Song playlistSong : updatedPlaylist.getSongs()){
          if(playlistSong.getId().equals(songId)){
            updatedPlaylist.getSongs().remove(song);
            userRepository.save(user);
            playlistRepository.save(updatedPlaylist);
            break;
          }
        }
      }
    }

    return updatedPlaylist;
  }

  public Playlist getPlaylist(Integer playlistId) {
    Playlist playlist = playlistRepository.getPlaylistById(playlistId);
    return playlist;
  }


  public Boolean deletePlaylist(Integer playlistId, HttpSession session) {
    Boolean deleted = false;
    Playlist playlistToDelete = playlistRepository.getPlaylistById(playlistId);

    if(playlistToDelete != null) {
      User playlistToDeleteOwner = playlistToDelete.getOwner();
      User sessionUser = (User) session.getAttribute(SESSION_USER);
      if(!playlistToDeleteOwner.getId().equals(sessionUser.getId())) {
        playlistToDelete = null;
      } else {
        playlistRepository.delete(playlistToDelete);
        deleted = true;
      }
    }
    return deleted;
  }


  public Playlist generateGenrePlaylist(String genre) {
    User user = userRepository.findByUserName("Amplio");

    Set<Integer> songIds = songRepository.findSongsByGenre(genre);

    Playlist playlist = new Playlist(genre, genre + " playlist", "../../assets/images/genre/" + genre + ".JPG", user);
    playlist.setPublic(true);
    for(Integer songId : songIds) {
      playlist.getSongs().add(songRepository.findSongById(songId));
    }
    playlistRepository.save(playlist);

    return playlist;
  }

  public Set<Playlist> getGeneratedPlaylists() {
    User amplioUser = userRepository.findByUserName("Amplio");
    Set<Playlist> generatedPlaylists = null;

    if(amplioUser != null) {
      generatedPlaylists = playlistRepository.findPlaylistsByOwner(amplioUser);
      generatedPlaylists.add(getRecommended());
    }
    return generatedPlaylists;
  }

  public Set<Playlist> getPlaylistsByUser(Integer userId, HttpSession session) {
    User user = (User) session.getAttribute(SESSION_USER);
    User playlistOwner = userRepository.findUserById(userId);
    Set<Playlist> playlists = null;

    if(user != null && playlistOwner != null) {
      playlists = playlistRepository.findPlaylistsByOwnerAndPublic(playlistOwner.getId());
    }

    return playlists;
  }

  public Boolean togglePrivatePlaylist(Integer playlistId, HttpSession session){
    Boolean toggledPrivatePlaylist = false;
    User user = (User) session.getAttribute(SESSION_USER);
    Playlist playlistToToggle = playlistRepository.getPlaylistById(playlistId);

    if(user != null && playlistToToggle != null) {
      user = userRepository.findUserById(user.getId());
      if(user != null && playlistToToggle.getOwner().equals(user)){
        playlistToToggle.setPublic(!playlistToToggle.isPublic());
        userRepository.save(user);
        playlistRepository.save(playlistToToggle);
        toggledPrivatePlaylist = true;
      }
    }

    return toggledPrivatePlaylist;
  }

  public Playlist getTopCharts(HttpSession session) {
    User currentUser = (User) session.getAttribute(SESSION_USER);
    User amplio = userRepository.findByUserName("Amplio");
    Playlist topCharts = null;

    if(currentUser != null) {
      Set<Integer> songIds = songRepository.findTopSongs();

      topCharts = new Playlist("Top Charts","Most popular songs", "", amplio);
      for(Integer id: songIds) {
        topCharts.getSongs().add(songRepository.findSongById(id));
      }
    }

    return topCharts;
  }

  public Playlist getRecommended() {
    User amplio = userRepository.findByUserName("Amplio");
    Playlist recommendedSongs = null;

    Set<Integer> songIds = songRepository.findRecommendedSongs();

    recommendedSongs = new Playlist("RECOMMENDED","", "", amplio);
    for(Integer id: songIds) {
      recommendedSongs.getSongs().add(songRepository.findSongById(id));
    }


    return recommendedSongs;
  }
}
