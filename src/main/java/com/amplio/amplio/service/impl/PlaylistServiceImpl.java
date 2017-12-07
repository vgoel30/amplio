package com.amplio.amplio.service.impl;

import com.amplio.amplio.forms.EditPlaylistForm;
import com.amplio.amplio.forms.PlaylistForm;
import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.User;
import com.amplio.amplio.repository.PlaylistRepository;
import com.amplio.amplio.repository.SongRepository;
import com.amplio.amplio.repository.UserRepository;
import com.amplio.amplio.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Set;

@Service
public class PlaylistServiceImpl implements PlaylistService {
  @Autowired
  private PlaylistRepository playlistRepository;
  @Autowired
  private SongRepository songRepository;
  @Autowired
  private UserRepository userRepository;

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
  public Playlist editPlaylist(Integer playlistId, EditPlaylistForm editPlaylistForm, HttpSession session) {
    Playlist playlistToEdit = playlistRepository.getPlaylistByPlaylistId(playlistId);

    if(playlistToEdit != null){
      User playlistToEditOwner = playlistToEdit.getOwner();
      User sessionUser = (User)session.getAttribute("user");
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

  @Override
  public Playlist getPlaylist(Integer playlistId) {
    Playlist playlist = playlistRepository.getPlaylistByPlaylistId(playlistId);
    return playlist;
  }

  @Override
  public Playlist deletePlaylist(Integer playlistId, HttpSession session) {
    Playlist playlistToDelete = playlistRepository.getPlaylistByPlaylistId(playlistId);

    if(playlistToDelete != null){
      User playlistToDeleteOwner = playlistToDelete.getOwner();
      User sessionUser = (User)session.getAttribute("user");
      if(!playlistToDeleteOwner.getUserId().equals(sessionUser.getUserId())){
        playlistToDelete = null;
      }
      else{
        playlistRepository.delete(playlistToDelete);
      }
    }
    return playlistToDelete;
  }

  @Override
  public Playlist generateGenrePlaylist(String genre) {
      User user = userRepository.findByUserName("Amplio");

    Set<Integer> songIds = songRepository.getSongsByGenreEnum(genre);

    Playlist playlist = new Playlist(genre,genre + " playlist", "" , user);
    playlist.setPublic(true);
    for(Integer songId: songIds) {
      playlist.getSongs().add(songRepository.getSongBySongId(songId));
    }
    playlistRepository.save(playlist);

    return playlist;
  }


}
