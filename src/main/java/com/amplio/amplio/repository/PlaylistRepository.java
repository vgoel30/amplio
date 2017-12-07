package com.amplio.amplio.repository;


import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface PlaylistRepository extends CrudRepository<Playlist, Integer> {
  Playlist getPlaylistByPlaylistId(Integer playlistId);
  Set<Playlist> getPlaylistsByOwner(User owner);
}
