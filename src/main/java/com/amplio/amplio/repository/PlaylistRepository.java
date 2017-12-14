package com.amplio.amplio.repository;


import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface PlaylistRepository extends CrudRepository<Playlist, Integer> {
  Playlist getPlaylistById(Integer playlistId);

  Set<Playlist> findPlaylistsByOwnerAndPublic(User owner);

  List<Playlist> findTop10PlaylistsByTitleContainingAndPublic(String playlistName);

  Set<Playlist> findPlaylistsByOwner(User user);
}
