package com.amplio.amplio.service;

import com.amplio.amplio.constants.Constants;
import com.amplio.amplio.forms.EditPlaylistForm;
import com.amplio.amplio.forms.PlaylistForm;
import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.User;
import com.amplio.amplio.repository.PlaylistRepository;
import com.amplio.amplio.repository.SongRepository;
import com.amplio.amplio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Set;

@Service
public class PlaylistService{
  @Autowired
  private PlaylistRepository playlistRepository;
  @Autowired
  private SongRepository songRepository;
  @Autowired
  private UserRepository userRepository;


  public Playlist createPlaylist(PlaylistForm playlistForm, HttpSession session) {
    Playlist newPlaylist = null;
    String playlistTitle = playlistForm.getTitle();
    String description = playlistForm.getDescription();
    String image = playlistForm.getImage();
    User playlistOwner = (User)session.getAttribute(Constants.SESSION_USER);

    if(playlistOwner != null){
      newPlaylist = new Playlist(playlistTitle, description, image, playlistOwner);
      playlistRepository.save(newPlaylist);
    }
    return newPlaylist;
  }


  public Playlist editPlaylist(Integer playlistId, EditPlaylistForm editPlaylistForm, HttpSession session) {
    Playlist playlistToEdit = playlistRepository.getPlaylistByPlaylistId(playlistId);

    if(playlistToEdit != null){
      User playlistToEditOwner = playlistToEdit.getOwner();
      User sessionUser = (User)session.getAttribute(Constants.SESSION_USER);
      if(!playlistToEditOwner.getUserId().equals(sessionUser.getUserId())){
        playlistToEdit = null;
      }
      else{
        String title = editPlaylistForm.getTitle();
        String description = editPlaylistForm.getDescription();
        playlistToEdit.setTitle(title);
        playlistToEdit.setDescription(description);
        playlistRepository.save(playlistToEdit);
      }
    }

    return playlistToEdit;
  }


  public Playlist getPlaylist(Integer playlistId) {
    Playlist playlist = playlistRepository.getPlaylistByPlaylistId(playlistId);
    return playlist;
  }


  public Playlist deletePlaylist(Integer playlistId, HttpSession session) {
    Playlist playlistToDelete = playlistRepository.getPlaylistByPlaylistId(playlistId);

    if(playlistToDelete != null){
      User playlistToDeleteOwner = playlistToDelete.getOwner();
      User sessionUser = (User)session.getAttribute(Constants.SESSION_USER);
      if(!playlistToDeleteOwner.getUserId().equals(sessionUser.getUserId())){
        playlistToDelete = null;
      }
      else{
        playlistRepository.delete(playlistToDelete);
      }
    }
    return playlistToDelete;
  }


  public Playlist generateGenrePlaylist(String genre) {
    User user = userRepository.findByUserName("Amplio");

    Set<Integer> songIds = songRepository.getSongsByGenreEnum(genre);

    Playlist playlist = new Playlist(genre, genre + " playlist", "../../assets/images/genre/" + genre + ".JPG", user);
    playlist.setPublic(true);
    for(Integer songId: songIds) {
      playlist.getSongs().add(songRepository.getSongBySongId(songId));
    }
    playlistRepository.save(playlist);

    return playlist;
  }


  public Set<Playlist> getGeneratedPlaylists() {
    User amplioUser = userRepository.findByUserName("Amplio");
    Set<Playlist> generatedPlaylists = null;

    if(amplioUser != null) {
      generatedPlaylists = playlistRepository.getPlaylistsByOwner(amplioUser);
    }
    return generatedPlaylists;
  }
}
