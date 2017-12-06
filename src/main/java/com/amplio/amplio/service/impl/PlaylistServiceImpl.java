package com.amplio.amplio.service.impl;

import com.amplio.amplio.forms.PlaylistForm;
import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.User;
import com.amplio.amplio.repository.PlaylistRepository;
import com.amplio.amplio.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class PlaylistServiceImpl implements PlaylistService {
  @Autowired
  private PlaylistRepository playlistRepository;

  @Override
  public Playlist createPlaylist(PlaylistForm playlistForm, HttpSession session) {
    Playlist newPlaylist = null;
    String playlistTitle = playlistForm.getTitle();
    String description = playlistForm.getDescription();
    String image = playlistForm.getImage();
    User playlistOwner = (User)session.getAttribute("user");

    if(playlistOwner != null){
      newPlaylist = new Playlist(playlistTitle, description, image, playlistOwner);
      playlistRepository.save(newPlaylist);
    }
    return newPlaylist;
  }

  @Override
  public Playlist getPlaylist(Integer playlistId) {
    Playlist playlist = playlistRepository.getPlaylistByPlaylistId(playlistId);
    return playlist;
  }

  @Override
  public Playlist deletePlaylist(Integer playlistId, HttpSession session) {
    Playlist playlistToDelete;
    playlistToDelete = playlistRepository.getPlaylistByPlaylistId(playlistId);
    if(playlistToDelete != null){
      User playlistToDeleteOwner = playlistToDelete.getOwner();
      User sessionUser = (User)session.getAttribute("user");
      if(!playlistToDeleteOwner.getUserId().equals(sessionUser.getUserId())){
        playlistToDelete = null;
      }
      else{
        playlistToDeleteOwner.getPlaylists().remove(playlistToDelete);
        playlistRepository.delete(playlistToDelete);
      }
    }
    return playlistToDelete;
  }
}
