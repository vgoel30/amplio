package com.amplio.amplio.repository;


import com.amplio.amplio.models.Playlist;
import com.amplio.amplio.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;

import java.util.List;
import java.util.Set;

public interface PlaylistRepository extends CrudRepository<Playlist, Integer> {
  Playlist getPlaylistById(Integer playlistId);

  @Query(value = "Select * from playlist where owner_id = :id and is_public = true limit 10", nativeQuery = true)
  Set<Playlist> findPlaylistsByOwnerAndPublic(@Param("id") Integer ownerId);

  @Query(value = "Select * from playlist where title like %:query% and is_public = true limit 10", nativeQuery = true)
  List<Playlist> findTop10PlaylistsByTitleContainingAndPublic(@Param("query") String playlistName);

  Set<Playlist> findPlaylistsByOwner(User user);
}
