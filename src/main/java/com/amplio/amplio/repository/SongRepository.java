package com.amplio.amplio.repository;

import com.amplio.amplio.models.Song;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SongRepository extends CrudRepository<Song, UUID> {
}
