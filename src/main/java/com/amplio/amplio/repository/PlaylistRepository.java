package com.amplio.amplio.repository;

import com.amplio.amplio.models.Playlist;
import org.springframework.data.repository.CrudRepository;

public interface PlaylistRepository extends CrudRepository<Playlist, Integer> {
  Playlist findById(Integer id);
}
