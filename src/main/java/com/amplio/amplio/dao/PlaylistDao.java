package com.amplio.amplio.dao;

import com.amplio.amplio.models.Playlist;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PlaylistDao extends CrudRepository<Playlist, UUID> {
}
