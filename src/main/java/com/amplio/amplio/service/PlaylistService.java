package com.amplio.amplio.service;

import com.amplio.amplio.forms.EditPlaylistForm;
import com.amplio.amplio.forms.PlaylistForm;
import com.amplio.amplio.models.Playlist;

import javax.servlet.http.HttpSession;
import java.util.Set;

public interface PlaylistService {
  Playlist createPlaylist(PlaylistForm playlistForm, HttpSession session);
  Playlist editPlaylist(Integer playlistId, EditPlaylistForm editPlaylistForm, HttpSession session);
  Playlist getPlaylist(Integer playlistId);
  Playlist deletePlaylist(Integer playlistId, HttpSession session);
  Playlist generateGenrePlaylist(String genre);

  Set<Playlist> getGeneratedPlaylists();
}
