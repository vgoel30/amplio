package com.amplio.amplio.service.impl;

import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.repository.PlaylistRepository;
import com.amplio.amplio.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaylistServiceImpl implements PlaylistService {
  @Autowired
  private PlaylistRepository playlistRepository;

  @Override
  public Playlist getPlaylist(Integer playlistId) {
    Playlist playlist = (Playlist) playlistRepository.findByCollectionId(playlistId);
    return playlist;
  }
}
