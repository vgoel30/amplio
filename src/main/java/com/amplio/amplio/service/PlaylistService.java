package com.amplio.amplio.service;

import com.amplio.amplio.forms.PlaylistForm;
import com.amplio.amplio.models.Playlist;

import javax.servlet.http.HttpSession;

public interface PlaylistService {
  Playlist createPlaylist(PlaylistForm playlistForm, HttpSession session);
  Playlist getPlaylist(Integer playlistId);
}
