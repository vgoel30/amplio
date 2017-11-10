package com.amplio.amplio.dao;

import com.amplio.amplio.models.Song;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SongDao extends CrudRepository<Song, UUID> {
}
